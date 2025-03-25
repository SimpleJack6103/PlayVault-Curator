package PlayVaultCurator.UI;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

public class HomePage extends BorderPane {

    public HomePage() {
        // Create UI Components
        SettingsPanel settingsPanel = new SettingsPanel();
        StoragePopupHandler popupHandler = new StoragePopupHandler();
        DeletionList deletionList = new DeletionList(popupHandler);
        MemorySection memorySection = new MemorySection();

        // Create layout for the bottom section: Memory Bar + Calculate Button
        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_LEFT); // Align to the left for memory bar
        bottomBar.setSpacing(10);

        Region spacer = new Region();  // Spacer to push the button to the right
        HBox.setHgrow(spacer, Priority.ALWAYS); // This will push the button to the far right

        bottomBar.getChildren().add(memorySection); // Add memory section to the left
        bottomBar.getChildren().add(spacer); // Add spacer between memory section and button
        bottomBar.getChildren().add(memorySection.getCalculateButton()); // Add button to the far right

        // Center the suggestion list
        VBox centerContainer = new VBox(deletionList);
        centerContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(deletionList, Priority.ALWAYS); // Make the deletion list grow vertically as window resizes

        // Add components to the layout
        setRight(settingsPanel);  // Settings button stays at the top-right
        setCenter(centerContainer); // Suggestion list centered
        setBottom(bottomBar);  // Bottom bar with memory bar and calculate button
        setPadding(new javafx.geometry.Insets(10));  // Add some padding for layout spacing

        // Ensure bottom bar components stretch
        HBox.setHgrow(memorySection, Priority.ALWAYS); // Ensure memory bar expands to fill available space in the bottom bar
    }
}





