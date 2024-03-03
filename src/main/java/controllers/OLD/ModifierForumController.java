package controllers.OLD;
import entities.ForumEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceForumF;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierForumController {
    @FXML
    private TextField f_desc_mod;

    @FXML
    private TextField f_title_mod;

    @FXML
    private Button id_butt;

    private ForumEntity newForum;
    private ForumEntity oldForum;

    public void setData(ForumEntity forum)
    {
        this.f_desc_mod.setText(forum.getDescription());
        this.f_title_mod.setText(forum.getTitle());
        this.oldForum = forum;
    }
    ServiceForumF SF = new ServiceForumF();
    @FXML
    void SaveNewDate(ActionEvent event) {
        try {
            newForum = new ForumEntity(oldForum.getId_forum(), f_title_mod.getText(),f_desc_mod.getText(), oldForum.getReplies_num(),oldForum.getDate());
            SF.modifier(newForum);
            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Old/AfficherForumArtist.fxml"));
            id_butt.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
