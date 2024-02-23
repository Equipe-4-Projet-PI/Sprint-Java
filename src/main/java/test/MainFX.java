package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/AddProduct.fxml"));
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("gerer produit");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
