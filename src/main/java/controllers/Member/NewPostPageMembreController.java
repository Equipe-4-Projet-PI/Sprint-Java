package controllers.Member;

import entities.ForumEntity;
import entities.PostEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServicePostF;

import java.io.IOException;
import java.sql.SQLException;

public class NewPostPageMembreController {
    @FXML
    private TextField post_desc;

    @FXML
    private TextField post_title;

    private ForumEntity forum;

    @FXML
    void AfficherPostes(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Member/AfficherForumMembre.fxml"));
            post_title.getScene().setRoot(root);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjouterPoste(ActionEvent event) {
        try {
            ServicePostF sp=new ServicePostF();
            sp.ajouter(new PostEntity(forum.getId_forum(),1,post_title.getText(),post_desc.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Forum Ajouté");
            alert.showAndWait();


            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Old/AfficherForumArtist.fxml"));
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

    public void setData(ForumEntity currentForum) {

        this.forum = currentForum;
    }
}
