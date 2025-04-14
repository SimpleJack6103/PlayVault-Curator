package PlayVaultCurator.UI;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * Handles hover popups for displaying dynamic memory usage
 * using a custom MemoryBarPopup visualization.
 */
public class StoragePopupHandler {

    private final Popup storagePopup;

    public StoragePopupHandler() {
        storagePopup = new Popup();
    }

    /**
     * Attaches a popup near the hovered game label.
     * Shows total used memory, estimated freed space, and target threshold.
     *
     * @param gameLabel    Label to attach the popup to.
     * @param freedSpace   Memory that would be freed.
     * @param totalSpace   Current used memory.
     * @param desiredUsed  Target memory after deletion.
     * @param event        Triggering mouse event for location.
     */
    public void attachHoverPopup(Label gameLabel,
                                 double freedSpace,
                                 double totalSpace,
                                 double desiredUsed,
                                 MouseEvent event) {
        MemoryBarPopup memoryBarPopup = new MemoryBarPopup(totalSpace, freedSpace, desiredUsed);

        // Use CSS class instead of inline style
        StackPane container = new StackPane(memoryBarPopup);
        container.getStyleClass().add("memory-popup");

        showPopupWhenSceneReady(gameLabel, container, event);
    }

    /**
     * Overloaded convenience method — assumes desired used memory = total - freed.
     */
    public void attachHoverPopup(Label gameLabel,
                                 double freedSpace,
                                 double totalSpace,
                                 MouseEvent event) {
        double desiredUsed = totalSpace - freedSpace;
        attachHoverPopup(gameLabel, freedSpace, totalSpace, desiredUsed, event);
    }

    /**
     * Hides the current popup.
     */
    public void hidePopup() {
        storagePopup.hide();
    }

    /**
     * Waits for the label to be part of the scene, then shows the popup at the correct screen position.
     */
    private void showPopupWhenSceneReady(Label label,
                                         StackPane container,
                                         MouseEvent event) {
        if (label.getScene() == null) {
            label.sceneProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends javafx.scene.Scene> observable,
                                    javafx.scene.Scene oldScene,
                                    javafx.scene.Scene newScene) {
                    if (newScene != null) {
                        observable.removeListener(this);
                        Platform.runLater(() -> showPopup(label, container, event));
                    }
                }
            });
        } else {
            Platform.runLater(() -> showPopup(label, container, event));
        }
    }

    private void showPopup(Label label,
                           StackPane container,
                           MouseEvent event) {
        Window window = label.getScene().getWindow();
        storagePopup.getContent().setAll(container);
        storagePopup.show(window, event.getScreenX() + 10, event.getScreenY() + 10);
    }
}









