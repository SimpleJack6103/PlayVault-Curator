package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * A compact panel displaying a gear icon button that, when clicked,
 * switches the application to the SettingsPage.
 * <p>
 * Tries to load "/gear-icon.png" from resources, falls back to
 * "/fallback-gear.png", then to a Unicode ⚙ character if neither is found.
 * </p>
 */
public class SettingsPanel extends VBox {
    private Button settingsButton;

    /**
     * Constructs the SettingsPanel, loads the gear icon (or fallback),
     * and registers the click handler to open the settings scene.
     */
    public SettingsPanel() {
        setAlignment(Pos.TOP_RIGHT);
        setSpacing(10);
        setPadding(new Insets(5));
        getStyleClass().add("settings-panel");

        ImageView gearIcon = loadGearIcon();
        if (gearIcon != null) {
            gearIcon.setFitWidth(20);
            gearIcon.setFitHeight(20);
            settingsButton = new Button("", gearIcon);
        } else {
            settingsButton = new Button("⚙");
        }
        settingsButton.getStyleClass().add("settings-button");
        settingsButton.setOnAction(e -> Main.switchToSettingsPage());

        getChildren().add(settingsButton);
    }

    /**
     * Attempts to load the primary or fallback gear icon from resources.
     *
     * @return an ImageView of the gear icon, or null if not found
     */
    private ImageView loadGearIcon() {
        try {
            return new ImageView(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/gear-icon.png"))));
        } catch (Exception ignored) {
            try {
                return new ImageView(new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("/fallback-gear.png"))));
            } catch (Exception ignored2) {
                System.err.println("Warning: No gear icon found; using text fallback.");
                return null;
            }
        }
    }
}












