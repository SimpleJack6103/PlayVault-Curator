// File: PlayVaultCurator/UI/SettingsPage.java
package PlayVaultCurator.UI;

import PlayVaultCurator.UI.Main;                // ← correct import of Main
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
 *   <li>Specify a memory threshold,</li>
 *   <li>Enter their Steam User ID,</li>
 *   <li>Select the game installation directory,</li>
 *   <li>Save or go Back.</li>
 * </ul>
 */
public class SettingsPage extends BorderPane {
    private TextField thresholdInput;
    private ComboBox<String> unitComboBox;
    private TextField steamUserIdInput;
    private Label selectedDirectoryLabel;

    public SettingsPage() {
        // container for all of your controls
        VBox settingsContainer = new VBox(15);
        settingsContainer.setPadding(new Insets(20));
        settingsContainer.setAlignment(Pos.CENTER);
        settingsContainer.getStyleClass().add("settings-page");

        // --- Title ---
        Label titleLabel = new Label("Memory Threshold Settings");
        titleLabel.getStyleClass().add("settings-title");

        // --- Threshold input + unit ---
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

        // --- Steam User ID ---
        Label steamIdLabel = new Label("Steam User ID:");
        steamIdLabel.getStyleClass().add("settings-label");
        steamUserIdInput = new TextField();
        steamUserIdInput.setPromptText("Enter your Steam User ID");
        steamUserIdInput.setMaxWidth(200);
        steamUserIdInput.getStyleClass().add("text-field");

        VBox steamBox = new VBox(5, steamIdLabel, steamUserIdInput);
        steamBox.setAlignment(Pos.CENTER);

        // --- Directory chooser ---
        Button chooseDirectoryButton = new Button("Choose Game Directory");
        chooseDirectoryButton.getStyleClass().add("button");
        chooseDirectoryButton.setOnAction(e -> openDirectoryChooser());

        selectedDirectoryLabel = new Label("No directory selected");
        selectedDirectoryLabel.getStyleClass().add("settings-label");

        VBox directoryBox = new VBox(5, chooseDirectoryButton, selectedDirectoryLabel);
        directoryBox.setAlignment(Pos.CENTER);

        // --- Navigation buttons ---
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("nav-button");
        backButton.setOnAction(e -> Main.switchToHomePage());

        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("save-button");
        saveButton.setOnAction(e -> saveSettings());

        HBox buttonBox = new HBox(20, backButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);

        // assemble all into the VBox
        settingsContainer.getChildren().addAll(
                titleLabel,
                thresholdBox,
                steamBox,
                directoryBox,
                buttonBox
        );

        // put that VBox into this BorderPane’s center
        setCenter(settingsContainer);

        // preload saved settings
        double storedThreshold = SettingsManager.getThreshold();
        thresholdInput.setText(String.valueOf((int)(storedThreshold * 100)));
        steamUserIdInput.setText(SettingsManager.getSteamUserId());
        String savedDirectory = SettingsManager.getGameDirectory();
        if (!savedDirectory.isEmpty()) {
            selectedDirectoryLabel.setText("Selected: " + savedDirectory);
        }
    }

    /**
     * Opens a directory chooser, scans for games, injects into HomePage,
     * then fires calculate so suggestions appear immediately.
     */
    private void openDirectoryChooser() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Game Directory");
        File dir = chooser.showDialog(null);
        if (dir != null) {
            selectedDirectoryLabel.setText("Selected: " + dir.getAbsolutePath());
            SettingsManager.setGameDirectory(dir.getAbsolutePath());

            List<Game> scannedGames = DirectorySearch.searchFiles(dir);
            Main.setGames(scannedGames);

            // immediately recalc & display deletion suggestions
            Main.getMemorySection().getCalculateButton().fire();
        }
    }

    /**
     * Reads threshold & Steam ID inputs, normalizes & persists them,
     * and updates the MemorySection threshold immediately.
     */
    private void saveSettings() {
        try {
            double value = Double.parseDouble(thresholdInput.getText().trim());
            String unit = unitComboBox.getValue();
            double normalized;
            if (unit.equals("%")) {
                normalized = value / 100.0;
            } else if (unit.equals("GB")) {
                normalized = value / Main.getMemorySection().getTotalGB();
            } else {
                normalized = (value / 1024.0) / Main.getMemorySection().getTotalGB();
            }
            Main.getMemorySection().setThreshold(normalized);
            SettingsManager.setSteamUserId(steamUserIdInput.getText().trim());
            System.out.println("Threshold set to: " + normalized);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid threshold input.");
        }
    }
}







