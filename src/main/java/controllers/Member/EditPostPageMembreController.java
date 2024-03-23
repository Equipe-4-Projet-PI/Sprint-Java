package controllers.Member;

import entities.ForumEntity;
import entities.PostEntity;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;
import services.ServicePostF;

import java.io.IOException;
import java.sql.SQLException;

public class EditPostPageMembreController {
    @FXML
    private TextField post_desc;

    @FXML
    private TextField post_title;

    int user_id;
    private ForumEntity forum;
    private User userlogged;
    private PostEntity post;
    @FXML
    void AfficherPostes(ActionEvent event)  throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AfficherForumMembre.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = post_title.getScene();
        scene.setRoot(loginSuccessRoot);
        AfficherForumMembreController afficherForumMembreController = loader.getController();
        afficherForumMembreController.initUser(userlogged);
    }

    @FXML
    void AjouterPoste(ActionEvent event) {
        try {
            ServicePostF sp=new ServicePostF();
            sp.modifier(new PostEntity(post.getId_post(),forum.getId_forum(),user_id,post_desc.getText(),post_title.getText(),post.getLike_num(),post.getTime(),post.getDate()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Forum Modifier");
            alert.showAndWait();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AfficherForumMembre.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = post_title.getScene();
            scene.setRoot(loginSuccessRoot);
            AfficherForumMembreController afficherForumMembreController = loader.getController();
            afficherForumMembreController.initUser(userlogged);

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AddPostMembre.fxml"));
//            Parent loginSuccessRoot = loader.load();
//            Scene scene = post_title.getScene();
//            AddPostMembreController addPostMembreControllerController = new AddPostMembreController();
//            loader.setController(addPostMembreControllerController);
//            Parent loginSuccessRoot = loader.load();
//            Scene scene = post_title.getScene();
//            scene.setRoot(loginSuccessRoot);

//            addPostMembreControllerController.setData(forum,userlogged);


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize()
    {
        PutData();
    }
    private void PutData()
    {
        post_title.setText(post.getTitle());
        post_desc.setText(post.getText());
    }
    public void setData(ForumEntity currentForum , @NotNull User user, @NotNull PostEntity p) {
        post = p;
        this.forum = currentForum;
        user_id=user.getId_User();
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
        userlogged.setImageURL(user.getImageURL());
    }


}
