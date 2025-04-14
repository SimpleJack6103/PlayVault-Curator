package PlayVaultCurator.UI;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * ResizeHelper enables manual resizing of undecorated JavaFX windows.
 * <p>
 * It uses mouse position to detect if you're near an edge or corner and
 * changes the cursor accordingly, resizing the window when dragged.
 * <p>
 * This is used in {@link Main} on the outer container.
 */
public class ResizeHelper {
    private static final int RESIZE_MARGIN = 5;

    /**
     * Adds resize detection and resizing logic to a root region of the stage.
     *
     * @param stage the stage to resize
     * @param root  the root region that receives the mouse events
     */
    public static void addResizeListener(Stage stage, Region root) {
        root.setOnMouseMoved(event -> {
            if (isMouseWithinResizeMargin(event, stage)) {
                root.setCursor(getCursorForEdge(event, stage));
            } else {
                root.setCursor(Cursor.DEFAULT);
            }
        });

        root.setOnMouseDragged(event -> {
            double mouseX = event.getScreenX();
            double mouseY = event.getScreenY();
            double stageX = stage.getX();
            double stageY = stage.getY();
            double stageWidth = stage.getWidth();
            double stageHeight = stage.getHeight();

            Cursor cursor = root.getCursor();

            if (cursor == Cursor.E_RESIZE) {
                stage.setWidth(mouseX - stageX);
            } else if (cursor == Cursor.S_RESIZE) {
                stage.setHeight(mouseY - stageY);
            } else if (cursor == Cursor.SE_RESIZE) {
                stage.setWidth(mouseX - stageX);
                stage.setHeight(mouseY - stageY);
            } else if (cursor == Cursor.W_RESIZE) {
                double newWidth = stageWidth - (mouseX - stageX);
                if (newWidth > stage.getMinWidth()) {
                    stage.setX(mouseX);
                    stage.setWidth(newWidth);
                }
            } else if (cursor == Cursor.N_RESIZE) {
                double newHeight = stageHeight - (mouseY - stageY);
                if (newHeight > stage.getMinHeight()) {
                    stage.setY(mouseY);
                    stage.setHeight(newHeight);
                }
            } else if (cursor == Cursor.NW_RESIZE) {
                double newWidth = stageWidth - (mouseX - stageX);
                double newHeight = stageHeight - (mouseY - stageY);
                if (newWidth > stage.getMinWidth()) {
                    stage.setX(mouseX);
                    stage.setWidth(newWidth);
                }
                if (newHeight > stage.getMinHeight()) {
                    stage.setY(mouseY);
                    stage.setHeight(newHeight);
                }
            } else if (cursor == Cursor.NE_RESIZE) {
                double newHeight = stageHeight - (mouseY - stageY);
                if (newHeight > stage.getMinHeight()) {
                    stage.setY(mouseY);
                    stage.setHeight(newHeight);
                }
                stage.setWidth(mouseX - stageX);
            } else if (cursor == Cursor.SW_RESIZE) {
                double newWidth = stageWidth - (mouseX - stageX);
                if (newWidth > stage.getMinWidth()) {
                    stage.setX(mouseX);
                    stage.setWidth(newWidth);
                }
                stage.setHeight(mouseY - stageY);
            }
        });
    }

    private static boolean isMouseWithinResizeMargin(MouseEvent event, Stage stage) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();
        double width = stage.getWidth();
        double height = stage.getHeight();

        return (mouseX < RESIZE_MARGIN || mouseX > width - RESIZE_MARGIN ||
                mouseY < RESIZE_MARGIN || mouseY > height - RESIZE_MARGIN);
    }

    private static Cursor getCursorForEdge(MouseEvent event, Stage stage) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();
        double width = stage.getWidth();
        double height = stage.getHeight();
        boolean left = mouseX < RESIZE_MARGIN;
        boolean right = mouseX > width - RESIZE_MARGIN;
        boolean top = mouseY < RESIZE_MARGIN;
        boolean bottom = mouseY > height - RESIZE_MARGIN;

        if (left && top) return Cursor.NW_RESIZE;
        else if (right && top) return Cursor.NE_RESIZE;
        else if (left && bottom) return Cursor.SW_RESIZE;
        else if (right && bottom) return Cursor.SE_RESIZE;
        else if (left) return Cursor.W_RESIZE;
        else if (right) return Cursor.E_RESIZE;
        else if (top) return Cursor.N_RESIZE;
        else return Cursor.S_RESIZE;
    }
}
