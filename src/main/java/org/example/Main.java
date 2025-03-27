package org.example;

import org.example.utils.SceneManager;
import org.example.controllers.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set title for the window
        primaryStage.setTitle("JavaFX Manual UI");

        // Create UI components directly in Java code
        Button btn = new Button("Click Me");
        btn.setOnAction(e -> System.out.println("Button Clicked!"));

        // Layout for the scene (VBox in this case)
        VBox layout = new VBox(10, btn);  // 10px spacing between components

        // Create the scene with the layout
        Scene scene = new Scene(layout, 300, 250);

        // Set the scene and show the window
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





