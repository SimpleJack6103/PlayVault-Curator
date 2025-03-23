package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class TitleBar extends HBox {
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isMaximized = false;
    private double prevWidth, prevHeight, prevX, prevY;
    private final Stage window;

    public TitleBar(Stage primaryStage) {
        this.window = primaryStage;
        setStyle("-fx-background-color: #1E1E1E; -fx-padding: 5;");
        setAlignment(Pos.CENTER);

        Label title = new Label("PlayVaultCurator");
        title.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 14px;");
        HBox.setMargin(title, new Insets(0, 10, 0, 10));

        Button minButton = new Button("_");
        Button maxButton = new Button("[ ]");
        Button closeButton = new Button("X");

        String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: #E0E0E0; -fx-font-size: 12px;";
        minButton.setStyle(buttonStyle);
        maxButton.setStyle(buttonStyle);
        closeButton.setStyle(buttonStyle);

        minButton.setOnAction(e -> window.setIconified(true));
        maxButton.setOnAction(e -> toggleMaximize());
        closeButton.setOnAction(e -> window.close());

        setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        setOnMouseDragged((MouseEvent event) -> {
            if (!isMaximized) {
                window.setX(event.getScreenX() - xOffset);
                window.setY(event.getScreenY() - yOffset);
            }
        });

        HBox controls = new HBox(5, minButton, maxButton, closeButton);
        getChildren().addAll(title, controls);
        setHgrow(title, Priority.ALWAYS);
    }

    private void toggleMaximize() {
        if (isMaximized) {
            window.setX(prevX);
            window.setY(prevY);
            window.setWidth(prevWidth);
            window.setHeight(prevHeight);
            isMaximized = false;
        } else {
            prevX = window.getX();
            prevY = window.getY();
            prevWidth = window.getWidth();
            prevHeight = window.getHeight();
            window.setX(0);
            window.setY(0);
            window.setWidth(javafx.stage.Screen.getPrimary().getVisualBounds().getWidth());
            window.setHeight(javafx.stage.Screen.getPrimary().getVisualBounds().getHeight());
            isMaximized = true;
        }
    }
}

