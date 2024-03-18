package controllers;

import entities.Disscussion;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.io.File;
import java.sql.SQLException;

public class DiscussionController {

    @FXML
    public Button Afficherdis;

    @FXML
    public HBox box;

    @FXML
    public HBox box1;

    @FXML
    public Label content;

    @FXML
    public ImageView img;

    @FXML
    public Label receiver;

    @FXML
    public VBox vbox;


    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    ServiceUser serviceUser = new ServiceUser();
    ServiceMessage serviceMessage = new ServiceMessage();

    private Disscussion dis;

    public DiscussionController() {
    }

    public void setData(Disscussion disscussion) throws SQLException {
        User user = new User();
//        Image image = new Image("bell.png");
//        img.setImage(image);
        File file = new File("C:\\Users\\Lenovo\\Desktop\\ArtyVenci\\src\\main\\resources\\images\\pers1.png");
        Image image = new Image(file.toURI().toString());
        img.setImage(image);


        receiver.setText(serviceUser.getbyid(disscussion.getIdReceiver()).getUsername());
        content.setText(serviceMessage.getContentById(disscussion.getIdDis()).getContent());
        dis = disscussion;

    }
}
