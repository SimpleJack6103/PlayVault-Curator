package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MemorySection extends VBox {
    private Label totalStorageLabel;
    private ProgressBar memoryBar;
    private Button calculateButton;

    public MemorySection() {
        // Create a label for memory usage details.
        totalStorageLabel = new Label("Total Storage: 30.0 GB / 50.0 GB");
        totalStorageLabel.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 14px;");

        // Create a progress bar (example: 60% used), styled and allowed to expand.
        memoryBar = new ProgressBar(0.6);
        memoryBar.setStyle("-fx-accent: #4CAF50;");
        memoryBar.setMaxWidth(Double.MAX_VALUE);

        // Create a larger calculate button; adjusting font size and padding.
        calculateButton = new Button("Calculate");
        calculateButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        calculateButton.setMinWidth(120);
        calculateButton.setOnAction(e -> System.out.println("Calculating..."));

        // Assemble an HBox with the progress bar and the calculate button.
        HBox barAndButtonContainer = new HBox(10);
        barAndButtonContainer.setAlignment(Pos.CENTER);
        barAndButtonContainer.getChildren().add(memoryBar);

        // Insert a spacer to let the progress bar expand and push the button to the right.
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        barAndButtonContainer.getChildren().add(spacer);

        barAndButtonContainer.getChildren().add(calculateButton);
        HBox.setHgrow(memoryBar, Priority.ALWAYS);

        // Arrange the label above the horizontal bar.
        getChildren().addAll(totalStorageLabel, barAndButtonContainer);
        setSpacing(5);
        setPadding(new Insets(5));
    }

    public Button getCalculateButton() {
        return calculateButton;
    }
}