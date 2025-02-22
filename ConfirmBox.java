import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

// This is going to pull up a window and return the response to the other
// window
public class ConfirmBox {

    static boolean answer;


    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        // This is the different part
        Button nButton = new Button("No");
        Button yButton = new Button("Yes");


        yButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        nButton.setOnAction(e -> {
            answer = false;
            window.close();
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yButton, nButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);


        window.showAndWait();
        return answer;
    }
}
