package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

public class StoragePopupHandler {

    private Popup storagePopup;
    private StackPane storageContainer;
    private ProgressBar storageBar;
    private ProgressBar freedSpaceBar;

    public StoragePopupHandler() {
        storagePopup = new Popup();
        storageBar = new ProgressBar(0.7); // Placeholder for 70% used memory
        storageBar.setPrefWidth(200);
        storageBar.setStyle("-fx-accent: #4CAF50;"); // Green for used memory

        freedSpaceBar = new ProgressBar(0.2); // Placeholder for 20% freed memory
        freedSpaceBar.setPrefWidth(200);
        freedSpaceBar.setStyle("-fx-accent: grey;"); // Gray for freed memory

        storageContainer = new StackPane(storageBar, freedSpaceBar);
        storageContainer.setPadding(new Insets(10));
        storageContainer.setStyle("-fx-background-color: #1E1E1E; -fx-border-color: #333333; -fx-border-radius: 5; -fx-padding: 5;");
        storagePopup.getContent().add(storageContainer);
    }

    public Popup getStoragePopup() {
        return storagePopup;
    }
}

