// File: PlayVaultCurator/UI/HomePage.java
package PlayVaultCurator.UI;

import Games2Delete.Game;
import Games2Delete.Games2Delete;
import PlayVaultCurator.util.DirectorySearch;
import PlayVaultCurator.util.SettingsManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main content pane for the application's Home Page.
 */
public class HomePage extends BorderPane {

    private final DeletionList deletionList;
    private final MemorySection memorySection;
    private List<Game> games;

    public HomePage() {
        getStyleClass().add("home-page");
        setPadding(new Insets(10));

        // Right: settings panel
        setRight(new SettingsPanel());

        // Center: deletion list
        deletionList = new DeletionList();
        setCenter(deletionList);

        // Bottom: memory section + calculate button
        memorySection = new MemorySection();
        Button calc = memorySection.getCalculateButton();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottom = new HBox(10, memorySection, spacer, calc);
        bottom.getStyleClass().add("bottom-bar");
        bottom.setPadding(new Insets(10));
        setBottom(bottom);

        calc.setOnAction(e -> onCalculate());
    }

    private void onCalculate() {
        // always re-scan games directory
        String dir = SettingsManager.getGameDirectory();
        if (dir != null && !dir.isEmpty()) {
            games = DirectorySearch.searchFiles(new File(dir));
        }

        if (games == null || games.isEmpty()) {
            deletionList.updateGames(List.of());
            return;
        }

        double used   = memorySection.getCurrentUsageGB();
        double total  = memorySection.getTotalGB();
        double thresh = memorySection.getThreshold() * total;
        double needed = used - thresh;

        Games2Delete.rankGamesForDeletion(games);

        if (needed > 0) {
            List<Game> suggestions = Games2Delete.getSuggestedGamesToUninstall(games, needed);
            deletionList.updateGames(suggestions);
        } else {
            List<Game> top3 = games.stream()
                    .sorted(Comparator.comparingDouble(Game::getDeletionRanking).reversed())
                    .limit(3)
                    .collect(Collectors.toList());
            deletionList.updateGamesWithHeader(
                    "Under threshold—top candidates to uninstall:", top3);
        }
    }

    /**
     * Injects a real list of games into the HomePage.
     * This method was missing; now Main.setGames(...) will compile.
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }

    /** Expose MemorySection so SettingsPage can read/update it. */
    public MemorySection getMemorySection() {
        return memorySection;
    }

    /** Expose DeletionList for testing or other use. */
    public DeletionList getDeletionList() {
        return deletionList;
    }
}









