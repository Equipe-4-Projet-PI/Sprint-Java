package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
        // Authenticate the user
        User user = serviceUser.GetUser(username, password);
        System.out.println(user);
        if (user != null) {
            FXMLLoader loader;
            Parent root;

            if (user.getRole().equals("Admin")) {
                loader = new FXMLLoader(getClass().getResource("/AdminUI.fxml"));
                root = loader.load();
                AdminController adminController = loader.getController();
                adminController.initData(user);
            } else {
                loader = new FXMLLoader(getClass().getResource("/Product.fxml"));
                root = loader.load();
                ProductController productController = loader.getController();
                productController.initUser(user);
//
            }

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            // Show an error message for invalid username or password
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Username or Password");
            alert.showAndWait();
        }
    } catch (Exception e) {
        // Handle any exceptions
        System.out.println("Error: " + e.getMessage());
    }
}

    public void MoveToSignup(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
    }

    public void resetPassword(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reset_Account.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
    }
}



