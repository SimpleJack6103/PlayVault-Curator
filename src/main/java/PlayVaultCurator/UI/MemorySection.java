package PlayVaultCurator.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

public class MemorySection extends HBox {

    private ProgressBar memoryBar;
    private Button calculateButton;

    public MemorySection() {
        memoryBar = new ProgressBar(0.5); // Placeholder for 50% used memory
        memoryBar.setPrefWidth(250);
        memoryBar.getStyleClass().add("memory-bar");

        calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> System.out.println("Calculating..."));

        setSpacing(10);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        getChildren().addAll(memoryBar, calculateButton);
    }
}
