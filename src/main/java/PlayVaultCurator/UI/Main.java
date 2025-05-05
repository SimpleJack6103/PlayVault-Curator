// File: PlayVaultCurator/UI/Main.java
package PlayVaultCurator.UI;

import Games2Delete.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    private static Stage primaryStage;
    private static HomePage currentHomePage;
    private static MemorySection memorySection;
    private static final int MIN_WIDTH = 550, MIN_HEIGHT = 400;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("PlayVault Curator");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        switchToHomePage();
        primaryStage.show();
    }

    public static void switchToHomePage() {
        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("home-page");
        TitleBar titleBar = new TitleBar(primaryStage);
        mainLayout.setTop(titleBar);
        HomePage home = new HomePage();
        currentHomePage = home;
        mainLayout.setCenter(home);
        memorySection = home.getMemorySection();
        StackPane outer = new StackPane(mainLayout);
        outer.getStyleClass().add("app-root");
        Scene scene = new Scene(outer, MIN_WIDTH, MIN_HEIGHT);

        // ✅ Add both dark-theme and custom-scroll styles
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/dark-theme.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/custom-scroll.css")).toExternalForm());

        primaryStage.setScene(scene);
        ResizeHelper.addResizeListener(primaryStage, outer);
    }

    public static void switchToSettingsPage() {
        BorderPane layout = new BorderPane();
        layout.getStyleClass().add("home-page");
        TitleBar titleBar = new TitleBar(primaryStage);
        layout.setTop(titleBar);
        SettingsPage settings = new SettingsPage();
        layout.setCenter(settings);
        StackPane outer = new StackPane(layout);
        outer.getStyleClass().add("app-root");
        Scene scene = new Scene(outer, MIN_WIDTH, MIN_HEIGHT);

        // ✅ Add both dark-theme and custom-scroll styles
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/dark-theme.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/custom-scroll.css")).toExternalForm());

        primaryStage.setScene(scene);
        ResizeHelper.addResizeListener(primaryStage, outer);
    }

    /** Called by SettingsPage **/
    public static void setGames(List<Game> games) {
        if (currentHomePage != null) {
            currentHomePage.setGames(games);
            System.out.println("Found " + games.size() + " .exe files.");

        }
    }

    /** Exposed so SettingsPage can fire calculate **/
    public static MemorySection getMemorySection() {
        return memorySection;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
















