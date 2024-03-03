package controllers;

import entities.Disscussion;
import entities.Message;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceMessage;
import services.ServiceUser;
import services.ServiceDisscussion;


import java.io.File;
import java.io.IOException;
import java.security.cert.PolicyNode;
import java.sql.SQLException;
import java.util.List;
//import com.github.michaelederau.emojipicker.EmojiPicker;



public class DiscussionController {


    @FXML
    public Button Afficherdis;

    @FXML
    private VBox vbox;

    @FXML
    private HBox box;
    @FXML
    private Label content;

    @FXML
    private ImageView img;

    @FXML
    private Label receiver;

    private Disscussion dis;
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    ServiceUser serviceUser = new ServiceUser();
    ServiceMessage serviceMessage = new ServiceMessage();

    public DiscussionController() {
    }

    public void setData(Disscussion disscussion) throws SQLException {
        User user = new User() ;
//        Image image = new Image("bell.png");
//        img.setImage(image);
        File file = new File("C:\\Users\\Lenovo\\Desktop\\ArtyVenci\\src\\main\\resources\\images\\pers1.png"  ) ;
        Image image = new Image(file.toURI().toString());
        img.setImage(image);


        receiver.setText(serviceUser.getbyid(disscussion.getIdReceiver()).getUsername());
        content.setText(serviceMessage.getContentById(disscussion.getIdDis()).getContent());
        dis = disscussion;
    }

    @FXML
    void setMethod () throws SQLException {
        EnvoiMessageController envoiMessageController = new EnvoiMessageController() ;
        envoiMessageController.afficherMessages() ;
    }


}








