package PlayVaultCurator.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {
    private static Stage primaryStage; // Keep a reference to the primary stage
    private static final int MIN_WIDTH = 550;
    private static final int MIN_HEIGHT = 400;

    @Override
    public void start(Stage stage) {
        primaryStage = stage; // Initialize primaryStage
        primaryStage.setTitle("PlayVault Curator");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);

        // Start on the Home Page
        switchToHomePage();

        primaryStage.show();
    }

    // Switch to Home Page, note that HomePage includes its own settings button.
    public static void switchToHomePage() {
        BorderPane mainLayout = new BorderPane();

        // Title bar for window controls (drag, minimize, maximize, close)
        TitleBar titleBar = new TitleBar(primaryStage);
        mainLayout.setTop(titleBar);

        // Home page content, which already includes the SettingsPanel on the right.
        HomePage homePage = new HomePage();
        mainLayout.setCenter(homePage);

        // If HomePage already contains a settings button, don't duplicate it by adding another here!
        // Outer pane for styling and to capture resize events.
        StackPane outerPane = new StackPane(mainLayout);
        outerPane.setStyle("-fx-background-color: #121212; -fx-border-color: #BB86FC; -fx-border-width: 2px;");

        Scene scene = new Scene(outerPane, MIN_WIDTH, MIN_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/dark-theme.css")).toExternalForm());
        primaryStage.setScene(scene);

        // Add resize functionality to the entire root region
        ResizeHelper.addResizeListener(primaryStage, outerPane);
    }

    // Switch to the Settings Page (this page might have its own content layout, no duplicate settings button here)
    public static void switchToSettingsPage() {
        BorderPane settingsLayout = new BorderPane();

        // Title bar remains at the top
        TitleBar titleBar = new TitleBar(primaryStage);
        settingsLayout.setTop(titleBar);

        // Settings page content
        SettingsPage settingsPage = new SettingsPage();
        settingsLayout.setCenter(settingsPage);

        // Outer pane for styling and to capture resize events
        StackPane outerPane = new StackPane(settingsLayout);
        outerPane.setStyle("-fx-background-color: #121212; -fx-border-color: #BB86FC; -fx-border-width: 4px;");

        Scene scene = new Scene(outerPane, MIN_WIDTH, MIN_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/dark-theme.css")).toExternalForm());
        primaryStage.setScene(scene);

        // Add resize functionality
        ResizeHelper.addResizeListener(primaryStage, outerPane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}









