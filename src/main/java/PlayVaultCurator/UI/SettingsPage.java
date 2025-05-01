// File: PlayVaultCurator/UI/SettingsPage.java
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

public class SettingsPage extends BorderPane {
    private TextField thresholdInput;
    private ComboBox<String> unitComboBox;
    private TextField steamUserIdInput;
    private Label selectedDirectoryLabel;
    private Label errorLabel;

    public SettingsPage() {
        VBox settingsContainer = new VBox(10);
        settingsContainer.setPadding(new Insets(20));
        settingsContainer.setAlignment(Pos.TOP_CENTER);
        settingsContainer.getStyleClass().add("settings-page");

        // Title
        Label titleLabel = new Label("Memory Threshold Settings");
        titleLabel.getStyleClass().add("settings-title");

        // Threshold input + unit
        thresholdInput = new TextField();
        thresholdInput.setPromptText("Enter threshold");
        thresholdInput.getStyleClass().addAll("text-field", "threshold-input");

        unitComboBox = new ComboBox<>();
        unitComboBox.getItems().addAll("%", "GB", "MB");
        unitComboBox.setValue("%");
        unitComboBox.getStyleClass().add("unit-combo");

        HBox thresholdBox = new HBox(10, thresholdInput, unitComboBox);
        thresholdBox.setAlignment(Pos.CENTER);

        // Error label
        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        // Steam ID
        Label steamIdLabel = new Label("Steam User ID:");
        steamIdLabel.getStyleClass().add("settings-label");

        steamUserIdInput = new TextField();
        steamUserIdInput.setPromptText("Enter your Steam User ID");
        steamUserIdInput.getStyleClass().add("text-field");
        steamUserIdInput.setMaxWidth(250);  // 🔧 restrict width

        // Directory chooser
        Button chooseDirectoryButton = new Button("Choose Game Directory");
        chooseDirectoryButton.setOnAction(e -> openDirectoryChooser());
        selectedDirectoryLabel = new Label("No directory selected");
        selectedDirectoryLabel.getStyleClass().add("settings-label");

        // Buttons
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("nav-button");
        backButton.setOnAction(e -> Main.switchToHomePage());

        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("save-button");
        saveButton.setOnAction(e -> saveSettings());

        HBox buttonBox = new HBox(20, backButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);

        settingsContainer.getChildren().addAll(
                titleLabel,
                thresholdBox,
                errorLabel,
                steamIdLabel, steamUserIdInput,
                chooseDirectoryButton, selectedDirectoryLabel,
                buttonBox
        );
        setCenter(settingsContainer);

        // Preload settings
        double stored = SettingsManager.getThreshold();
        thresholdInput.setText(String.valueOf((int)(stored * 100)));
        steamUserIdInput.setText(SettingsManager.getSteamUserId());
        String savedDir = SettingsManager.getGameDirectory();
        if (!savedDir.isEmpty()) selectedDirectoryLabel.setText("Selected: " + savedDir);
    }

    private void openDirectoryChooser() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Game Directory");
        File dir = chooser.showDialog(null);
        if (dir != null) {
            SettingsManager.setGameDirectory(dir.getAbsolutePath());
            selectedDirectoryLabel.setText("Selected: " + dir.getAbsolutePath());
            List<Game> scanned = DirectorySearch.searchFiles(dir);
            Main.setGames(scanned);
        }
    }

    private void saveSettings() {
        errorLabel.setVisible(false);
        String text = thresholdInput.getText().trim();
        if (text.isEmpty()) {
            showError("Threshold cannot be empty.");
            return;
        }

        double value;
        try {
            value = Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            showError("Threshold must be a number.");
            return;
        }

        String unit = unitComboBox.getValue();
        if (value < 0) {
            showError("Threshold cannot be negative.");
            return;
        }

        double totalGB = Main.getMemorySection().getTotalGB();

        // Check input bounds based on unit
        if (unit.equals("%") && value > 100) {
            showError("Percentage must be ≤ 100.");
            return;
        } else if (unit.equals("GB") && value > totalGB) {
            showError("Cannot exceed total storage (" + (int) totalGB + " GB).");
            return;
        } else if (unit.equals("MB") && value > totalGB * 1024) {
            showError("Cannot exceed total storage (" + (int)(totalGB * 1024) + " MB).");
            return;
        }

        // Normalize
        double normalized;
        switch (unit) {
            case "%": normalized = value / 100.0; break;
            case "GB": normalized = totalGB > 0 ? value / totalGB : 0; break;
            case "MB": normalized = totalGB > 0 ? (value / 1024.0) / totalGB : 0; break;
            default: normalized = 0;
        }

        Main.getMemorySection().setThreshold(normalized);
        SettingsManager.setThreshold(normalized);
        SettingsManager.setSteamUserId(steamUserIdInput.getText().trim());
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
}









