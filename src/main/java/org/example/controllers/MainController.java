package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button clickMeButton;

    /**
     * Called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        clickMeButton.setOnAction(e -> welcomeLabel.setText("Button Clicked!"));
    }
}

