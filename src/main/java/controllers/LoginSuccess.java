package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginSuccess {

    @FXML
    private Label nav_name;

    @FXML
    private Label post_name;

    @FXML
    private Label statue_name;

    @FXML
    void Details(ActionEvent event) {

    }

    @FXML
    void Gallerie(ActionEvent event) {

    }

    @FXML
    void Post_Blog(ActionEvent event) {

    }

    public void initData(User user) {
        nav_name.setText("Bienvenue,"+ " "+user.getFirstName());
        post_name.setText(user.getFirstName() + " " + user.getLastName());
        statue_name.setText(user.getFirstName() + " " + user.getLastName());

    }
}