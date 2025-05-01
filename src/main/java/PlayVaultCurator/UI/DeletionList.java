// File: PlayVaultCurator/UI/DeletionList.java
package PlayVaultCurator.UI;

import Games2Delete.Game;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * A scrollable list displaying games to uninstall, with placeholder and header support.
 */
public class DeletionList extends ScrollPane {

    private final VBox listContainer;

    public DeletionList() {
        getStyleClass().add("game-list");

        listContainer = new VBox(10);
        listContainer.setPadding(new Insets(10));
        listContainer.setStyle("-fx-background-color: #2A2A2A;");
        setContent(listContainer);
        setFitToWidth(true);
        setStyle("-fx-background-color: transparent;");

        // style viewport when ready
        Platform.runLater(() -> {
            var vp = lookup(".viewport");
            if (vp != null) vp.setStyle("-fx-background-color: #2E2E2E;");
        });

        // initial welcome
        showPlaceholder("Welcome! Choose Settings → directory, then Calculate.");
    }

    public void updateGames(List<Game> gamesToDelete) {
        listContainer.getChildren().clear();
        if (gamesToDelete == null || gamesToDelete.isEmpty()) {
            showPlaceholder("No suggestions available. Adjust your threshold.");
        } else {
            for (Game g : gamesToDelete) {
                Label lbl = new Label("• " + g.getGameName() + " (" + g.getSizeGB() + " GB)");
                lbl.getStyleClass().add("deletion-game");
                listContainer.getChildren().add(lbl);
            }
        }
    }

    public void updateGamesWithHeader(String headerText, List<Game> games) {
        listContainer.getChildren().clear();
        Label header = new Label(headerText);
        header.getStyleClass().add("deletion-game-header");
        listContainer.getChildren().add(header);
        updateGames(games);
    }

    private void showPlaceholder(String text) {
        Label placeholder = new Label(text);
        placeholder.getStyleClass().add("placeholder-text");
        listContainer.getChildren().add(placeholder);
    }
}







