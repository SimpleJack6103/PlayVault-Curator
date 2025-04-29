package PlayVaultCurator.UI;

import Games2Delete.Game;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;

/**
 * DeletionList is a scrollable UI container displaying suggested games
 * for deletion. Each game is shown as a label styled as "deletion-game".
 *
 * This box is intended to be shown either inside the HomePage or as a
 * visual output when the "Calculate" button is used.
 */
public class DeletionList extends VBox {
    private VBox gameList;
    private ScrollPane scrollPane;

    /**
     * Creates a DeletionList with initial placeholder entries.
     */
    public DeletionList() {
        // Create a container for game labels.
        gameList = new VBox();
        gameList.setSpacing(5);
        gameList.setPadding(new Insets(10));
        gameList.setMaxWidth(Double.MAX_VALUE);
        gameList.setStyle("-fx-background-color: #323232;");

        // Initially display a waiting message until real data is loaded.
        Label waitingLabel = new Label("Waiting for deletion suggestions...");
        waitingLabel.getStyleClass().add("deletion-game");
        waitingLabel.setMaxWidth(Double.MAX_VALUE);
        gameList.getChildren().add(waitingLabel);

        // Create the ScrollPane that will contain the gameList.
        scrollPane = new ScrollPane(gameList);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setMinHeight(150);

        // Attempt to load the custom-scroll.css file.
        URL cssUrl = getClass().getResource("/custom-scroll.css");
        if (cssUrl != null) {
            scrollPane.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Warning: Could not load /css/custom-scroll.css. Make sure it is in the resources folder.");
        }

        scrollPane.getStyleClass().add("deletion-box");

        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        getChildren().add(scrollPane);
    }

    /**
     * Updates the DeletionList UI with a new list of games for deletion.
     *
     * @param games List of Game objects to display.
     */
    public void updateGames(List<Game> games) {
        Platform.runLater(() -> {
            gameList.getChildren().clear();
            if (games != null && !games.isEmpty()) {
                for (Game game : games) {
                    Label gameLabel = new Label(game.getGameName());
                    gameLabel.getStyleClass().add("deletion-game");
                    gameLabel.setMaxWidth(Double.MAX_VALUE);
                    gameList.getChildren().add(gameLabel);
                }
            } else {
                Label noGameLabel = new Label("No games available for deletion.");
                noGameLabel.getStyleClass().add("deletion-game");
                noGameLabel.setMaxWidth(Double.MAX_VALUE);
                gameList.getChildren().add(noGameLabel);
            }
        });
    }
}




