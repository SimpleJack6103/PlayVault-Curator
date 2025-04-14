package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;

import java.io.File;

/**
 * The SettingsPage provides the full settings UI where users can:
 * <ul>
 *   <li>Specify a memory threshold (percentage, GB, or MB),</li>
 *   <li>Enter their Steam User ID,</li>
 *   <li>Select the game installation directory,</li>
 *   <li>Save or discard changes via Back/Save buttons.</li>
 * </ul>
 * <p>
 * All controls are styled via CSS classes defined in <code>dark-theme.css</code>.
 * </p>
 */
public class SettingsPage extends BorderPane {
    private TextField thresholdInput;
    private ComboBox<String> unitComboBox;
    private TextField steamUserIdInput;
    private Label selectedDirectoryLabel;

    /**
     * Constructs the SettingsPage, laying out:
     * <ul>
     *   <li>Memory threshold controls,</li>
     *   <li>Steam User ID input,</li>
     *   <li>Directory chooser,</li>
     *   <li>Navigation buttons (Back/Save).</li>
     * </ul>
     * <p>
     * TODO: Hook into actual application logic for saving settings.
     * </p>
     */
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
    }

    /**
     * Opens a {@link DirectoryChooser} dialog to let the user select
     * the game installation directory. Updates the {@code selectedDirectoryLabel}
     * with the chosen path.
     * <p>
     * TODO: Pass the selected path to the file scanning component.
     * </p>
     */
    private void openDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Game Directory");
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            selectedDirectoryLabel.setText("Selected: " + selectedDirectory.getAbsolutePath());
        }
    }

    /**
     * Reads and validates user inputs (threshold, unit, Steam ID),
     * standardizes the threshold to MB, and prints values.
     * <p>
     * TODO: Replace print statements with actual persistence/integration logic.
     * </p>
     */
    private void saveSettings() {
        try {
            double value = Double.parseDouble(thresholdInput.getText());
            String unit = unitComboBox.getValue();
            double standardizedValue;
            switch (unit) {
                case "%": standardizedValue = value; break;
                case "GB": standardizedValue = value * 1024; break;
                case "MB": default: standardizedValue = value; break;
            }
            String steamUserId = steamUserIdInput.getText();
            System.out.println("Threshold set to: " + standardizedValue + " MB");
            System.out.println("Steam ID: " + steamUserId);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid input for memory threshold.");
        }
    }
}







