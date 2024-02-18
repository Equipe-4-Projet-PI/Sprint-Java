package controllers;

import entities.ForumEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import services.ServiceForum;

import java.io.IOException;
import java.sql.SQLException;

public class ForumTemplateController {

    @FXML
    private Label date_id;

    @FXML
    private Label desc_id;

    @FXML
    private Label posts_num;

    @FXML
    private Label title_id;

    @FXML
    private Button butt_mod;

    @FXML
    private Button del_butt;

    private ForumEntity fdetest;
    private ServiceForum SF = new ServiceForum();

    public void setData(ForumEntity forum) {
        this.title_id.setText(forum.getTitle());
        this.desc_id.setText(forum.getDescription());
        this.posts_num.setText("" + forum.getReplies_num());
        this.date_id.setText("" + forum.getDate());
        this.fdetest = forum;
    }
    @FXML
    void Delete(ActionEvent event) {
        try {
            SF.supprimer(fdetest);
            Parent root= FXMLLoader.load(getClass().getResource("/AfficherForum.fxml"));
            del_butt.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Modifier(ActionEvent event) {

    }

}
