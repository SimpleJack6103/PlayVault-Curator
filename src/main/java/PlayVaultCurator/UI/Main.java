package PlayVaultCurator.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private double windowWidth = 400;
    private double windowHeight = 300;
    private double resizeBorder = 5; // Resize detection area

    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("PlayVaultCurator");

        TitleBar titleBar = new TitleBar(window);
        HomePage homePage = new HomePage();

        BorderPane root = new BorderPane();
        root.setTop(titleBar);
        root.setCenter(homePage);

        Scene scene = new Scene(root, windowWidth, windowHeight);
        scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());

        enableWindowResize(root);

        window.setScene(scene);
        window.show();
    }

    private void enableWindowResize(BorderPane root) {
        root.setOnMouseMoved(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            double width = window.getWidth();
            double height = window.getHeight();

            if (mouseX > width - resizeBorder && mouseY > height - resizeBorder) {
                root.setCursor(javafx.scene.Cursor.NW_RESIZE);
            } else if (mouseX > width - resizeBorder) {
                root.setCursor(javafx.scene.Cursor.E_RESIZE);
            } else if (mouseY > height - resizeBorder) {
                root.setCursor(javafx.scene.Cursor.S_RESIZE);
            } else {
                root.setCursor(javafx.scene.Cursor.DEFAULT);
            }
        });

        root.setOnMouseDragged(event -> {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            double width = window.getWidth();
            double height = window.getHeight();

            if (root.getCursor() == javafx.scene.Cursor.E_RESIZE) {
                window.setWidth(mouseX);
            } else if (root.getCursor() == javafx.scene.Cursor.S_RESIZE) {
                window.setHeight(mouseY);
            } else if (root.getCursor() == javafx.scene.Cursor.NW_RESIZE) {
                window.setWidth(mouseX);
                window.setHeight(mouseY);
            }
        });
    }
}

