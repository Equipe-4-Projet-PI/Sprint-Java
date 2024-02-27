package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class DetailsProfileController {

     @FXML
    private TextField id_Nom;

    @FXML
    private TextField id_adress;

    @FXML
    private PasswordField id_confirm;

    @FXML
    private DatePicker id_date;

    @FXML
    private TextField id_email;

    @FXML
    private PasswordField id_mdp;

    @FXML
    private TextField id_prenom;

    @FXML
    private TextField id_tele;

    @FXML
    private TextField id_user;

    @FXML
    private Label nav_name;

    @FXML
    private Label statue_name;
    private User userlogged;


    private String id_Utlisateur;
    private String Role;
    private String Gender ;

    @FXML
    void Annuler_Edit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile_propos.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProfileController profileController = loader.getController();
        profileController.initData(userlogged);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Annuler la Modification");
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    void Confirme_Edit(ActionEvent event) {
        // Get the edited values from the text fields
        int userId = Integer.parseInt(id_user.getText());
        String firstName = id_Nom.getText();
        String lastName = id_prenom.getText();
        String address = id_adress.getText();
        int phone = Integer.parseInt(id_tele.getText());
        LocalDate dob = id_date.getValue();
        String email = id_email.getText();
        String username = id_Utlisateur;
        String password = id_mdp.getText();
        String role = Role;
        String gender = Gender;

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
        System.out.println(editedUser);

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile_Propos.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = id_user.getScene();
            scene.setRoot(loginSuccessRoot);
            ProfileController profileController = loader.getController();
            profileController.initData(editedUser);
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
    void Details(ActionEvent event) {

    }

    @FXML
    void Gallerie(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Annuler la Modification" );
        alert.showAndWait();
    }

    @FXML
    void Post_Blog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Annuler la Modification" );
        alert.showAndWait();
    }

    public void initData(User user) {
        statue_name.setText(user.getFirstName() + " " + user.getLastName());
        nav_name.setText("Bienvenue,"+ " "+user.getFirstName());
        id_Nom.setText(user.getFirstName());
        id_prenom.setText(user.getLastName());
        id_adress.setText(user.getAdress());
        id_tele.setText(String.valueOf(user.getPhone()));
        id_date.setValue(LocalDate.parse(user.getDOB())); // Assuming getDOB() returns a String in "yyyy-MM-dd" format
        id_email.setText(user.getEmail());
        id_mdp.setText(user.getPassword());
        id_user.setText(String.valueOf(user.getId_User()));
         id_Utlisateur = user.getUsername();
         Role = user.getRole();
        Gender= user.getGender();


        userlogged = new User();
        userlogged.setGender(user.getGender());
        userlogged.setDOB(user.getDOB());
        userlogged.setPhone(user.getPhone());
        userlogged.setAdress(user.getAdress());
        userlogged.setUsername(user.getUsername());
        userlogged.setEmail(user.getEmail());
        userlogged.setFirstName(user.getFirstName());
        userlogged.setPassword(user.getPassword());
        userlogged.setLastName(user.getLastName());
        userlogged.setId_User(user.getId_User());
        userlogged.setRole(user.getRole());
    }
}