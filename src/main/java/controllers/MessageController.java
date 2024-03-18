package controllers;

import entities.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.sql.SQLException;

public class MessageController {

    //déclaration des services
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion() ;
    ServiceUser serviceUser = new ServiceUser() ;
    ServiceMessage serviceMessage = new ServiceMessage() ;

    //Constructeur de controller
    public MessageController() {
    }

    @FXML
    public Label messagee;

    @FXML
    public VBox vbox;


    public void setData(Message message) throws SQLException {

        String content = serviceMessage.getContentById(message.getIdMsg()).getContent();
        String heure = serviceMessage.getContentById(message.getIdMsg()).getTime().toString() ;
        String reaction = serviceMessage.getContentById(message.getIdMsg()).getReaction();

//        System.out.println(content);
//        System.out.println(heure);

//        time.setText(heure);
        messagee.setText(content);
    }

}
