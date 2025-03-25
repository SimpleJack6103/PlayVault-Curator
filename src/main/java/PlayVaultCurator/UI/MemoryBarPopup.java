package PlayVaultCurator.UI;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * MemoryBarPopup is a custom UI component that visualizes memory usage.
 * - The entire green bar represents total used memory.
 * - The purple overlay (#BB86FC) on the right represents the memory that would be freed.
 * - A vertical black line indicates the desired used memory threshold.
 */
public class MemoryBarPopup extends Pane {
    private static final double BAR_WIDTH = 300;
    private static final double BAR_HEIGHT = 20;

    // Memory values (in MB or any consistent unit)
    private double totalUsed;    // Total memory currently used
    private double freed;        // Memory that would be freed by deletion
    private double desiredUsed;  // Desired used memory threshold (target used memory after deletion)

    /**
     * Constructs a MemoryBarPopup with the given values.
     *
     * @param totalUsed   Total memory currently used.
     * @param freed       Memory freed if deletion occurs.
     * @param desiredUsed Desired used memory threshold.
     */
    public MemoryBarPopup(double totalUsed, double freed, double desiredUsed) {
        this.totalUsed = totalUsed;
        this.freed = freed;
        this.desiredUsed = desiredUsed;
        setPrefSize(BAR_WIDTH, BAR_HEIGHT);
        drawBar();
    }

    /**
     * Draws the memory bar with the current values.
     */
    private void drawBar() {
        // Clear any previous drawings.
        getChildren().clear();

        // The full green bar represents the total used memory.
        Region greenBar = new Region();
        greenBar.setStyle("-fx-background-color: green;");
        greenBar.setPrefSize(BAR_WIDTH, BAR_HEIGHT);
        greenBar.setLayoutX(0);
        greenBar.setLayoutY(0);

        // Compute the width for the freed memory area (purple overlay)
        double freedWidth = (freed / totalUsed) * BAR_WIDTH;
        Region purpleOverlay = new Region();
        purpleOverlay.setStyle("-fx-background-color: #BB86FC;");
        purpleOverlay.setPrefSize(freedWidth, BAR_HEIGHT);
        // Place the overlay on the right end of the bar.
        purpleOverlay.setLayoutX(BAR_WIDTH - freedWidth);
        purpleOverlay.setLayoutY(0);

        // Compute the threshold line position.
        double thresholdX = (desiredUsed / totalUsed) * BAR_WIDTH;
        Region thresholdLine = new Region();
        thresholdLine.setStyle("-fx-background-color: black;");
        thresholdLine.setPrefSize(2, BAR_HEIGHT);
        // Position the line centered at thresholdX
        thresholdLine.setLayoutX(thresholdX - 1);
        thresholdLine.setLayoutY(0);

        // Add all elements to this component.
        getChildren().addAll(greenBar, purpleOverlay, thresholdLine);
    }

    // Optionally, allow updating the bar values dynamically.
    public void updateValues(double totalUsed, double freed, double desiredUsed) {
        this.totalUsed = totalUsed;
        this.freed = freed;
        this.desiredUsed = desiredUsed;
        drawBar();
    }
}