package controllers;

import entities.Disscussion;
import entities.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.sql.SQLException;

public class MessageController {

    @FXML
    private Label messagee;

    @FXML
    private Label time;

    @FXML
    private VBox vbox;

    private Message curr_message = new Message();


    ServiceDisscussion serviceDisscussion = new ServiceDisscussion() ;
    ServiceUser serviceUser = new ServiceUser() ;
    ServiceMessage serviceMessage = new ServiceMessage() ;

    public MessageController() {
    }


//    public void setData(Message message) throws SQLException {
//        this.curr_message = message;
//        System.out.println(message);
////        System.out.println(message);
////        System.out.println(serviceMessage.getContentById(message.getIdDis()).getTime().toString());
//
//    }
//
//    @FXML
//    public void initialize()
//    {
//        try {
//            messagee.setText(serviceMessage.getContentById(curr_message.getIdDis()).getContent());
//            time.setText(serviceMessage.getContentById(curr_message.getIdDis()).getTime().toString());
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    public void setData(Message message) throws SQLException {

        String content = serviceMessage.getContentById(message.getIdMsg()).getContent();
        String heure = serviceMessage.getContentById(message.getIdMsg()).getTime().toString() ;

        System.out.println(content);
        System.out.println(heure);

        time.setText(heure);
        messagee.setText(content);
    }

}
