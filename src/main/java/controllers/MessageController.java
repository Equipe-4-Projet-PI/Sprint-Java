package controllers;

import entities.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import services.ServiceMessage;
import java.sql.SQLException;
import java.util.List;

public class MessageController {

    @FXML
    private ListView<String> messageListView;

    private ServiceMessage serviceMessage;

    public MessageController() {
        serviceMessage = new ServiceMessage();
    }

    @FXML
    public void initialize() {
        afficherMessages();
    }

    private void afficherMessages() {
        try {
            List<Message> messages = serviceMessage.afficher();
            ObservableList<String> messageStrings = FXCollections.observableArrayList();
            for (Message message : messages) {
                String messageString = "ID: " + message.getIdMsg() +
                        ", Date: " + message.getTime() +
                        ", Sender: " + message.getIdSender() +
                        ", Discussion ID: " + message.getIdDis() +
                        ", Content: " + message.getContent() +
                        ", Reaction: " + message.getReaction();
                messageStrings.add(messageString);
            }
            messageListView.setItems(messageStrings);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
