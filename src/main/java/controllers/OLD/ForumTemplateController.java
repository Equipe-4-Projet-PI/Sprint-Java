package controllers.OLD;

import entities.ForumEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import services.ServiceForumF;

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
    private Button title_id;

    @FXML
    private Button butt_mod;

    @FXML
    private Button del_butt;

    private ForumEntity fdetest;
    private ServiceForumF SF = new ServiceForumF();

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
            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Old/AfficherForumArtist.fxml"));
            del_butt.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Modifier(ActionEvent event) {
        try{
            Parent root= loadRootLayout();
            del_butt.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private Parent loadRootLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Old/ModifierForum.fxml"));
        ModifierForumController controller = new ModifierForumController();
        loader.setController(controller);
        Parent root = loader.load();
        controller.setData(fdetest); // Add data to the controller
        return root;
    }

    @FXML
    void GoToForum(ActionEvent event) {
        try{
            Parent root = loadRootLayoutForForum();
            del_butt.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Parent loadRootLayoutForForum() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Old/AddPostArtist.fxml"));
        AddPostController controller = new AddPostController();
        loader.setController(controller);
        controller.setData(fdetest); // Add data to the controller
        Parent root = loader.load();
        return root;
    }


}
