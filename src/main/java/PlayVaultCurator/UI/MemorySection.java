package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * MemorySection displays current memory usage stats and provides a button
 * to trigger game deletion suggestions.
 * <p>
 * Located at the bottom of the {@link HomePage}, this component contains:
 * <ul>
 *   <li>A label showing "used / total" GB storage,</li>
 *   <li>A green progress bar visualizing that usage,</li>
 *   <li>A "Calculate" button that triggers an action (e.g. generate suggestions).</li>
 * </ul>
 * <p>
 * All styling is controlled via the "dark-theme.css" stylesheet.
 */
public class MemorySection extends VBox {
    private Label totalStorageLabel;
    private ProgressBar memoryBar;
    private Button calculateButton;

    /**
     * Constructs a MemorySection with placeholder values.
     * The progress bar starts at 60% (30 GB / 50 GB).
     * <p>
     * TODO: Hook up to real data.
     */
    public MemorySection() {
        totalStorageLabel = new Label("Total Storage: 30.0 GB / 50.0 GB");
        totalStorageLabel.getStyleClass().add("label");

        memoryBar = new ProgressBar(0.6);
        memoryBar.getStyleClass().add("memory-bar");
        memoryBar.setMaxWidth(Double.MAX_VALUE);

        calculateButton = new Button("Calculate");
        calculateButton.getStyleClass().add("calculate-button");
        calculateButton.setOnAction(e -> {
            System.out.println("Calculating...");
            // TODO: Connect this to the algorithm team’s logic
        });

        HBox barAndButton = new HBox(10);
        barAndButton.setAlignment(Pos.CENTER);
        barAndButton.getChildren().addAll(memoryBar, new Region(), calculateButton);
        HBox.setHgrow(barAndButton.getChildren().get(1), Priority.ALWAYS);
        HBox.setHgrow(memoryBar, Priority.ALWAYS);

        getChildren().addAll(totalStorageLabel, barAndButton);
        setSpacing(5);
        setPadding(new Insets(5));
        getStyleClass().add("memory-section");
    }

    /**
     * @return the "Calculate" button so its action can be overridden externally if needed.
     */
    public Button getCalculateButton() {
        return calculateButton;
    }

    /**
     * Updates the label and progress bar based on actual memory usage.
     *
     * @param usedGB  amount of memory currently used (in gigabytes)
     * @param totalGB total available memory (in gigabytes)
     */
    public void updateStorageDisplay(double usedGB, double totalGB) {
        totalStorageLabel.setText(String.format("Total Storage: %.1f GB / %.1f GB", usedGB, totalGB));
        memoryBar.setProgress(usedGB / totalGB);
    }
}





