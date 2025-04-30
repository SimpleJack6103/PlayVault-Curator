// File: PlayVaultCurator/UI/HomePage.java
package PlayVaultCurator.UI;

import Games2Delete.Game;
import PlayVaultCurator.util.DirectorySearch;
import PlayVaultCurator.util.SettingsManager;
import PlayVaultCurator.util.SuggestionGenerator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.*;
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
        // Apply CSS class
        getStyleClass().add("home-page");
        setPadding(new Insets(10));

        // Right: Settings panel
        SettingsPanel settingsPanel = new SettingsPanel();
        settingsPanel.getStyleClass().add("settings-panel");
        setRight(settingsPanel);

        // Center: DeletionList
        deletionList = new DeletionList();
        deletionList.getStyleClass().add("deletion-list");
        setCenter(deletionList);

        // Bottom: MemorySection + Calculate button
        memorySection = new MemorySection();
        HBox bottomBar = new HBox(memorySection);
        bottomBar.getStyleClass().add("bottom-bar");
        HBox.setHgrow(memorySection, Priority.ALWAYS);
        setBottom(bottomBar);

        // Calculate logic
        memorySection.getCalculateButton().setOnAction(e -> {
            try {
                // 1) Load games if not already loaded
                if (games == null || games.isEmpty()) {
                    String path = SettingsManager.getGameDirectory();
                    if (path != null && !path.isEmpty()) {
                        games = DirectorySearch.searchFiles(new File(path));
                    }
                }

                if (games == null || games.isEmpty()) {
                    System.out.println("No games loaded yet.");
                    return;
                }

                // 2) Compute how much needs to be freed
                double used   = memorySection.getCurrentUsageGB();
                double total  = memorySection.getTotalGB();
                double thresh = memorySection.getThreshold() * total;
                double needed = used - thresh;

                if (needed <= 0) {
                    deletionList.updateGames(List.of());
                    System.out.println("Already under threshold.");
                    return;
                }

                // 3) Generate suggestion sets
                List<List<Game>> sets = SuggestionGenerator.getSuggestionSets(games, needed, 2);
                deletionList.updateSuggestionSets(sets);

                // 4) Show MemoryBarPopup for first suggestion set
                double freedGB = sets.stream()
                        .findFirst()
                        .orElse(List.of())
                        .stream()
                        .mapToDouble(Game::getSizeGB)
                        .sum();
                double usedMB    = used    * 1024;
                double freedMB   = freedGB * 1024;
                double desiredMB = thresh  * 1024;

                MemoryBarPopup popup = new MemoryBarPopup(usedMB, freedMB, desiredMB);
                popup.getStyleClass().add("memory-popup");

                if (!memorySection.getChildren().contains(popup)) {
                    memorySection.getChildren().add(popup);
                }

                // Optional: only show popup on hover
                deletionList.setOnMouseEntered(event -> {
                    if (!memorySection.getChildren().contains(popup)) {
                        memorySection.getChildren().add(popup);
                    }
                });

                deletionList.setOnMouseExited(event -> {
                    memorySection.getChildren().remove(popup);
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /** Called externally to populate games and trigger calculation. */
    public void setGames(List<Game> games) {
        this.games = games;
        Platform.runLater(() -> memorySection.getCalculateButton().fire());
    }

    public MemorySection getMemorySection() {
        return memorySection;
    }

    public DeletionList getDeletionList() {
        return deletionList;
    }
}



