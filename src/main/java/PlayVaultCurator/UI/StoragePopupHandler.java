package PlayVaultCurator.UI;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
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
     * This version requires a desiredUsed parameter.
     *
     * @param gameLabel   The label representing the game.
     * @param freedSpace  Memory that would be freed.
     * @param totalSpace  Current total used memory.
     * @param desiredUsed The threshold (target used memory after deletion).
     * @param event       The mouse event triggering the popup.
     */
    public void attachHoverPopup(Label gameLabel, double freedSpace, double totalSpace, double desiredUsed, MouseEvent event) {
        // Create an instance of the custom MemoryBarPopup control.
        MemoryBarPopup memoryBarPopup = new MemoryBarPopup(totalSpace, freedSpace, desiredUsed);

        // Wrap the memory bar in a styled container.
        StackPane storageContainer = new StackPane(memoryBarPopup);
        storageContainer.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #333333; -fx-border-radius: 5; -fx-padding: 5;");

        // Show the popup when the label is attached to a scene.
        showPopupWhenSceneReady(gameLabel, storageContainer, event);
    }

    /**
     * Overloaded method for attaching the popup, providing a default value for desiredUsed.
     * The default is calculated as totalSpace - freedSpace (i.e. used memory after deletion).
     *
     * @param gameLabel  The label representing the game.
     * @param freedSpace Memory that would be freed.
     * @param totalSpace Current total used memory.
     * @param event      The mouse event triggering the popup.
     */
    public void attachHoverPopup(Label gameLabel, double freedSpace, double totalSpace, MouseEvent event) {
        double defaultDesiredUsed = totalSpace - freedSpace; // Default desired used memory
        attachHoverPopup(gameLabel, freedSpace, totalSpace, defaultDesiredUsed, event);
    }

    /**
     * Hides the popup.
     */
    public void hidePopup() {
        storagePopup.hide();
    }

    /**
     * If the label is attached to a scene, shows the popup; otherwise, waits until it is attached.
     *
     * @param label            The label to which the popup is anchored.
     * @param storageContainer The container node holding the memory bar.
     * @param event            The triggering mouse event.
     */
    private void showPopupWhenSceneReady(Label label, StackPane storageContainer, MouseEvent event) {
        if (label.getScene() == null) {
            label.sceneProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends javafx.scene.Scene> observable, javafx.scene.Scene oldScene,
                                    javafx.scene.Scene newScene) {
                    if (newScene != null) {
                        observable.removeListener(this);
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
            Platform.runLater(() -> {
                Window window = label.getScene().getWindow();
                storagePopup.getContent().clear();
                storagePopup.getContent().add(storageContainer);
                storagePopup.show(label, event.getScreenX() + 10, event.getScreenY() + 10);
            });
        }
    }
}





