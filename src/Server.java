import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server extends Application {
    String message;
    private int clientNo = 0;
    TextArea ta = new TextArea();


    @Override
    public void start(Stage primaryStage) {
        // vores GUI
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread( () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                ta.appendText("Server started at " + new Date() + '\n');

                while (true) {
                    Socket socket = serverSocket.accept();

                    clientNo++;

                    Platform.runLater( () -> {
                        ta.appendText("Starting thread client " + clientNo + " at " + new Date() + '\n');

                        InetAddress inetAddress = socket.getInetAddress();
                        ta.appendText("Client " + clientNo + " host name is " + inetAddress.getHostName() + "\n");
                        ta.appendText("Client " + clientNo + " IP Address is " + inetAddress.getHostAddress() + "\n");
                    });
                    // opretter en ny thread til hver ny client
                    new Thread(new HandleAClient(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    // subclass som håndterer den enkelte client
    class HandleAClient implements Runnable {
        private Socket socket;

        public HandleAClient(Socket socket) {
            this.socket = socket;
        }
        public void run() {
            try {
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());


                while (true) {
                    // Modtager besked fra clienten
                    message = inputFromClient.readUTF();

                    Platform.runLater(() -> {
                        ta.appendText("Client: " + message + "\n");
                        try {
                            // sender beskeden tilbage til clienten
                            outputToClient.writeUTF(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
