package kolchy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("afficher-event-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Something");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
