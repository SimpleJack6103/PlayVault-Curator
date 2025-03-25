package PlayVaultCurator.UI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HomePage extends BorderPane {

    public HomePage() {
        // Create the SettingsPanel (with the settings button) on the right.
        SettingsPanel settingsPanel = new SettingsPanel();

        // Create a VBox to hold the list of game labels.
        VBox gameList = new VBox(10);
        gameList.setPadding(new Insets(10));
        // Use a solid rectangular background for the game list.
        gameList.setStyle("-fx-background-color: #2A2A2A;");

        // Instantiate the StoragePopupHandler for hover popups.
        StoragePopupHandler popupHandler = new StoragePopupHandler();

        // Add game labels with dummy memory values and hover events.
        for (int i = 1; i <= 7; i++) {
            final int gameIndex = i;
            Label gameLabel = new Label("Placeholder Game " + gameIndex);
            gameLabel.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 16px;");
            gameLabel.setOnMouseEntered(e -> {
                double totalSpace = 700;           // Dummy total used memory
                double freedSpace = gameIndex * 50;  // Dummy freed memory (increases per game)
                double desiredUsed = totalSpace - freedSpace;
                popupHandler.attachHoverPopup(gameLabel, freedSpace, totalSpace, desiredUsed, e);
            });
            gameLabel.setOnMouseExited(e -> popupHandler.hidePopup());
            gameList.getChildren().add(gameLabel);
        }

        // Wrap the game list in a ScrollPane.
        ScrollPane scrollPane = new ScrollPane(gameList);
        scrollPane.setFitToWidth(true);
        // Remove any border so there is no visible line.
        scrollPane.setStyle("-fx-background-color: #2A2A2A; -fx-border-width: 0;");
        // Ensure the internal viewport gets the same background.
        Platform.runLater(() -> {
            Region viewport = (Region) scrollPane.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: #2A2A2A;");
            }
        });
        // Add custom scrollbar styling from our external CSS file.
        scrollPane.getStylesheets().add(getClass().getResource("/custom-scroll.css").toExternalForm());

        // Create the bottom section using the improved MemorySection.
        MemorySection memorySection = new MemorySection();
        memorySection.setPrefHeight(60);
        // Place the MemorySection in an HBox so its progress bar expands and the calculate button is toward the right.
        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));
        HBox.setHgrow(memorySection, Priority.ALWAYS);
        bottomBar.getChildren().add(memorySection);

        // Assemble the overall layout.
        setRight(settingsPanel);   // Right: settings panel with the settings button.
        setCenter(scrollPane);       // Center: the scrollable list of game labels.
        setBottom(bottomBar);        // Bottom: the MemorySection row.
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #121212;");  // Overall window background.
    }
}