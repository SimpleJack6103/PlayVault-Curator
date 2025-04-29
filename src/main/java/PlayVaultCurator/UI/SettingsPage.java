package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import java.io.File;
import PlayVaultCurator.util.SettingsManager;

/**
 * The SettingsPage provides full settings UI where users can:
 *   • Specify a memory threshold (%, GB, or MB),
 *   • Enter their Steam User ID,
 *   • Select the game installation directory,
 *   • Save or discard changes via Back/Save buttons.
 *
 * All controls are styled via CSS classes defined in dark-theme.css.
 */
public class SettingsPage extends BorderPane {
    private TextField thresholdInput;
    private ComboBox<String> unitComboBox;
    private TextField steamUserIdInput;
    private Label selectedDirectoryLabel;

    public SettingsPage() {
        VBox settingsContainer = new VBox(15);
        settingsContainer.setPadding(new Insets(20));
        settingsContainer.setAlignment(Pos.CENTER);
        settingsContainer.getStyleClass().add("settings-page");

        // Threshold section
        Label titleLabel = new Label("Memory Threshold Settings");
        titleLabel.getStyleClass().add("settings-title");

        thresholdInput = new TextField();
        thresholdInput.setPromptText("Enter threshold");
        thresholdInput.setMaxWidth(200);
        thresholdInput.getStyleClass().addAll("text-field", "threshold-input");

        unitComboBox = new ComboBox<>();
        unitComboBox.getItems().addAll("%", "GB", "MB");
        unitComboBox.setValue("%");
        unitComboBox.setMaxWidth(100);
        unitComboBox.getStyleClass().add("unit-combo");

        HBox thresholdBox = new HBox(10, thresholdInput, unitComboBox);
        thresholdBox.setAlignment(Pos.CENTER);

        // Steam User ID section
        Label steamIdLabel = new Label("Steam User ID:");
        steamIdLabel.getStyleClass().add("settings-label");

        steamUserIdInput = new TextField();
        steamUserIdInput.setPromptText("Enter your Steam User ID");
        steamUserIdInput.setMaxWidth(200);
        steamUserIdInput.getStyleClass().add("text-field");

        VBox steamBox = new VBox(5, steamIdLabel, steamUserIdInput);
        steamBox.setAlignment(Pos.CENTER);

        // Directory chooser section
        Button chooseDirectoryButton = new Button("Choose Game Directory");
        chooseDirectoryButton.getStyleClass().add("button");
        chooseDirectoryButton.setOnAction(e -> openDirectoryChooser());

        selectedDirectoryLabel = new Label("No directory selected");
        selectedDirectoryLabel.getStyleClass().add("settings-label");

        VBox directoryBox = new VBox(5, chooseDirectoryButton, selectedDirectoryLabel);
        directoryBox.setAlignment(Pos.CENTER);

        // Navigation buttons
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("nav-button");
        backButton.setOnAction(e -> Main.switchToHomePage());

        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("save-button");
        saveButton.setOnAction(e -> saveSettings());

        HBox buttonBox = new HBox(20, backButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Assemble all sections
        settingsContainer.getChildren().addAll(
                titleLabel,
                thresholdBox,
                steamBox,
                directoryBox,
                buttonBox
        );
        setCenter(settingsContainer);

        // Preload saved settings:
        double storedThreshold = SettingsManager.getThreshold();
        if (unitComboBox.getValue().equals("%")) {
            thresholdInput.setText(String.valueOf((int) (storedThreshold * 100)));
        } else {
            thresholdInput.setText(String.valueOf(storedThreshold));
        }
        steamUserIdInput.setText(SettingsManager.getSteamUserId());
        String savedDirectory = SettingsManager.getGameDirectory();
        if (!savedDirectory.isEmpty()) {
            selectedDirectoryLabel.setText("Selected: " + savedDirectory);
        }
    }

    private void openDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Game Directory");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            String path = selectedDirectory.getAbsolutePath();
            selectedDirectoryLabel.setText("Selected: " + path);
            // Immediately persist this selection.
            SettingsManager.setGameDirectory(path);
        }
    }

    private void saveSettings() {
        try {
            double value = Double.parseDouble(thresholdInput.getText().trim());
            String unit = unitComboBox.getValue();
            double fraction = 0;

            // Get the current MemorySection's total storage.
            MemorySection memSection = Main.getMemorySection();
            double totalGB = memSection.getTotalGB();

            if (unit.equals("%")) {
                fraction = value / 100.0;
                System.out.println("Threshold set to: " + value + "% (normalized: " + fraction + ")");
            } else if (unit.equals("GB")) {
                if (totalGB > 0) {
                    fraction = value / totalGB;
                }
                System.out.println("Threshold set to: " + value + " GB (normalized: " + fraction + ")");
            } else if (unit.equals("MB")) {
                if (totalGB > 0) {
                    fraction = (value / 1024.0) / totalGB;
                }
                System.out.println("Threshold set to: " + value + " MB (normalized: " + fraction + ")");
            }

            String steamUserId = steamUserIdInput.getText().trim();
            System.out.println("Steam ID: " + steamUserId);
            // Save the Steam ID.
            SettingsManager.setSteamUserId(steamUserId);

            // Update the MemorySection's threshold.
            Main.getMemorySection().setThreshold(fraction);

            // If you also want to persist other changes immediately, add additional calls.
        } catch (NumberFormatException ex) {
            System.err.println("Invalid input for memory threshold.");
        }
    }
}






