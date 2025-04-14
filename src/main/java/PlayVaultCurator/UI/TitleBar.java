package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * A custom title bar that replaces the default window decorations.
 * <p>
 * Provides:
 * <ul>
 *   <li>Application title text,</li>
 *   <li>Minimize, maximize/restore, and close buttons,</li>
 *   <li>Click‑and‑drag anywhere on the bar (except buttons) to move the window.</li>
 * </ul>
 * All visual styling (colors, padding, hover effects) is handled by CSS classes
 * defined in <code>dark-theme.css</code>.
 * </p>
 */
public class TitleBar extends HBox {

    /**
     * Constructs the TitleBar and hooks up window control actions.
     *
     * @param stage the primary {@link Stage} that this title bar will control
     */
    public TitleBar(Stage stage) {
        // Apply CSS class for background, size, padding, etc.
        getStyleClass().add("title-bar");

        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);
        setPadding(new Insets(5));
        prefWidthProperty().bind(stage.widthProperty());

        // Title label (CSS‐styled)
        Label title = new Label("PlayVault Curator");
        title.getStyleClass().add("title-label");

        // Spacer pushes the buttons to the right edge
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Window control buttons
        Button minimizeButton = new Button("–");
        Button maximizeButton = new Button("⬜");
        Button closeButton    = new Button("X");

        // Assign CSS class and hand cursor
        for (Button btn : new Button[]{minimizeButton, maximizeButton, closeButton}) {
            btn.getStyleClass().add("window-button");
            btn.setCursor(Cursor.HAND);
        }

        // Hook up button actions
        minimizeButton.setOnAction(e -> stage.setIconified(true));
        maximizeButton.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));
        closeButton   .setOnAction(e -> stage.close());

        getChildren().addAll(title, spacer, minimizeButton, maximizeButton, closeButton);

        // Enable click‑and‑drag window moving
        final Delta dragDelta = new Delta();
        setOnMousePressed(e -> {
            dragDelta.x = e.getSceneX();
            dragDelta.y = e.getSceneY();
        });
        setOnMouseDragged(e -> {
            if (!stage.isMaximized()) {
                stage.setX(e.getScreenX() - dragDelta.x);
                stage.setY(e.getScreenY() - dragDelta.y);
            }
        });
    }

    /**
     * Helper class to store mouse offset values during dragging.
     */
    private static class Delta {
        double x, y;
    }
}





