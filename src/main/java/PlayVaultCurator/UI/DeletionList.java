package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class DeletionList extends VBox {

    private VBox gameList;
    private ScrollPane scrollPane;

    public DeletionList() {
        gameList = new VBox();
        gameList.setSpacing(5);
        gameList.setPadding(new Insets(10));
        // Set the same gray background as the deletion box
        gameList.setStyle("-fx-background-color: #323232;");

        // Add placeholder games
        for (int i = 1; i <= 20; i++) {
            Label gameLabel = new Label("Placeholder Game " + i);
            gameLabel.getStyleClass().add("deletion-game");
            gameList.getChildren().add(gameLabel);
        }

        // Wrap the game list in a ScrollPane
        scrollPane = new ScrollPane(gameList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(150);
        scrollPane.getStyleClass().add("deletion-box");

        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        getChildren().add(scrollPane);
    }
}


