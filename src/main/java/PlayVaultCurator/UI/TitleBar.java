package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class TitleBar extends HBox {
    private double dragOffsetX = 0;
    private double dragOffsetY = 0;
    private static final int TITLE_BAR_HEIGHT = 30;

    public TitleBar(Stage stage) {
        // Fix the height
        setMinHeight(TITLE_BAR_HEIGHT);
        setPrefHeight(TITLE_BAR_HEIGHT);
        setMaxHeight(TITLE_BAR_HEIGHT);

        setPadding(new Insets(5, 10, 5, 10));
        setSpacing(10);
        setStyle("-fx-background-color: #1E1E1E;");
        setAlignment(Pos.CENTER_LEFT);

        // Title label on the left
        Label title = new Label("PlayVault Curator");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        // Spacer to push buttons to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Minimize button
        Button minimizeButton = new Button("_");
        minimizeButton.getStyleClass().add("window-button");
        minimizeButton.setOnAction(e -> stage.setIconified(true));

        // Maximize button
        Button maximizeButton = new Button("⬜");
        maximizeButton.getStyleClass().add("window-button");
        maximizeButton.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));

        // Close button
        Button closeButton = new Button("X");
        closeButton.getStyleClass().add("window-button");
        closeButton.setOnAction(e -> stage.close());

        getChildren().addAll(title, spacer, minimizeButton, maximizeButton, closeButton);

        // Entire TitleBar (except buttons) is draggable
        setOnMousePressed(e -> {
            dragOffsetX = e.getSceneX();
            dragOffsetY = e.getSceneY();
            setCursor(Cursor.MOVE);
        });
        setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - dragOffsetX);
            stage.setY(e.getScreenY() - dragOffsetY);
        });
        setOnMouseReleased(e -> setCursor(Cursor.DEFAULT));
    }
}

