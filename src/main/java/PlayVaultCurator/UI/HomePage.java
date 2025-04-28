package PlayVaultCurator.UI;

import Games2Delete.Game;
import Games2Delete.Games2Delete;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.application.Platform;

import java.util.List;

/**
 * The main content pane for the application's Home Page.
 * <p>
 * This {@link BorderPane} arranges:
 * <ul>
 *   <li>A {@link SettingsPanel} on the right,</li>
 *   <li>A scrollable list of suggested games (DeletionList) in the center,</li>
 *   <li>A {@link MemorySection} at the bottom.</li>
 * </ul>
 */
public class HomePage extends BorderPane {

    private DeletionList deletionList;
    private MemorySection memorySection;
    private List<Game> games; // Real list of games injected externally

    /**
     * Constructs the HomePage layout.
     */
    public HomePage() {
        getStyleClass().add("home-page");
        setPadding(new Insets(10));

        // --- Right: Settings panel ---
        SettingsPanel settingsPanel = new SettingsPanel();
        setRight(settingsPanel);

        // --- Center: DeletionList ---
        deletionList = new DeletionList();
        setCenter(deletionList);

        // --- Bottom: Memory section and Calculate button ---
        memorySection = new MemorySection();
        memorySection.setPrefHeight(60);

        HBox bottomBar = new HBox(memorySection);
        bottomBar.getStyleClass().add("bottom-bar");
        HBox.setHgrow(memorySection, Priority.ALWAYS);
        setBottom(bottomBar);

        // --- Hook up Calculate Button ---
        memorySection.getCalculateButton().setOnAction(e -> {
            try {
                if (games == null || games.isEmpty()) {
                    System.out.println("No games loaded yet.");
                    return;
                }

                // Rank the games
                Games2Delete.rankGamesForDeletion(games);

                // Suggest games to uninstall to free at least 50 GB
                List<Game> suggestions = Games2Delete.getSuggestedGamesToUninstall(games, 50.0); // <- neededSpace is 50.0 for now

                // Update DeletionList
                deletionList.updateGames(suggestions);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Injects a real list of games into the HomePage.
     * @param games list of Game objects
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }

    /**
     * @return the MemorySection (for future expansion)
     */
    public MemorySection getMemorySection() {
        return memorySection;
    }

    /**
     * @return the DeletionList (for updating game suggestions)
     */
    public DeletionList getDeletionList() {
        return deletionList;
    }
}
