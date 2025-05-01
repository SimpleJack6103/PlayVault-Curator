// File: PlayVaultCurator/UI/HomePage.java
package PlayVaultCurator.UI;

import Games2Delete.Game;
import Games2Delete.Games2Delete;
import PlayVaultCurator.util.DirectorySearch;
import PlayVaultCurator.util.SettingsManager;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.Button;

import java.io.File;
import java.util.List;

/**
 * The main content pane for the application's Home Page.
 */
public class HomePage extends BorderPane {

    private DeletionList deletionList;
    private MemorySection memorySection;
    private List<Game> games;

    public HomePage() {
        getStyleClass().add("home-page");
        setPadding(new Insets(10));

        // --- Right: Settings panel ---
        SettingsPanel settingsPanel = new SettingsPanel();
        setRight(settingsPanel);

        // --- Center: DeletionList ---
        deletionList = new DeletionList();
        setCenter(deletionList);

        // --- Bottom: Memory section + Calculate button ---
        memorySection = new MemorySection();
        memorySection.setPrefHeight(60);

        Button calculateButton = memorySection.getCalculateButton();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottomBar = new HBox(10, memorySection, spacer, calculateButton);
        bottomBar.getStyleClass().add("bottom-bar");
        bottomBar.setPadding(new Insets(10));
        setBottom(bottomBar);

        // --- Hook up Calculate Button with directory-scan fallback & logging ---
        calculateButton.setOnAction(e -> {
            try {
                // 1) If no games loaded yet, scan the directory the user picked
                if (games == null || games.isEmpty()) {
                    String dir = SettingsManager.getGameDirectory();
                    System.out.println("Loading games from directory: " + dir);
                    if (dir != null && !dir.isEmpty()) {
                        games = DirectorySearch.searchFiles(new File(dir));
                    }
                    System.out.println("Found " + (games == null ? 0 : games.size()) + " games.");
                }

                if (games == null || games.isEmpty()) {
                    System.out.println("Still no games loaded – check that the directory contains games.");
                    deletionList.updateGames(List.of());
                    return;
                }

                // 2) Rank & suggest
                Games2Delete.rankGamesForDeletion(games);
                List<Game> suggestions = Games2Delete.getSuggestedGamesToUninstall(games, 50.0);

                // 3) Update the list
                deletionList.updateGames(suggestions);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /** Called by SettingsPage to inject scanned games immediately. */
    public void setGames(List<Game> games) {
        this.games = games;
        System.out.println("HomePage received " + (games==null?0:games.size()) + " games via setGames()");
    }

    /** Expose MemorySection so Main can read threshold or fire calculate. */
    public MemorySection getMemorySection() {
        return memorySection;
    }

    /** Expose DeletionList for other uses. */
    public DeletionList getDeletionList() {
        return deletionList;
    }
}





