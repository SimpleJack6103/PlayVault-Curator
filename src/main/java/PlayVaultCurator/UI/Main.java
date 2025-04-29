package PlayVaultCurator.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

/**
 * The entry point and primary controller for the PlayVault Curator application.
 * This class extends {@link Application} to bootstrap JavaFX, initialize the primary
 * window (Stage), and switch between the Home and Settings pages.
 */
public class Main extends Application {
    private static Stage primaryStage;
    private static final int MIN_WIDTH = 550;
    private static final int MIN_HEIGHT = 400;

    // The current MemorySection instance; accessible to the SettingsPage.
    private static MemorySection memorySection;

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

        // Custom title bar.
        TitleBar titleBar = new TitleBar(primaryStage);
        mainLayout.setTop(titleBar);

        // Create the HomePage (which in turn creates its MemorySection).
        HomePage homePage = new HomePage();
        mainLayout.setCenter(homePage);

        // Capture the MemorySection instance for access by SettingsPage.
        memorySection = homePage.getMemorySection();

        // Outer container.
        StackPane outerPane = new StackPane(mainLayout);
        outerPane.getStyleClass().add("app-root");

        Scene scene = new Scene(outerPane, MIN_WIDTH, MIN_HEIGHT);
        scene.getStylesheets().add(
                Objects.requireNonNull(Main.class.getResource("/dark-theme.css"))
                        .toExternalForm()
        );
        primaryStage.setScene(scene);

        ResizeHelper.addResizeListener(primaryStage, outerPane);
    }

    public static void switchToSettingsPage() {
        BorderPane settingsLayout = new BorderPane();
        settingsLayout.getStyleClass().add("home-page");

        TitleBar titleBar = new TitleBar(primaryStage);
        settingsLayout.setTop(titleBar);

        SettingsPage settingsPage = new SettingsPage();
        settingsLayout.setCenter(settingsPage);

        StackPane outerPane = new StackPane(settingsLayout);
        outerPane.getStyleClass().add("app-root");

        Scene scene = new Scene(outerPane, MIN_WIDTH, MIN_HEIGHT);
        scene.getStylesheets().add(
                Objects.requireNonNull(Main.class.getResource("/dark-theme.css"))
                        .toExternalForm()
        );
        primaryStage.setScene(scene);

        ResizeHelper.addResizeListener(primaryStage, outerPane);
    }

    // Getter for the MemorySection instance.
    public static MemorySection getMemorySection() {
        return memorySection;
    }

    public static void main(String[] args) {
        launch(args);
    }
}















