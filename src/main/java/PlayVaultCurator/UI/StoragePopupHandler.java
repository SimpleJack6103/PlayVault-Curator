package PlayVaultCurator.UI;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Window;

public class StoragePopupHandler {

    private Popup storagePopup;

    public StoragePopupHandler() {
        storagePopup = new Popup();
    }

    /**
     * Attaches and shows a popup near the given gameLabel using the MouseEvent's screen coordinates.
     * If the label is not yet attached to a scene, a listener is added to wait until it is.
     */
    public void attachHoverPopup(Label gameLabel, double freedSpace, double totalSpace, MouseEvent event) {
        // Create the content for the popup
        ProgressBar usedBar = new ProgressBar(freedSpace / totalSpace); // Green for used space
        usedBar.setPrefWidth(200);
        usedBar.setStyle("-fx-accent: #4CAF50;");

        // For demonstration, use the same value for the freed bar
        ProgressBar freedBar = new ProgressBar(freedSpace / totalSpace);
        freedBar.setPrefWidth(200);
        freedBar.setStyle("-fx-accent: #808080;");

        StackPane storageContainer = new StackPane(usedBar, freedBar);
        storageContainer.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #333333; -fx-border-radius: 5; -fx-padding: 5;");

        // Use a helper function to show the popup when the label is attached to a scene
        showPopupWhenSceneReady(gameLabel, storageContainer, event);
    }

    /**
     * Hides the popup.
     */
    public void hidePopup() {
        storagePopup.hide();
    }

    /**
     * If the label is attached to a scene, show the popup.
     * Otherwise, add a listener to wait until the label is attached.
     */
    private void showPopupWhenSceneReady(Label label, StackPane storageContainer, MouseEvent event) {
        if (label.getScene() == null) {
            // Add a listener to wait for the scene to be set
            label.sceneProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends javafx.scene.Scene> observable, javafx.scene.Scene oldScene, javafx.scene.Scene newScene) {
                    if (newScene != null) {
                        // Remove this listener after scene is set
                        observable.removeListener(this);
                        // Now that the label is in a scene, show the popup
                        Platform.runLater(() -> {
                            Window window = newScene.getWindow();
                            storagePopup.getContent().clear();
                            storagePopup.getContent().add(storageContainer);
                            storagePopup.show(label, event.getScreenX() + 10, event.getScreenY() + 10);
                        });
                    }
                }
            });
        } else {
            // Label is already in a scene
            Platform.runLater(() -> {
                Window window = label.getScene().getWindow();
                storagePopup.getContent().clear();
                storagePopup.getContent().add(storageContainer);
                storagePopup.show(label, event.getScreenX() + 10, event.getScreenY() + 10);
            });
        }
    }
}





