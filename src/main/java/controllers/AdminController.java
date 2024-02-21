package controllers;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;

public class AdminController {


    @FXML
    private TableColumn<User, String> id_adress;

    @FXML
    private TableColumn<User, DatePicker> id_dob;

    @FXML
    private TableColumn<User, String> id_email;

    @FXML
    private TableColumn<User, String> id_firstname;

    @FXML
    private TableColumn<User, String> id_gender;

    @FXML
    private TableColumn<User, String> id_lastname;

    @FXML
    private TableColumn<User, Integer> id_phone;

    @FXML
    private TableColumn<User, String> id_role;

    @FXML
    private TableColumn<User, String> id_username;
    @FXML
    private TableView<User> tv_users;

    @FXML
    private ComboBox<String> id_Choice;



    @FXML
    private Label Num_Arts;

    @FXML
    private Label Num_Auction;

    @FXML
    private Label Num_Events;

    @FXML
    private Label Num_Users;
     ServiceUser serviceUser = new ServiceUser();

    public void initialize() throws SQLException {
        initializeComboBoxContent();
        Num_Users.setText(String.valueOf(serviceUser.CountUsers()));


        id_Choice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Artist".equals(newValue)) {
                try {
                    ObservableList<User> observableList = FXCollections.observableList(serviceUser.DISPLAYARTIST());
                    tv_users.setItems(observableList);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            else  if ("Member".equals(newValue)) {
                try {
                    ObservableList<User> observableList = FXCollections.observableList(serviceUser.DISPLAYMember());
                    tv_users.setItems(observableList);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        try {
           ObservableList<User> observableList = FXCollections.observableList(serviceUser.DISPLAY());
           tv_users.setItems(observableList);
           id_username.setCellValueFactory(new PropertyValueFactory<>("Username"));
           id_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
           id_role.setCellValueFactory(new PropertyValueFactory<>("Role"));
           id_firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
           id_lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
           id_adress.setCellValueFactory(new PropertyValueFactory<>("Adress"));
           id_phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
           id_gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
           id_dob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
       }catch (SQLException e) {
           System.out.println(e.getMessage());
       }
}

    private void initializeComboBoxContent() {
        // Initialize  choices
        id_Choice.getItems().addAll("Member", "Artist"); // Example Role choices

    }

    public void logout(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Choice.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();

    }

    public void Delete(ActionEvent actionEvent) {
        User selectedItem = tv_users.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int User_id = selectedItem.getId_User();
            try {
                // Delete the item from the database
                serviceUser.DELETEUser(User_id);

                // Remove the item from the ObservableList
                ObservableList<User> userList = tv_users.getItems();
                userList.remove(selectedItem);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void Edit(ActionEvent actionEvent) {
        User selectedItem = tv_users.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                // Load the Edit_User.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Edit_User.fxml"));
                Parent editUserRoot = loader.load();

                // Get the controller associated with Edit_User.fxml
                EditUserController editUserController = loader.getController();

                // Pass the selected user to the EditUserController
                editUserController.initData(selectedItem);

                // Get the scene from any node in the current scene
                Scene scene = id_Choice.getScene();

                // Set the root of the current scene to the Edit_User scene
                scene.setRoot(editUserRoot);

                // Optionally, you can remove the item from the TableView
                ObservableList<User> userList = tv_users.getItems();
                userList.remove(selectedItem);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void initData(User user) {
    }
}