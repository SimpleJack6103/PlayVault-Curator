// File: PlayVaultCurator/UI/ResizeHelper.java
package PlayVaultCurator.UI;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.Region;

/**
 * Adds a resize listener to a Region (your outer pane) so that
 * the resize cursor only appears when the mouse is within
 * RESIZE_MARGIN pixels of an edge or corner.
 */
public class ResizeHelper {
    private static final int RESIZE_MARGIN = 5;

    private static double dragOffsetX;
    private static double dragOffsetY;
    private static Cursor currentCursor = Cursor.DEFAULT;

    public static void addResizeListener(Stage stage, Region root) {
        root.setOnMouseMoved(ResizeHelper::onMouseMoved);
        root.setOnMousePressed(ResizeHelper::onMousePressed);
        root.setOnMouseDragged(event -> onMouseDragged(stage, event));
        root.setOnMouseReleased(e -> root.setCursor(Cursor.DEFAULT));
    }

    private static void onMouseMoved(MouseEvent event) {
        Region root = (Region) event.getSource();
        double x = event.getX(), y = event.getY();
        double w = root.getWidth(), h = root.getHeight();

        // determine which cursor to show
        if (x < RESIZE_MARGIN && y < RESIZE_MARGIN) {
            currentCursor = Cursor.NW_RESIZE;
        } else if (x < RESIZE_MARGIN && y > h - RESIZE_MARGIN) {
            currentCursor = Cursor.SW_RESIZE;
        } else if (x > w - RESIZE_MARGIN && y < RESIZE_MARGIN) {
            currentCursor = Cursor.NE_RESIZE;
        } else if (x > w - RESIZE_MARGIN && y > h - RESIZE_MARGIN) {
            currentCursor = Cursor.SE_RESIZE;
        } else if (x < RESIZE_MARGIN) {
            currentCursor = Cursor.W_RESIZE;
        } else if (x > w - RESIZE_MARGIN) {
            currentCursor = Cursor.E_RESIZE;
        } else if (y < RESIZE_MARGIN) {
            currentCursor = Cursor.N_RESIZE;
        } else if (y > h - RESIZE_MARGIN) {
            currentCursor = Cursor.S_RESIZE;
        } else {
            currentCursor = Cursor.DEFAULT;
        }
        root.setCursor(currentCursor);
    }

    private static void onMousePressed(MouseEvent event) {
        // record difference between stage size and mouse position
        Region root = (Region) event.getSource();
        dragOffsetX = root.getWidth() - event.getX();
        dragOffsetY = root.getHeight() - event.getY();
    }

    private static void onMouseDragged(Stage stage, MouseEvent event) {
        if (currentCursor == Cursor.DEFAULT) {
            return; // no resizing
        }

        double mouseX = event.getX();
        double mouseY = event.getY();

        // east / west
        if (currentCursor == Cursor.E_RESIZE || currentCursor == Cursor.SE_RESIZE || currentCursor == Cursor.NE_RESIZE) {
            stage.setWidth(mouseX + dragOffsetX);
        }
        if (currentCursor == Cursor.W_RESIZE || currentCursor == Cursor.SW_RESIZE || currentCursor == Cursor.NW_RESIZE) {
            double newWidth = stage.getX() - event.getScreenX() + stage.getWidth();
            if (newWidth >= stage.getMinWidth()) {
                stage.setWidth(newWidth);
                stage.setX(event.getScreenX());
            }
        }

        // north / south
        if (currentCursor == Cursor.S_RESIZE || currentCursor == Cursor.SE_RESIZE || currentCursor == Cursor.SW_RESIZE) {
            stage.setHeight(mouseY + dragOffsetY);
        }
        if (currentCursor == Cursor.N_RESIZE || currentCursor == Cursor.NE_RESIZE || currentCursor == Cursor.NW_RESIZE) {
            double newHeight = stage.getY() - event.getScreenY() + stage.getHeight();
            if (newHeight >= stage.getMinHeight()) {
                stage.setHeight(newHeight);
                stage.setY(event.getScreenY());
            }
        }
    }
}

