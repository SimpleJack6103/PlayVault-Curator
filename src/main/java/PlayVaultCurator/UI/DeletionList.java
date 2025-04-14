package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * DeletionList is a scrollable UI container displaying suggested games
 * for deletion. Each game is shown as a label styled as a "deletion-game".
 * <p>
 * This box is intended to be shown either inside the HomePage or as a
 * visual output when the "Calculate" button is used.
 */
public class DeletionList extends VBox {
    private VBox gameList;
    private ScrollPane scrollPane;

    /**
     * Creates a DeletionList with placeholder entries.
     * TODO: Replace dummy labels with dynamic suggestions from algorithm.
     */
    public DeletionList() {
        gameList = new VBox();
        gameList.setSpacing(5);
        gameList.setPadding(new Insets(10));
        gameList.setMaxWidth(Double.MAX_VALUE);
        gameList.setStyle("-fx-background-color: #323232;");

        // TODO: Replace with real suggestions
        for (int i = 1; i <= 20; i++) {
            Label gameLabel = new Label("Placeholder Game " + i);
            gameLabel.getStyleClass().add("deletion-game");
            gameLabel.setMaxWidth(Double.MAX_VALUE);
            gameList.getChildren().add(gameLabel);
        }

        scrollPane = new ScrollPane(gameList);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setMinHeight(150);
        scrollPane.getStyleClass().add("deletion-box");

        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        getChildren().add(scrollPane);
    }
}




