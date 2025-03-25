package PlayVaultCurator.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MemorySection extends VBox {
    private ProgressBar memoryBar;
    private Label totalStorageLabel;
    private Button calculateButton;

    public MemorySection() {
        // Label to indicate total storage
        totalStorageLabel = new Label("Total Storage: 30.0 GB / 50.0 GB");
        totalStorageLabel.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 14px;");

        // Create the progress bar for total storage usage (example: 60% used)
        memoryBar = new ProgressBar(0.6); // Example: 60% used
        memoryBar.setPrefWidth(250);
        memoryBar.setStyle("-fx-accent: #4CAF50;"); // Green for used memory

        // Create a layout for the memory bar and label
        VBox memoryContainer = new VBox(5, totalStorageLabel, memoryBar);
        memoryContainer.setAlignment(Pos.CENTER_LEFT);
        memoryContainer.setSpacing(5);

        // Ensure memoryContainer stays aligned to the bottom left of the screen
        setSpacing(5);
        setAlignment(Pos.BOTTOM_LEFT); // Positioning at the bottom-left corner
        setMaxWidth(Double.MAX_VALUE); // Allow it to expand horizontally

        // Create Calculate Button for the bottom-right
        calculateButton = new Button("Calculate");
        calculateButton.setMinWidth(100);
        calculateButton.setOnAction(e -> System.out.println("Calculating..."));

        // Add the memory bar and calculate button to the main layout
        getChildren().add(memoryContainer);
    }

    // Getter for calculateButton
    public Button getCalculateButton() {
        return calculateButton;
    }
}

