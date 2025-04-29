package PlayVaultCurator.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;
import java.util.List;
import Games2Delete.Game;

public class Main extends Application {
    private static Stage primaryStage;
    private static final int MIN_WIDTH = 550;
    private static final int MIN_HEIGHT = 400;

    // The current MemorySection instance; accessible to SettingsPage.
    private static MemorySection memorySection;
    // Save the current HomePage so that we can update its games list.
    private static HomePage currentHomePage;

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
        // Create the root layout.
        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("home-page");

        // Add custom title bar.
        TitleBar titleBar = new TitleBar(primaryStage);
        mainLayout.setTop(titleBar);

        // Create the HomePage.
        HomePage homePage = new HomePage();
        currentHomePage = homePage; // Store the reference.
        mainLayout.setCenter(homePage);

        // Capture the MemorySection instance for use by SettingsPage.
        memorySection = homePage.getMemorySection();

        // Wrap in an outer container.
        StackPane outerPane = new StackPane(mainLayout);
        outerPane.getStyleClass().add("app-root");

        Scene scene = new Scene(outerPane, MIN_WIDTH, MIN_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/dark-theme.css")).toExternalForm());
        primaryStage.setScene(scene);

        ResizeHelper.addResizeListener(primaryStage, outerPane);
    }

    /**
     * Switches the scene to the SettingsPage.
     */
    public static void switchToSettingsPage() {
        // Create a new layout for the settings page.
        BorderPane settingsLayout = new BorderPane();
        settingsLayout.getStyleClass().add("home-page");

        // Create the custom title bar.
        TitleBar titleBar = new TitleBar(primaryStage);
        settingsLayout.setTop(titleBar);

        // Create the SettingsPage.
        SettingsPage settingsPage = new SettingsPage();
        settingsLayout.setCenter(settingsPage);

        // Wrap in an outer container.
        StackPane outerPane = new StackPane(settingsLayout);
        outerPane.getStyleClass().add("app-root");

        Scene scene = new Scene(outerPane, MIN_WIDTH, MIN_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("/dark-theme.css")).toExternalForm());
        primaryStage.setScene(scene);

        ResizeHelper.addResizeListener(primaryStage, outerPane);
    }

    /**
     * Allows external parts of the application (like DirectorySearch) to update
     * the list of games in the HomePage.
     *
     * @param games List of Game objects to update.
     */
    public static void setGames(List<Game> games) {
        if (currentHomePage != null) {
            currentHomePage.setGames(games);
        }
    }

    // Getter for the MemorySection instance.
    public static MemorySection getMemorySection() {
        return memorySection;
    }

    public static void main(String[] args) {
        launch(args);
    }
}














