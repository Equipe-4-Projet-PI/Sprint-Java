package controllers;

import entities.PostEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServicePost;

import java.io.IOException;
import java.sql.SQLException;

public class NewPostPageController {
    @FXML
    private TextField post_desc;

    @FXML
    private TextField post_title;

    @FXML
    void AfficherPostes(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Artist/AfficherForumArtist.fxml"));
            post_title.getScene().setRoot(root);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjouterPoste(ActionEvent event) {
        try {
            ServicePost sp=new ServicePost();
            sp.ajouter(new PostEntity(1,1,post_title.getText(),post_desc.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Forum Ajouté");
            alert.showAndWait();


            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Artist/AfficherForumArtist.fxml"));
            post_title.getScene().setRoot(root);


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
