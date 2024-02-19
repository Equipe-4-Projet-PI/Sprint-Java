package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.ServiceUser;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {


    ServiceUser serviceUser = new ServiceUser();

    @FXML
    private TextField id_Username;
    @FXML
    private TextField id_Email;
    @FXML
    private TextField id_Password;
    @FXML
    private ComboBox<String> id_Role;
    @FXML
    private TextField id_Firstname;
    @FXML
    private TextField id_Lastname;
    @FXML
    private TextField id_Adress;
    @FXML
    private DatePicker id_Dob;
    @FXML
    private ComboBox<String> id_Gender;
    @FXML
    private TextField id_Phone;
    @FXML

    public void initialize() {
        initializeComboBoxContent();
    }
    public void SignUp(javafx.event.ActionEvent actionEvent) {
        try {
            serviceUser.ADD(new User(id_Username.getText(), id_Email.getText(), id_Password.getText(), id_Role.getValue(), id_Firstname.getText(), id_Lastname.getText(), id_Adress.getText(),id_Gender.getValue(),Integer.parseInt(id_Phone.getText()), id_Dob.getValue()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setContentText("USER ADD");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
            scene.setRoot(loginSuccessRoot);
        }
        catch (SQLException | IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    private void initializeComboBoxContent() {
            // Initialize  choices
            id_Role.getItems().addAll("Member", "Artist"); // Example Role choices
            id_Gender.getItems().addAll("Male", "Female", "Other"); // Example gender choices
    }

    public void GoBackClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
    }
}
