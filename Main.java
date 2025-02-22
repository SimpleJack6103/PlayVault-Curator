import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

// This is basically pop up windows

public class Main extends Application {

    Stage window;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("thenewbutton");

        // This is handling when the close with the x
        window.setOnCloseRequest(e -> closeProgram());
        button = new Button("Click me");
        /*
        // This is the code for AlertBox

        button.setOnAction(e -> AlertBox.display("Title", "Wow this alert box is awesome!"));
        */


        /*
        * This is the code for ConfirmBox
        button.setOnAction(e -> {
            boolean result = ConfirmBox.display("Title", "Do you want to close this window");
            System.out.println(result);
        });
        */

        button.setOnAction(e -> closeProgram());


        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();

    }

    private void closeProgram() {
        // There is no file saved this is just pseudo.
        // Heres the problem we have a button to close the program
        // But if they click the x they bypass this.
        // This is useful for if we want to save something on exit
        System.out.println("File is saved");
        window.close();
    }

}