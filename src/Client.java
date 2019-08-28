import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import static javafx.application.Application.launch;

public class Client extends Application {
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    public void start(Stage primaryStage) {
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter a message: "));

        // TextField hvor man kan indtaste sin besked
        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);

        BorderPane mainPane = new BorderPane();
        // TextArea hvor ens egne og andres beskeder bliver vist
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);

        // Laver en scene og sætter den i vores Stage
        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client"); // Stage titel
        primaryStage.setScene(scene); // Placer scenen på Stage
        primaryStage.show(); // Viser Stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}
