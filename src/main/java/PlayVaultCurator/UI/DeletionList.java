package PlayVaultCurator.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DeletionList extends StackPane {
    private ScrollPane scrollPane;
    private VBox contentBox;
    private StoragePopupHandler popupHandler;

    public DeletionList(StoragePopupHandler popupHandler) {
        this.popupHandler = popupHandler;

        // Create the container for game suggestions
        contentBox = new VBox(5);
        contentBox.setStyle("-fx-background-color: #323232;");
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setFillWidth(true);

        // Add placeholder items with hover popups
        for (int i = 1; i <= 20; i++) {
            Label gameLabel = new Label("Placeholder Game " + i);
            gameLabel.setStyle("-fx-text-fill: white; -fx-padding: 5px;");
            gameLabel.setMaxWidth(Double.MAX_VALUE);

            double freedSpace = i * 0.5;   // Example calculation
            double totalSpace = 50.0;      // Example total space

            // Use a mouse entered handler that passes the event to the popup handler
            gameLabel.setOnMouseEntered((MouseEvent event) -> {
                popupHandler.attachHoverPopup(gameLabel, freedSpace, totalSpace, event);
            });
            gameLabel.setOnMouseExited((MouseEvent event) -> {
                popupHandler.hidePopup();
            });

            contentBox.getChildren().add(gameLabel);
        }

        // Add a spacer to push everything up
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        contentBox.getChildren().add(spacer);

        // Create the ScrollPane
        scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Bind the size
        scrollPane.prefWidthProperty().bind(this.widthProperty());
        scrollPane.prefHeightProperty().bind(this.heightProperty());
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        getChildren().add(scrollPane);
    }
}



