package controllers;

import entities.Disscussion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceMessage;
import services.ServiceUser;


import java.sql.SQLException;


public class DiscussionController {

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


//    ServiceDisscussion serviceDisscussion = new ServiceDisscussion() ;
    ServiceUser serviceUser = new ServiceUser() ;
    ServiceMessage serviceMessage = new ServiceMessage() ;

    public void setData(Disscussion disscussion) throws SQLException {
//        Image image = new Image("bell.png");
//        img.setImage(image);
        receiver.setText(serviceUser.getbyid(disscussion.getIdReceiver()).getUsername());
        content.setText(serviceMessage.getContentById(disscussion.getIdDis()).getContent());

    }
}
