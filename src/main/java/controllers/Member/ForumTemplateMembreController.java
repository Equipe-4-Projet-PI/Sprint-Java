package controllers.Member;

import entities.ForumEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ForumTemplateMembreController {

    @FXML
    private HBox box;

    @FXML
    private Label date_id;

    @FXML
    private Label desc_id;

    @FXML
    private Label posts_num;

    @FXML
    private Button title_id;

    private ForumEntity current_forum;

    @FXML
    void GoToForum(ActionEvent event) {
        try{
            Parent root = loadRootLayoutForForum();
            title_id.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Parent loadRootLayoutForForum() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AddPostMembre.fxml"));
        AddPostMembreController controller = new AddPostMembreController();
        loader.setController(controller);
        controller.setData(current_forum); // Add data to the controller
        Parent root = loader.load();
        return root;
    }
    public void setData(ForumEntity forum) {
        this.title_id.setText(forum.getTitle());
        this.desc_id.setText(forum.getDescription());
        this.posts_num.setText("" + forum.getReplies_num());
        this.date_id.setText("" + forum.getDate());
        this.current_forum = forum;
    }
}
