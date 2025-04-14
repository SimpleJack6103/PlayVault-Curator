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
 * <p>
 * This class extends {@link Application} to bootstrap JavaFX, initialize the primary
 * window (Stage), and switch between the Home and Settings pages. All UI styling
 * is driven via the {@code dark-theme.css} stylesheet.
 * </p>
 * <h2>Responsibilities:</h2>
 * <ul>
 *   <li>Configure and show the primary {@link Stage}.</li>
 *   <li>Provide static methods to switch between the Home and Settings scenes:
 *       {@link #switchToHomePage()} and {@link #switchToSettingsPage()}.</li>
 *   <li>Maintain a reference to the primary Stage for use by other UI components.</li>
 *   <li>Enforce minimum window dimensions and an undecorated style (custom title bar).</li>
 *   <li>Load and apply the global CSS stylesheet.</li>
 *   <li>Attach resize listeners to allow border resizing.</li>
 * </ul>
 */
public class Main extends Application {
    /**
     * The primary application window. Stored statically so other UI components
     * (e.g., {@link TitleBar}) can control minimize/maximize/close operations.
     */
    private static Stage primaryStage;

    /**
     * Minimum allowed width for the application window.
     */
    private static final int MIN_WIDTH = 550;

    /**
     * Minimum allowed height for the application window.
     */
    private static final int MIN_HEIGHT = 400;

    /**
     * JavaFX lifecycle method. Called when the application is launched.
     * <p>
     * Configures the {@code primaryStage} with title, style, and minimum dimensions,
     * then displays the Home page.
     * </p>
     *
     * @param stage the primary stage provided by the JavaFX runtime
     */
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

    /**
     * Builds and displays the Home page scene.
     * <p>
     * This method:
     * <ol>
     *   <li>Creates a {@link BorderPane} root and applies the {@code home-page} CSS class.</li>
     *   <li>Adds a custom {@link TitleBar} to the top region.</li>
     *   <li>Instantiates and centers the {@link HomePage} content.</li>
     *   <li>Wraps everything in a {@link StackPane} with the {@code app-root} CSS class
     *       (providing the purple border).</li>
     *   <li>Creates a {@link Scene}, loads the {@code dark-theme.css} stylesheet, and sets it on the stage.</li>
     *   <li>Attaches window resizing behavior via {@link ResizeHelper}.</li>
     * </ol>
     */
    public static void switchToHomePage() {
        // Root layout for Home
        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("home-page");

        // Custom title bar (drag, minimize, maximize, close)
        TitleBar titleBar = new TitleBar(primaryStage);
        mainLayout.setTop(titleBar);

        // Main content area
        HomePage homePage = new HomePage();
        mainLayout.setCenter(homePage);

        // Outer container for styling (purple border)
        StackPane outerPane = new StackPane(mainLayout);
        outerPane.getStyleClass().add("app-root");

        // Create scene and apply stylesheet
        Scene scene = new Scene(outerPane, MIN_WIDTH, MIN_HEIGHT);
        scene.getStylesheets().add(
                Objects.requireNonNull(Main.class.getResource("/dark-theme.css"))
                        .toExternalForm()
        );
        primaryStage.setScene(scene);

        // Enable border-based resizing
        ResizeHelper.addResizeListener(primaryStage, outerPane);
    }

    /**
     * Builds and displays the Settings page scene.
     * <p>
     * Follows the same structure as {@link #switchToHomePage()}, except that
     * the center content is a {@link SettingsPage} instead of {@link HomePage}.
     * </p>
     */
    public static void switchToSettingsPage() {
        // Root layout for Settings
        BorderPane settingsLayout = new BorderPane();
        settingsLayout.getStyleClass().add("home-page");

        // Title bar remains the same
        TitleBar titleBar = new TitleBar(primaryStage);
        settingsLayout.setTop(titleBar);

        // Settings content
        SettingsPage settingsPage = new SettingsPage();
        settingsLayout.setCenter(settingsPage);

        // Outer container with purple border
        StackPane outerPane = new StackPane(settingsLayout);
        outerPane.getStyleClass().add("app-root");

        // Scene with global stylesheet
        Scene scene = new Scene(outerPane, MIN_WIDTH, MIN_HEIGHT);
        scene.getStylesheets().add(
                Objects.requireNonNull(Main.class.getResource("/dark-theme.css"))
                        .toExternalForm()
        );
        primaryStage.setScene(scene);

        // Enable resizing
        ResizeHelper.addResizeListener(primaryStage, outerPane);
    }

    /**
     * Standard Java entry point. Launches the JavaFX application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }
}















