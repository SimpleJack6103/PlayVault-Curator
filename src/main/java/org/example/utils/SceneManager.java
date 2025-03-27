package org.example.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class SceneManager {

    /**
     * Loads an FXML file from the classpath and returns a Scene.
     *
     * @param fxmlPath the relative path to the FXML file (e.g., "views/main-view.fxml")
     * @return a new Scene containing the loaded FXML layout
     * @throws IOException if the FXML file cannot be loaded
     */
    public static Scene loadScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getClassLoader().getResource(fxmlPath));
        Parent root = loader.load();
        return new Scene(root);
    }
}
