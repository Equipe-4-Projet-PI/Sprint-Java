package controllers;

import entities.PostEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import services.ServicePost;

import java.io.IOException;
import java.sql.SQLException;


public class PostTemplateController {

    @FXML
    private Label Like_num_id;

    @FXML
    private Label post_label_id;

    @FXML
    private Label timestamp_label_id;

    @FXML
    private Label title_label_id;

    @FXML
    private Label user_label_id;

    private PostEntity post;

    public void setData(PostEntity postEntity)
    {
        title_label_id.setText(postEntity.getTitle());
        post_label_id.setText(postEntity.getText());
        Like_num_id.setText(""+postEntity.getLike_num());
        timestamp_label_id.setText(""+postEntity.getTime());
        this.post = postEntity;
//        user_label_id = ?
    }
    @FXML
    private Button like_butt_id;
    @FXML
    void LikeAction(ActionEvent event) throws SQLException {
        int likes = post.getLike_num() + 1;
        ServicePost sp = new ServicePost();
        PostEntity newPost = new PostEntity(post.getId_post(),post.getId_forum(),post.getId_user(),post.getTitle(),post.getText(),likes,post.getTime(),post.getDate());
        sp.modifier(newPost);
        reloadPage();
    }

    void reloadPage()
    {
        Parent root= post_label_id.getScene().getRoot();
        post_label_id.getScene().setRoot(root);
    }

}
