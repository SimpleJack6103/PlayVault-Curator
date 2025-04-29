package PlayVaultCurator.UI;

import PlayVaultCurator.util.SettingsManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;

public class MemorySection extends VBox {
    private Label totalStorageLabel;
    private ProgressBar memoryBar;
    private Button calculateButton;

    // The black threshold marker and the stored threshold value as a fraction (0.70 means 70%).
    private Region thresholdMarker;
    private DoubleProperty threshold = new SimpleDoubleProperty(0.70);

    // Optional directory filter — if set, only that drive is evaluated.
    private File directoryFilter = null;

    // Container to overlay the threshold marker on the progress bar.
    private StackPane progressBarContainer;

    // Store the current total capacity (in GB) so we can convert GB or MB thresholds.
    private double currentTotalGB = 0;

    public MemorySection() {
        // Create the storage usage label.
        totalStorageLabel = new Label("Total Storage: 0.0 GB / 0.0 GB");
        totalStorageLabel.getStyleClass().add("label");

        // Initialize the progress bar.
        memoryBar = new ProgressBar();
        memoryBar.getStyleClass().add("memory-bar");
        memoryBar.setMaxWidth(Double.MAX_VALUE);
        memoryBar.setPrefHeight(20);

        // Create the threshold marker: a thin black vertical line.
        thresholdMarker = new Region();
        thresholdMarker.setStyle("-fx-background-color: black;");
        thresholdMarker.setPrefWidth(2);
        thresholdMarker.setMinWidth(2);
        thresholdMarker.setMaxWidth(2);
        // Bind the marker's height to match the progress bar's height.
        thresholdMarker.prefHeightProperty().bind(memoryBar.heightProperty());

        // Create a StackPane so the threshold marker overlays the progress bar.
        progressBarContainer = new StackPane();
        progressBarContainer.getChildren().addAll(memoryBar, thresholdMarker);

        // Bind the threshold marker's translateX property so that it sits at (threshold * barWidth - barWidth/2).
        thresholdMarker.translateXProperty().bind(new DoubleBinding() {
            {
                super.bind(memoryBar.widthProperty(), threshold);
            }
            @Override
            protected double computeValue() {
                double barWidth = memoryBar.getWidth();
                return barWidth * threshold.get() - (barWidth / 2);
            }
        });

        // Create the Calculate button.
        calculateButton = new Button("Calculate");
        calculateButton.getStyleClass().add("calculate-button");
        calculateButton.setOnAction(e -> {
            System.out.println("Calculating...");
            // TODO: Connect to your deletion suggestions logic.
        });

        // Layout the progress bar and the button in an HBox.
        HBox barAndButton = new HBox(10, progressBarContainer, new Region(), calculateButton);
        barAndButton.setAlignment(Pos.CENTER);
        HBox.setHgrow(progressBarContainer, Priority.ALWAYS);

        // Add components to the main VBox.
        getChildren().addAll(totalStorageLabel, barAndButton);
        setSpacing(5);
        setPadding(new Insets(5));
        getStyleClass().add("memory-section");

        // Load stored settings.
        String drivePath = SettingsManager.getDrivePath();
        if (!drivePath.isEmpty()) {
            File driveFile = new File(drivePath);
            if (driveFile.exists() && driveFile.isDirectory()) {
                setDirectoryFilter(driveFile);
            }
        }
        // Use the stored threshold value (stored as a fraction).
        setThreshold(SettingsManager.getThreshold());

        // Periodically update storage usage (every second).
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateStorageFromDisks()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Initial update.
        updateStorageFromDisks();
    }

    /**
     * Aggregates storage usage data.
     * If a directory filter is set, uses that drive; otherwise aggregates across all storage devices
     * (ignoring drives under 1 GB).
     */
    public void updateStorageFromDisks() {
        long totalSpace = 0;
        long freeSpace = 0;
        if (directoryFilter != null && directoryFilter.exists() && directoryFilter.isDirectory()) {
            totalSpace = directoryFilter.getTotalSpace();
            freeSpace = directoryFilter.getFreeSpace();
        } else {
            File[] roots = File.listRoots();
            for (File root : roots) {
                // Only include devices with more than 1 GB capacity.
                if (root.getTotalSpace() > 1024L * 1024 * 1024) {
                    totalSpace += root.getTotalSpace();
                    freeSpace += root.getFreeSpace();
                }
            }
        }
        long usedSpace = totalSpace - freeSpace;
        double totalGB = totalSpace / (1024.0 * 1024 * 1024);
        currentTotalGB = totalGB;
        double usedGB = usedSpace / (1024.0 * 1024 * 1024);
        updateStorageDisplay(usedGB, totalGB);
    }

    /**
     * Updates the storage label and progress bar.
     *
     * @param usedGB  Used storage in gigabytes.
     * @param totalGB Total storage in gigabytes.
     */
    public void updateStorageDisplay(double usedGB, double totalGB) {
        totalStorageLabel.setText(String.format("Total Storage: %.1f GB / %.1f GB", usedGB, totalGB));
        memoryBar.setProgress((totalGB > 0) ? usedGB / totalGB : 0);
    }

    /**
     * Returns the Calculate button for external customization.
     */
    public Button getCalculateButton() {
        return calculateButton;
    }

    /**
     * Sets the directory filter so that storage is calculated only for that drive.
     * Passing null aggregates usage across all devices.
     *
     * @param directory The directory to filter by.
     */
    public void setDirectoryFilter(File directory) {
        this.directoryFilter = directory;
        updateStorageFromDisks();
        if (directory != null) {
            SettingsManager.setDrivePath(directory.getAbsolutePath());
        } else {
            SettingsManager.setDrivePath("");
        }
    }

    /**
     * Returns the currently set directory filter (or null if aggregating all).
     */
    public File getDirectoryFilter() {
        return directoryFilter;
    }

    /**
     * Gets the current threshold value as a fraction (0 to 1).
     */
    public double getThreshold() {
        return threshold.get();
    }

    /**
     * Sets the threshold value from a normalized fraction (0 to 1).
     * This value is saved via SettingsManager and a layout update is forced so that the marker moves.
     *
     * @param normalizedThreshold A value between 0 and 1 representing the threshold.
     */
    public void setThreshold(double normalizedThreshold) {
        System.out.println("MemorySection.setThreshold received value: " + normalizedThreshold);
        threshold.set(normalizedThreshold);
        SettingsManager.setThreshold(normalizedThreshold);
        Platform.runLater(() -> {
            memoryBar.applyCss();
            memoryBar.layout();
        });
    }

    /**
     * Returns the current total storage capacity (in GB).
     */
    public double getTotalGB() {
        return currentTotalGB;
    }

    public DoubleProperty thresholdProperty() {
        return threshold;
    }
}




