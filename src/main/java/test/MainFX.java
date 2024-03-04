package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ListeEncheres.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("liste enchères");
            stage.show();
        }catch (Exception e){
            System.out.println("hereMainFX");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }
    public static void main(String[] args) {
        launch(args);
    }




}
