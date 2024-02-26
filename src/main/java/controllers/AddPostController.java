package controllers;

import entities.ForumEntity;
import entities.PostEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServicePost;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AddPostController {

    private ForumEntity forum;
    @FXML
    private Label forum_name_id;

    @FXML
    private VBox id_vbox_posts;

    @FXML
    private Button forumPage_id;



    //SERVICE POST
    ServicePost sp = new ServicePost();

    public void setData(ForumEntity forumEntity)
    {

        this.forum = forumEntity;

    }
    private void SetDataAgain()
    {
        this.forum_name_id.setText(forum.getTitle());
    }

    @FXML
    void initialize()
    {
//        try{
//            ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());
//            for (int i = 0; i < observableList.size(); i++) {
//                FXMLLoader fxmlLoader= new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/PostTemplate.fxml"));
//                HBox cardBox = fxmlLoader.load();
//                PostTemplateController cardController = fxmlLoader.getController();
//                cardController.setData(observableList.get(i));
//                id_vbox_posts.getChildren().add(cardBox);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try {
            //SET FORUM NAME
            SetDataAgain();

            ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());

            // Filter the list based on the search text
            List<PostEntity> filteredList = observableList.stream()
                    .filter(e -> e.getId_forum() == forum.getId_forum())
                    .collect(Collectors.toList());

            // Load and display filtered data
            for (PostEntity f : filteredList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForumPages/Artist/PostTemplate.fxml"));
                HBox cardBox = fxmlLoader.load();
                PostTemplateController cardController = fxmlLoader.getController();
                cardController.setData(f);
                id_vbox_posts.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void GotoforumPage(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Artist/AfficherForumArtist.fxml"));
            forumPage_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    ///ADD BUTTON LOGIC

    @FXML
    private Button Add_Post_Butt_Id;
    @FXML
    void AddForum(ActionEvent event) {
        try{
            Parent root = loadRootLayoutForForum();
            Add_Post_Butt_Id.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private Parent loadRootLayoutForForum() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Artist/NewPostPage.fxml"));
        NewPostPageController controller = new NewPostPageController();
        loader.setController(controller);
        Parent root = loader.load();
        return root;
    }

}
