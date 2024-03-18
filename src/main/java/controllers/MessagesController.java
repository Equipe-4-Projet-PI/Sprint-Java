package controllers;

import entities.Disscussion;
import entities.Message;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MessagesController implements Initializable {

    @FXML
    public VBox Msg;

    @FXML
    public ScrollPane messages;

    //taw mba3d
    @FXML
    public Button notification;

    //déclarations des services
    public ServiceMessage serviceMessage = new ServiceMessage();
    public ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    public ServiceUser serviceUser = new ServiceUser();

    //autres variables
    public Disscussion discussion;


    //Constructeur
    public MessagesController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherMessages() ;
    }

    //Afficher les Messages
    public void setData(Disscussion discussion) {
        this.discussion = discussion;
        System.out.println("test f west el setData west MessagesController : " + discussion.getIdDis());
        System.out.println("test f west el setData west MessagesController 3la el discussion : " + discussion);
    }

    public void afficherMessages() {
        try {
            // Chargez et affichez les messages de la discussion sélectionnée
            if (discussion != null) {
//                System.out.println("si ce message s'affiche la methode est appelé");
                 int discussionId = discussion.getIdDis(); // Supposons que getId() renvoie l'ID de la discussion
                // Utilisez l'ID de la discussion pour charger les messages de cette discussion
                ObservableList<Message> messages = FXCollections.observableList(serviceMessage.afficheridDis(discussionId));
//                ObservableList<Message> messages = FXCollections.observableList(serviceMessage.afficher());
//                System.out.println("test f west el afficherMessages west MessagesController 3la el discussion: " + discussion);
//                System.out.println("test f west el afficherMessages west MessagesController: " + discussionId);
                for (Message mesg : messages) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
                    VBox cardBox = fxmlLoader.load();
                    MessageController msgController = fxmlLoader.getController();
                    msgController.setData(mesg);
                    Msg.getChildren().add(cardBox);
                }
            } else {
                // Affichez une alerte si la discussion est nulle
                showAlert(Alert.AlertType.ERROR, "Erreur", "Discussion non sélectionnée");
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les Messages: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
