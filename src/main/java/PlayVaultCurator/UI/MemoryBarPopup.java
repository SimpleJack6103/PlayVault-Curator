package PlayVaultCurator.UI;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * MemoryBarPopup is a floating visual that displays memory impact of a potential
 * deletion suggestion.
 * <p>
 * Contains a horizontal bar with 3 elements:
 * <ul>
 *   <li>A green bar representing total currently used memory,</li>
 *   <li>A purple overlay showing what memory would be freed,</li>
 *   <li>A thin black line indicating the desired used memory threshold.</li>
 * </ul>
 * <p>
 * This is typically shown as a hover-popup near each game suggestion.
 */
public class MemoryBarPopup extends Pane {
    private static final double BAR_WIDTH = 300;
    private static final double BAR_HEIGHT = 20;

    private double totalUsed;
    private double freed;
    private double desiredUsed;

    /**
     * Constructs the popup memory bar with current and suggested memory values.
     *
     * @param totalUsed   current total used memory (in MB)
     * @param freed       amount of memory that would be freed (in MB)
     * @param desiredUsed ideal used memory after deletion (in MB)
     */
    public MemoryBarPopup(double totalUsed, double freed, double desiredUsed) {
        this.totalUsed = totalUsed;
        this.freed = freed;
        this.desiredUsed = desiredUsed;

        getStyleClass().add("memory-bar-popup");
        setPrefSize(BAR_WIDTH, BAR_HEIGHT);
        drawBar();
    }

    /**
     * Redraws the internal visual representation of the memory bar.
     * <p>
     * This method clears all previous children and draws:
     * - The full green bar
     * - The purple overlay indicating memory to be freed
     * - The threshold line
     */
    private void drawBar() {
        getChildren().clear();

        Region greenBar = new Region();
        greenBar.getStyleClass().add("green-bar");
        greenBar.setPrefSize(BAR_WIDTH, BAR_HEIGHT);

        double freedWidth = (freed / totalUsed) * BAR_WIDTH;
        Region purpleOverlay = new Region();
        purpleOverlay.getStyleClass().add("purple-overlay");
        purpleOverlay.setPrefSize(freedWidth, BAR_HEIGHT);
        purpleOverlay.setLayoutX(BAR_WIDTH - freedWidth);

        double thresholdX = (desiredUsed / totalUsed) * BAR_WIDTH;
        Region thresholdLine = new Region();
        thresholdLine.getStyleClass().add("threshold-line");
        thresholdLine.setPrefHeight(BAR_HEIGHT);
        thresholdLine.setLayoutX(thresholdX - 1);

        getChildren().addAll(greenBar, purpleOverlay, thresholdLine);
    }

    /**
     * Allows updating the values and regenerating the bar dynamically.
     *
     * @param totalUsed   new total memory used (in MB)
     * @param freed       memory to be freed (in MB)
     * @param desiredUsed new threshold to aim for (in MB)
     */
    public void updateValues(double totalUsed, double freed, double desiredUsed) {
        this.totalUsed = totalUsed;
        this.freed = freed;
        this.desiredUsed = desiredUsed;
        drawBar();
    }
}

