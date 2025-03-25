package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsPage extends BorderPane {
    private TextField thresholdInput;
    private ComboBox<String> unitComboBox;

    public SettingsPage() {
        // Create main container
        VBox settingsContainer = new VBox(10);
        settingsContainer.setPadding(new Insets(20));
        settingsContainer.setAlignment(Pos.CENTER);

        // Title Label
        Label titleLabel = new Label("Memory Threshold Settings");
        titleLabel.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Input for threshold
        thresholdInput = new TextField();
        thresholdInput.setPromptText("Enter threshold");
        thresholdInput.setMaxWidth(200);

        // Dropdown for unit selection
        unitComboBox = new ComboBox<>();
        unitComboBox.getItems().addAll("%", "GB", "MB");
        unitComboBox.setValue("%");
        unitComboBox.setMaxWidth(100);

        HBox inputBox = new HBox(10, thresholdInput, unitComboBox);
        inputBox.setAlignment(Pos.CENTER);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> Main.switchToHomePage());

        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveSettings());

        HBox buttonBox = new HBox(20, backButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Add all elements to layout
        settingsContainer.getChildren().addAll(titleLabel, inputBox, buttonBox);
        setCenter(settingsContainer);
    }

    private void saveSettings() {
        try {
            double value = Double.parseDouble(thresholdInput.getText());
            String unit = unitComboBox.getValue();
            double standardizedValue;

            switch (unit) {
                case "%":
                    standardizedValue = value; // Use percentage as-is
                    break;
                case "GB":
                    standardizedValue = value * 1024; // Convert GB to MB
                    break;
                case "MB":
                    standardizedValue = value; // Already in MB
                    break;
                default:
                    standardizedValue = value;
            }
            System.out.println("Threshold set to: " + standardizedValue + " MB");
        } catch (NumberFormatException ex) {
            System.err.println("Invalid input for memory threshold.");
        }
    }
}

