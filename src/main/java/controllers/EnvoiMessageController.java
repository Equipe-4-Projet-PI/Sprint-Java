package controllers;

import entities.Disscussion;
import entities.Message;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EnvoiMessageController implements Initializable {

    @FXML
    private VBox dis;


    @FXML
    private Button deleteDis;

    @FXML
    private Button Envoi;

    @FXML
    private TextField msg;

    @FXML
    private TextField username;

    @FXML
    public VBox Msg;



    public EnvoiMessageController() {
        // Initialisation de dis
        dis = new VBox();
    }


    private ServiceMessage serviceMessage = new ServiceMessage();
    private ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    private ServiceUser serviceUser = new ServiceUser();
    private  User sender = new User();
    private User receriver = new User();



    @FXML
    void ajouter(ActionEvent event) {
        try {
            String messageContent = msg.getText().trim();
            if (!messageContent.isEmpty()) {
//                serviceMessage.ajouter(new Message(sender.getId_User(), messageContent, null));
                serviceMessage.ajouter(new Message(serviceUser.getbyid(1).getId_User(), serviceDisscussion.getDiscussionById(1).getIdDis(), messageContent, null));
                showAlert(Alert.AlertType.INFORMATION, "Succès d'envoi", "Message envoyé");
                msg.clear();
            } else {
                showAlert(Alert.AlertType.WARNING, "Message vide", "Le contenu du message est vide.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'envoi du message: " + e.getMessage());
        }
    }

    private void afficherDiscussions() {
        try {
            ObservableList<Disscussion> discussions = FXCollections.observableList(serviceDisscussion.afficher());
            for (Disscussion discussion : discussions) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/discussion.fxml"));
                HBox cardBox = fxmlLoader.load();
                DiscussionController discController = fxmlLoader.getController();
                discController.setData(discussion);
                dis.getChildren().add(cardBox);
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les discussions: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        afficherDiscussions();
        try {
            afficherDiscussions();
            afficherMessages() ;
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les discussions: " + e.getMessage());
        }
    }

    @FXML
    public void signalerMessage(ActionEvent event) {
        // Lancement de la fenêtre de signalement du message
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignalerMessage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Signaler un message");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir la fenêtre de signalement du message.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void btnNotificationOnAction(ActionEvent event) throws SQLException {

        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;

        tray.setAnimationType(type);
        tray.setTitle("Nouveau Message");
        tray.setMessage("Nouveau Message from  :" + serviceUser.getbyid(serviceDisscussion.getDiscussionById(1).getIdSender()).getUsername());
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
    }


    @FXML
    void AjouterDisc(ActionEvent event) throws SQLException, IOException {
        Disscussion d = new Disscussion( 1 , chercheruser(event).getId_User()) ;

        if(serviceDisscussion.Compare(d)){

            showAlert(Alert.AlertType.INFORMATION, "Déjà disponible", "Discussion déjà existante");
        }
        else { showAlert(Alert.AlertType.CONFIRMATION, "Discussion Créee", "Discussion Créee avec : " +chercheruser(event).getUsername());
            serviceDisscussion.ajouter(d);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnvoiMessage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Signaler un message");
        stage.show();
    }


    @FXML
    User chercheruser(Event event) throws SQLException {
        String userName = username.getText().trim() ;
        receriver = serviceUser.getbyUsername(userName) ;

        if(receriver==null){showAlert(Alert.AlertType.ERROR, "Erreur", "Utilisateur non trouvé " );}
//        else {System.out.println(receriver.getUsername());}
        return receriver ;
    }


    //la methode afficherMessages() fonctionne statiquement
    public void afficherMessages() throws SQLException {

        try {
//            List<Message> messages = serviceMessage.afficheridDis(recupereridDis(disscussion));
            List<Message> messages = serviceMessage.afficheridDis(1);
            List<Message> messageSender  , messageReceiver;
//            System.out.println(recupereridDis(disscussion));
            for (Message message : messages) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
                VBox msgBox = fxmlLoader.load();
                MessageController msgController = fxmlLoader.getController();
                msgController.setData(message);
                Msg.getChildren().add(msgBox);
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les messages: " + e.getMessage());
        }
    }

    //essai 99999

    public int recupereridDis() throws SQLException {

        DiscussionController dc = new DiscussionController() ;
        ServiceDisscussion sd = new ServiceDisscussion() ;

        int id = 1;
        HBox hbox = (HBox) dis.getChildren().get(3);
        System.out.println(hbox);
        if (!hbox.getChildren().isEmpty()) {
//            id = hbox.getChildren().indexOf(dc.i);
            System.out.println("retour : " +dc);
            System.out.println("List is not empty");
//            System.out.println(hbox.getChildren());
            System.out.println(id);
        } else {
            // Handle the case when the list is empty
            System.out.println("List is empty");
        }

        Button btn = (Button) hbox.getChildren().get(2);
        System.out.println(btn);

        if (btn.isPressed()) {
////            serviceMessage.getContentById(disscussion.getIdDis()).getContent()
//                hbox.getChildren().get(serviceDisscussion.getDiscussionById(disscussion.getIdDis()).getIdDis()) ;
//            System.out.println(serviceDisscussion.getDiscussionById(disscussion.getIdDis()).getIdDis());
//            System.out.println("btn pressed");
//            id = hbox.getChildren().indexOf(dc.Afficherdis);
//            System.out.println(id);
//            id = dis.getChildren().indexOf(dc.Afficherdis) ;
            System.out.println("id : " +id);
            System.out.println("ktibtek : " + hbox.getChildren().indexOf(dis));
        }
        else {
            System.out.println("btn not pressed");
        }

        System.out.println("id : " +id);
        System.out.println(id);
        return id;
    }

    @FXML
    void SupprimerDisc(ActionEvent event) throws SQLException {
        Disscussion d = new Disscussion( 1 , chercheruser(event).getId_User()) ;

        if(serviceDisscussion.Compare(d)){
            showAlert(Alert.AlertType.CONFIRMATION, "Suppression", "Voulez vous vraiment supprimer toute la conversation avec :"+chercheruser(event).getUsername() +" ? ");
            serviceDisscussion.supprimer(d);
        }
        else { showAlert(Alert.AlertType.ERROR, "Discussion pas disponible", "Discussion non trouvée");
        }
    }


}
