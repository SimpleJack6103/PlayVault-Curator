package PlayVaultCurator.UI;

import PlayVaultCurator.util.DirectorySearch;
import PlayVaultCurator.util.SettingsManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.util.List;
import Games2Delete.Game;

/**
 * The SettingsPage provides the full settings UI where users can:
 * <ul>
 *   <li>Specify a memory threshold (percentage, GB, or MB),</li>
 *   <li>Enter their Steam User ID,</li>
 *   <li>Select the game installation directory,</li>
 *   <li>Save or discard changes via Back/Save buttons.</li>
 * </ul>
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

        // Preload saved settings.
        double storedThreshold = SettingsManager.getThreshold();  // stored as a fraction (e.g., 0.70)
        // We'll show it as a percentage when the unit is "%" (default).
        thresholdInput.setText(String.valueOf((int)(storedThreshold * 100)));
        steamUserIdInput.setText(SettingsManager.getSteamUserId());
        String savedDirectory = SettingsManager.getGameDirectory();
        if (!savedDirectory.isEmpty()) {
            selectedDirectoryLabel.setText("Selected: " + savedDirectory);
        }
    }

    /**
     * Opens a DirectoryChooser dialog for the user to select the game installation directory.
     * After selection, it scans the directory for games and passes the result to Main.
     */
    private void openDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Game Directory");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            selectedDirectoryLabel.setText("Selected: " + selectedDirectory.getAbsolutePath());
            // Optionally, store the chosen directory in SettingsManager.
            SettingsManager.setGameDirectory(selectedDirectory.getAbsolutePath());
            // Scan the selected directory for games.
            List<Game> scannedGames = DirectorySearch.searchFiles(selectedDirectory);
            // Pass the scanned games to Main so that HomePage may update its deletion list.
            Main.setGames(scannedGames);
        }
    }

    /**
     * Reads and validates user inputs (threshold, unit, Steam ID), converts the threshold into a normalized fraction,
     * and persists the new settings. The normalized threshold is then passed to the active MemorySection.
     */
    private void saveSettings() {
        try {
            double value = Double.parseDouble(thresholdInput.getText().trim());
            String unit = unitComboBox.getValue();
            double normalizedThreshold;
            if (unit.equals("%")) {
                normalizedThreshold = value / 100.0;
            } else if (unit.equals("GB")) {
                // Get the total storage capacity from MemorySection.
                double totalGB = Main.getMemorySection().getTotalGB();
                normalizedThreshold = totalGB > 0 ? value / totalGB : 0;
            } else { // "MB"
                double totalGB = Main.getMemorySection().getTotalGB();
                normalizedThreshold = totalGB > 0 ? (value / 1024.0) / totalGB : 0;
            }
            String steamUserId = steamUserIdInput.getText().trim();

            // Update the MemorySection's threshold so that the new setting takes effect immediately.
            Main.getMemorySection().setThreshold(normalizedThreshold);
            // Persist the Steam User ID.
            SettingsManager.setSteamUserId(steamUserId);

            System.out.println("Threshold set to: " + normalizedThreshold);
            System.out.println("Steam ID: " + steamUserId);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid input for memory threshold.");
        }
    }
}






