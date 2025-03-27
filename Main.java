import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Main extends Application {

    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    } // End of main.

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        Label label1 = new Label("Welcome to the first scene!");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));

        // Layout 1 = children are laid out in vertical column
        VBox layout1 = new VBox(20);
        // All objects are taken and listed vertically space 20 pixels apart
        layout1.getChildren().addAll(label1, button1);
        // getChildren.addAll tells us the all the added "Children" will be added
        // to that scene following the layout.
        scene1 = new Scene(layout1, 200, 200);

        //Button 2
        Button button2 = new Button("Go to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        // Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 400, 200);

        // This is where we set the first screen
        window.setScene(scene1);
        window.setTitle("Title text");
        window.show();
    }

} // End of Program