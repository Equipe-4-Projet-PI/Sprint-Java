package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceForum;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierForumController {
    @FXML
    private TextField f_desc_mod;

    @FXML
    private TextField f_title_mod;

    @FXML
    private Button id_butt;

    ServiceForum SF = new ServiceForum();
//    @FXML
//    void SaveNewDate(ActionEvent event) {
//        try {
//            SF.modifier();
//            Parent root= FXMLLoader.load(getClass().getResource("/AfficherForum.fxml"));
//            id_butt.getScene().setRoot(root);
//        } catch (SQLException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
