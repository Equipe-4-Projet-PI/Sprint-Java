package controllers;

import entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherDiscussionController implements Initializable {


    @FXML
    private VBox Msg;
    private ServiceMessage serviceMessage = new ServiceMessage();
    private ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    private ServiceUser serviceUser = new ServiceUser();

    public void setVBoxId(String id) {
        Msg.setId(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            int discussionId = 1; // Remplacez ceci par l'ID de la discussion que vous souhaitez afficher
//
////            MessageController dc = new MessageController();
//            EnvoiMessageController controller=new EnvoiMessageController();
//            controller.afficherDiscussion(new ActionEvent()) ;
//            System.out.println("ID de la discussion sélectionnée : " + discussionId);
//
//            List<Message> messages = serviceMessage.afficheridDis(discussionId);
//
//            for (Message message : messages) {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
//                VBox msgBox = fxmlLoader.load();
//                MessageController msgController = fxmlLoader.getController();
//                msgController.setData(message);
//                Msg.getChildren().add(msgBox);
//            }
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }

    }}