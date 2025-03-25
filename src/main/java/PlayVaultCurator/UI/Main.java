package PlayVaultCurator.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PlayVault Curator");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setMinWidth(550);
        primaryStage.setMinHeight(400);

        BorderPane mainLayout = new BorderPane();
        TitleBar titleBar = new TitleBar();
        mainLayout.setTop(titleBar);
        HomePage homePage = new HomePage();
        mainLayout.setCenter(homePage);

        StackPane outerPane = new StackPane(mainLayout);
        outerPane.setStyle("-fx-background-color: #121212; -fx-border-color: #333333; -fx-border-width: 2px;");

        Scene scene = new Scene(outerPane, 550, 400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/dark-theme.css")).toExternalForm());
        primaryStage.setScene(scene);

        ResizeHelper.addResizeListener(primaryStage, outerPane);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}











