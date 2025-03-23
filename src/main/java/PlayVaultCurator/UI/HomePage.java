package PlayVaultCurator.UI;

import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;

public class HomePage extends BorderPane {

    public HomePage() {
        // Create individual UI components
        SettingsPanel settingsPanel = new SettingsPanel();
        DeletionList deletionList = new DeletionList();
        MemorySection memorySection = new MemorySection();
        StoragePopupHandler popupHandler = new StoragePopupHandler();

        // Setup hover for the deletion list to show the floating storage popup
        deletionList.setOnMouseEntered(e ->
                popupHandler.getStoragePopup().show(deletionList, e.getScreenX() + 10, e.getScreenY() + 10)
        );
        deletionList.setOnMouseExited(e -> popupHandler.getStoragePopup().hide());

        // Layout Setup: place settings on the right, deletion list in center, and memory section at the bottom
        setRight(settingsPanel);
        setCenter(deletionList);
        setBottom(memorySection);
    }
}

