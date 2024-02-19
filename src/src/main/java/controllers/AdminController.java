package controllers;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
     ServiceUser serviceUser = new ServiceUser();

    public void initialize() throws SQLException {
        initializeComboBoxContent();


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
        alert.show();

    }
}