// File: PlayVaultCurator/UI/DeletionList.java
package PlayVaultCurator.UI;

import Games2Delete.Game;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.util.List;

/**
 * A scrollable list displaying one or more “suggestion sets” of games to uninstall.
 */
public class DeletionList extends ScrollPane {

    private VBox gameList;

    public DeletionList() {
        getStyleClass().add("deletion-box");
        gameList = new VBox(5);
        gameList.getStyleClass().add("game-list");
        setContent(gameList);
        setFitToWidth(true);
    }

    /**
     * Display a single list of games.
     */
    public void updateGames(List<Game> suggestions) {
        Platform.runLater(() -> {
            gameList.getChildren().clear();

            if (suggestions == null || suggestions.isEmpty()) {
                // Add placeholder text when there are no games
                Label none = new Label("No games to display. Please load your games.");
                none.getStyleClass().add("deletion-game");
                none.setStyle("-fx-text-fill: grey;");
                gameList.getChildren().add(none);
            } else {
                for (Game g : suggestions) {
                    Label lbl = new Label("• " + g.getName());
                    lbl.getStyleClass().add("deletion-game");
                    lbl.setMaxWidth(Double.MAX_VALUE);
                    gameList.getChildren().add(lbl);
                }
            }
        });
    }

    /**
     * Display multiple numbered suggestion‐sets.
     */
    public void updateSuggestionSets(List<List<Game>> suggestionSets) {
        Platform.runLater(() -> {
            gameList.getChildren().clear();

            if (suggestionSets == null || suggestionSets.isEmpty()) {
                // Add placeholder text when there are no suggestion sets
                Label none = new Label("No suggestions available. Adjust your threshold.");
                none.getStyleClass().add("deletion-game");
                none.setStyle("-fx-text-fill: grey;");
                gameList.getChildren().add(none);
                return;
            }

            int idx = 1;
            for (List<Game> set : suggestionSets) {
                Label header = new Label("Suggestion " + (idx++) + ":");
                header.getStyleClass().add("deletion-game-header");
                gameList.getChildren().add(header);
                for (Game g : set) {
                    Label gameLabel = new Label(" • " + g.getName());
                    gameLabel.getStyleClass().add("deletion-game");
                    gameLabel.setMaxWidth(Double.MAX_VALUE);
                    gameList.getChildren().add(gameLabel);
                }
                Region spacer = new Region();
                spacer.setPrefHeight(10);
                gameList.getChildren().add(spacer);
            }
        });
    }
}




