package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditUserController {

    @FXML
    private Label Num_Arts;

    @FXML
    private Label Num_Auction;

    @FXML
    private Label Num_Events;

    @FXML
    private Label Num_Users;


    @FXML
    private TextField id_Adress;

    @FXML
    private PasswordField id_CPassword;

    @FXML
    private DatePicker id_DOB;

    @FXML
    private TextField id_Email;

    @FXML
    private TextField id_Nom;

    @FXML
    private TextField id_Prénom;

    @FXML
    private ComboBox<String> id_Role;
    @FXML
    private ComboBox<String> id_genre;

    @FXML
    private TextField id_Telephone;

    @FXML
    private TextField id_Utlisateur;

    @FXML
    private PasswordField id_mdp;

    @FXML
    private ImageView logout;

    @FXML
    private TextField id_user;

    @FXML
    void Annuler_Edit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUI.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_user.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Annuler la Modification");
        alert.setHeaderText(null);
        alert.show();
    }


    @FXML
    void Confirm_Edit(ActionEvent event) {
        // Get the edited values from the text fields
        int userId = Integer.parseInt(id_user.getText());
        String firstName = id_Nom.getText();
        String lastName = id_Prénom.getText();
        String address = id_Adress.getText();
        int phone = Integer.parseInt(id_Telephone.getText());
        LocalDate dob = id_DOB.getValue();
        String email = id_Email.getText();
        String username = id_Utlisateur.getText();
        String password = id_mdp.getText();
        String role = id_Role.getValue();
        String gender = id_genre.getValue();

        // Create a new User object with the edited values
        User editedUser = new User(); // Initialize a new User object
        editedUser.setId_User(userId); // Assuming userId is accessible in this method
        editedUser.setUsername(username);
        editedUser.setEmail(email);
        editedUser.setPassword(password);
        editedUser.setRole(role);
        editedUser.setFirstName(firstName);
        editedUser.setLastName(lastName);
        editedUser.setAdress(address);
        editedUser.setPhone(phone);
        editedUser.setDOB(dob.toString());
        editedUser.setGender(gender);

        // Initialize ServiceUser
        ServiceUser serviceUser = new ServiceUser();

        try {
            // Update the user details using the ServiceUser
            serviceUser.UPDATE(editedUser);

            // Show a confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit User");
            alert.setHeaderText(null);
            alert.setContentText("User details updated successfully");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUI.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = id_user.getScene();
            scene.setRoot(loginSuccessRoot);
        } catch (SQLException | IOException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error updating user details: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void logout(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Adress.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.show();

    }
    private void initializeComboBoxContent() {
        // Initialize  choices
        id_Role.getItems().addAll("Member", "Artist"); // Example Role choices
        id_genre.getItems().addAll("Homme", "Femme", "Autre"); // Example gender choices

    }
    public void initialize() throws SQLException {
        initializeComboBoxContent();
    }
    public void initData(User user) {
        // Set values of the fields using the attributes of the User object
        id_Nom.setText(user.getFirstName());
        id_Prénom.setText(user.getLastName());
        id_Adress.setText(user.getAdress());
        id_Telephone.setText(String.valueOf(user.getPhone()));
        id_DOB.setValue(LocalDate.parse(user.getDOB())); // Assuming getDOB() returns a String in "yyyy-MM-dd" format
        id_Email.setText(user.getEmail());
        id_Utlisateur.setText(user.getUsername());
        id_mdp.setText(user.getPassword());
        id_Role.setValue(user.getRole());
        id_genre.setValue(user.getGender());
        id_user.setText(String.valueOf(user.getId_User()));
    }
}