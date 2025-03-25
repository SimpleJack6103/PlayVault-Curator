package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class SettingsPanel extends VBox {
    private Button settingsButton;

    public SettingsPanel() {
        // Align to the top-right corner
        setAlignment(Pos.TOP_RIGHT);
        setSpacing(10);
        setPadding(new Insets(5)); // Ensure it doesn’t touch the window edges

        ImageView gearIcon;

        try {
            // Try to load the main gear icon
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/gear-icon.png")));
            gearIcon = new ImageView(image);
        } catch (NullPointerException e) {
            System.err.println("Warning: gear-icon.png not found! Trying fallback image...");
            try {
                // Try to load a fallback gear image
                Image fallbackImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/fallback-gear.png")));
                gearIcon = new ImageView(fallbackImage);
            } catch (NullPointerException ex) {
                System.err.println("Warning: fallback-gear.png not found! Using text instead.");
                gearIcon = null; // No image available, fallback to text
            }
        }

        // If a valid gear image exists
        if (gearIcon != null) {
            gearIcon.setFitWidth(20);
            gearIcon.setFitHeight(20);
            settingsButton = new Button("", gearIcon);
        } else {
            // Fallback to Unicode icon if no image is available
            settingsButton = new Button("⚙");
        }

        // Style the button
        settingsButton.getStyleClass().add("settings-button");

        // Set action to switch to the Settings page
        settingsButton.setOnAction(e -> Main.switchToSettingsPage());

        // Add the settings button to the panel
        getChildren().add(settingsButton);
    }
}








