// File: PlayVaultCurator/UI/DeletionList.java
package PlayVaultCurator.UI;

import Games2Delete.Game;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class DeletionList extends ScrollPane {

    private final VBox listContainer;

    public DeletionList() {
        this.getStyleClass().add("game-list");

        listContainer = new VBox();
        listContainer.setSpacing(10);
        listContainer.setPadding(new Insets(10));
        listContainer.setFillWidth(true);
        listContainer.setStyle("-fx-background-color: #2A2A2A;");

        this.setContent(listContainer);
        this.setFitToWidth(true);
        this.setStyle("-fx-background-color: transparent;");

        Platform.runLater(() -> {
            var viewport = this.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: #2E2E2E;");
            }
        });

        // Add welcome/placeholder message
        showPlaceholder();
    }

    public void updateGames(List<Game> gamesToDelete) {
        listContainer.getChildren().clear();

        if (gamesToDelete == null || gamesToDelete.isEmpty()) {
            showPlaceholder();
        } else {
            Label header = new Label("Games Selected for Deletion:");
            header.getStyleClass().add("deletion-game-header");
            listContainer.getChildren().add(header);

            for (Game game : gamesToDelete) {
                Label gameLabel = new Label(game.getGameName() + " - " + game.getGameSize() + " GB");
                gameLabel.getStyleClass().add("deletion-game");
                listContainer.getChildren().add(gameLabel);
            }
        }
    }

    private void showPlaceholder() {
        Label placeholder = new Label("No games selected for deletion.");
        placeholder.getStyleClass().add("placeholder-text");
        listContainer.getChildren().add(placeholder);
    }
}





