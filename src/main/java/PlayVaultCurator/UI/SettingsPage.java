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
    private String lastValid = "";

    public SettingsPage() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.TOP_CENTER);
        container.getStyleClass().add("settings-page");

        // Title
        Label titleLabel = new Label("Memory Threshold Settings");
        titleLabel.getStyleClass().add("settings-title");

        // Threshold input + unit
        thresholdInput = new TextField();
        thresholdInput.setPromptText("Enter threshold");
        thresholdInput.getStyleClass().addAll("text-field", "threshold-input");
        thresholdInput.setMaxWidth(100);
        thresholdInput.textProperty().addListener((obs, old, ni) -> {
            try {
                Double.parseDouble(ni);
                lastValid = ni;
                errorLabel.setVisible(false);
            } catch (Exception ex) {
                thresholdInput.setText(lastValid);
            }
        });

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
        Label steamLabel = new Label("Steam ID:");
        steamLabel.getStyleClass().add("settings-label");

        steamUserIdInput = new TextField();
        steamUserIdInput.setPromptText("Enter your Steam User ID");
        steamUserIdInput.getStyleClass().add("text-field");
        steamUserIdInput.setMaxWidth(250);

        // Directory chooser
        Button chooseDir = new Button("Choose Game Directory");
        chooseDir.getStyleClass().add("button");
        chooseDir.setOnAction(e -> openDirectoryChooser());

        selectedDirectoryLabel = new Label("No directory selected");
        selectedDirectoryLabel.getStyleClass().add("settings-label");

        // Navigation buttons
        Button back = new Button("Back");
        back.getStyleClass().add("nav-button");
        back.setOnAction(e -> Main.switchToHomePage());

        Button save = new Button("Save");
        save.getStyleClass().add("save-button");
        save.setOnAction(e -> saveSettings());

        HBox navBox = new HBox(20, back, save);
        navBox.setAlignment(Pos.CENTER);

        container.getChildren().addAll(
                titleLabel,
                thresholdBox,
                errorLabel,
                steamLabel, steamUserIdInput,
                chooseDir, selectedDirectoryLabel,
                navBox
        );
        setCenter(container);

        // Preload settings
        double pct = SettingsManager.getThreshold() * 100;
        thresholdInput.setText(String.valueOf((int)pct));
        lastValid = thresholdInput.getText();

        steamUserIdInput.setText(SettingsManager.getSteamUserId());
        String dir = SettingsManager.getGameDirectory();
        if (!dir.isEmpty()) selectedDirectoryLabel.setText("Selected: " + dir);
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
        String txt = thresholdInput.getText().trim();
        double v;
        try {
            v = Double.parseDouble(txt);
        } catch (NumberFormatException ex) {
            errorLabel.setText("Must be a number");
            errorLabel.setVisible(true);
            thresholdInput.setText(lastValid);
            return;
        }
        if (v < 0) {
            errorLabel.setText("Cannot be negative");
            errorLabel.setVisible(true);
            thresholdInput.setText(lastValid);
            return;
        }

        double totalGB = Main.getMemorySection().getTotalGB();
        String unit = unitComboBox.getValue();
        if (unit.equals("%") && v > 100) {
            errorLabel.setText("Must be ≤100%");
            errorLabel.setVisible(true);
            thresholdInput.setText(lastValid);
            return;
        }
        if (unit.equals("GB") && v > totalGB) {
            errorLabel.setText("Cannot exceed " + (int) totalGB + " GB");
            errorLabel.setVisible(true);
            thresholdInput.setText(lastValid);
            return;
        }
        if (unit.equals("MB") && v > totalGB * 1024) {
            errorLabel.setText("Cannot exceed " + (int)(totalGB*1024) + " MB");
            errorLabel.setVisible(true);
            thresholdInput.setText(lastValid);
            return;
        }

        double norm = switch (unit) {
            case "%"  -> v/100.0;
            case "GB" -> v/totalGB;
            default   -> (v/1024.0)/totalGB;
        };

        Main.getMemorySection().setThreshold(norm);
        SettingsManager.setThreshold(norm);
        SettingsManager.setSteamUserId(steamUserIdInput.getText().trim());
        lastValid = txt;
    }
}











