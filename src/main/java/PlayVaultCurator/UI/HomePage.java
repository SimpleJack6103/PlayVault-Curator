package PlayVaultCurator.UI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.layout.Priority;

/**
 * The main content pane for the application's Home Page.
 * <p>
 * This {@link BorderPane} arranges:
 * <ul>
 *   <li>A {@link SettingsPanel} on the right,</li>
 *   <li>A scrollable list of game labels in the center,</li>
 *   <li>A {@link MemorySection} at the bottom.</li>
 * </ul>
 * All styling is driven by CSS classes defined in <code>dark-theme.css</code> and
 * scrollbar visuals from <code>custom-scroll.css</code>.
 * </p>
 */
public class HomePage extends BorderPane {

    /**
     * Constructs a HomePage, setting up:
     * <ul>
     *   <li>The settings panel on the right,</li>
     *   <li>A VBox of placeholder game labels wrapped in a {@link ScrollPane} in the center,</li>
     *   <li>A {@link MemorySection} at the bottom.</li>
     * </ul>
     * <p>
     * Each game label shows a memory‐usage popup on hover via {@link StoragePopupHandler}.
     * </p>
     */
    public HomePage() {
        // Apply CSS class for the page background
        getStyleClass().add("home-page");
        setPadding(new Insets(10));

        // --- Right: Settings panel ---
        SettingsPanel settingsPanel = new SettingsPanel();
        setRight(settingsPanel);

        // --- Center: Scrollable game list ---
        VBox gameList = new VBox(10);
        gameList.getStyleClass().add("game-list");
        gameList.setPadding(new Insets(10));

        StoragePopupHandler popupHandler = new StoragePopupHandler();
        for (int i = 1; i <= 7; i++) {
            final int idx = i;
            Label gameLabel = new Label("Placeholder Game " + idx);
            gameLabel.getStyleClass().add("game-label");

            // Show memory usage popup on hover
            gameLabel.setOnMouseEntered(e -> {
                double totalSpace  = 700;
                double freedSpace  = idx * 50;
                double desiredUsed = totalSpace - freedSpace;
                popupHandler.attachHoverPopup(gameLabel, freedSpace, totalSpace, desiredUsed, e);
            });
            gameLabel.setOnMouseExited(e -> popupHandler.hidePopup());

            gameList.getChildren().add(gameLabel);
        }

        ScrollPane scrollPane = new ScrollPane(gameList);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        // Load only scrollbar styles from custom-scroll.css
        scrollPane.getStylesheets().add(
                getClass().getResource("/custom-scroll.css").toExternalForm()
        );
        // Ensure viewport background matches the game-list background
        Platform.runLater(() -> {
            Region viewport = (Region) scrollPane.lookup(".viewport");
            if (viewport != null) {
                viewport.getStyleClass().add("viewport");
            }
        });
        setCenter(scrollPane);

        // --- Bottom: Memory section + Calculate button ---
        MemorySection memorySection = new MemorySection();
        memorySection.setPrefHeight(60);

        HBox bottomBar = new HBox(memorySection);
        bottomBar.getStyleClass().add("bottom-bar");
        HBox.setHgrow(memorySection, Priority.ALWAYS);
        setBottom(bottomBar);
    }
}






