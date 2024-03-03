package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EnvoiMessage.fxml"));
//       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AdminUI.fxml"));
//       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EmojiMessenger.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root) ;
        stage.setScene(scene);
        stage.setTitle("Envoi Message");
        stage.show();
    }


    public static void main (String[] args) {
        launch(args);
    }
}


