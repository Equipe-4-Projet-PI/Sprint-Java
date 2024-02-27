package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import services.ServiceUser;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class SignUpController {


    ServiceUser serviceUser = new ServiceUser();

    @FXML
    private TextField id_Username;
    @FXML
    private TextField id_Email;
    @FXML
    private PasswordField id_Password;
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
    private PasswordField id_CPassword;
    @FXML

    public void initialize() {
        initializeComboBoxContent();
    }
    public void SignUp(javafx.event.ActionEvent actionEvent) {
        String username = id_Username.getText();
        String email = id_Email.getText();
        String password = id_Password.getText();
        String role = id_Role.getValue();
        String firstName = id_Firstname.getText();
        String lastName = id_Lastname.getText();
        String address = id_Adress.getText();
        String gender = id_Gender.getValue();
        String phoneText = id_Phone.getText();
        LocalDate dob = id_Dob.getValue();
        String Cpassword = id_CPassword.getText();

        // Check if any required field is empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role == null || firstName.isEmpty() ||
                lastName.isEmpty() || address.isEmpty() || gender == null || phoneText.isEmpty() || dob == null) {
            showAlert("Error", "Please fill in all the fields.");
            return;
        }
        else if (!password.equals(Cpassword)) {
            showAlert("Error", "les 2 password son't faux");
            return;
        }

        // Validate phone number
        int phone ;
        try {
            phone = Integer.parseInt(phoneText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Entrer un Numéro Télephone Valide");
            return;
        }
        // Validate email format
        if (!isValidEmail(email)) {
            showAlert("Error", "Entrer un Adress Email Valide");
            return;
        }

        // Now you can proceed with adding the user
        try {
            serviceUser.ADD(new User(username, email, password, role, firstName, lastName, address, gender, phone, dob));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bienvenue");
            alert.setContentText("Bienvenue Dans ArtyVenci");
            alert.showAndWait();
            showAlert("Success", "User added successfully.");
            // Redirect to login page
            redirectToLogin();
        } catch (SQLException | IOException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private void redirectToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot);
    }

    private void initializeComboBoxContent() {
            // Initialize  choices
            id_Role.getItems().addAll("Member", "Artist"); // Example Role choices
            id_Gender.getItems().addAll("Homme", "Femme", "Autre"); // Example gender choices
    }

    public void GoBackClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
    }
}
