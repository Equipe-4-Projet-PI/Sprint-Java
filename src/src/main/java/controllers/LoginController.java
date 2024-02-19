package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceUser;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginController {
    @FXML
    private PasswordField id_Password;

    @FXML
    private TextField id_Username;

    ServiceUser serviceUser = new ServiceUser() ;

    public void GoBackClick(javafx.scene.input.MouseEvent mouseEvent) {
        System.out.println("goback");
    }
    public void Login(javafx.event.ActionEvent actionEvent) throws IOException {
        String username = id_Username.getText();
        String password = id_Password.getText();

        try {
            if (serviceUser.checkAdmin(username, password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUI.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
                scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
                System.out.println("Login successful!");

                // Proceed to the next scene or perform other actions
            }
            else if (serviceUser.authenticateUser(username, password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginSuccess.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
                scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
                System.out.println("Login successful!");

                // Proceed to the next scene or perform other actions
            }



            else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Invalide Username or Password");
                alert.showAndWait();
                //Show an error message or perform other actions
            }
        }
        catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }


    public void MoveToSignup(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
    }
}



