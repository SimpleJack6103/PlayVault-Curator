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
        try {
            ImageView gearIcon;
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/gear-icon.png")));
            gearIcon = new ImageView(image);
            gearIcon.setFitWidth(15);
            gearIcon.setFitHeight(15);
            settingsButton = new Button("", gearIcon);
        } catch (NullPointerException e) {
            System.err.println("Warning: gear-icon.png not found! Using fallback text.");
            settingsButton = new Button("⚙"); // Unicode fallback
        }

        settingsButton.getStyleClass().add("settings-button");
        settingsButton.setOnAction(e -> System.out.println("Settings Clicked"));

        setAlignment(Pos.TOP_RIGHT);
        setPadding(new Insets(5));
        getChildren().add(settingsButton);
    }
}


