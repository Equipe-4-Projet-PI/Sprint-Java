//package controllers;
//
//import entities.Disscussion;
//import entities.Message;
//import entities.User;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import services.DisListener;
//import services.ServiceDisscussion;
//import services.ServiceMessage;
//import services.ServiceUser;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class Envoi_MessageController
////        implements DisListener<Disscussion>
//{
//
//    ServiceMessage serviceMessage = new ServiceMessage() ;
//    ServiceDisscussion serviceDisscussion = new ServiceDisscussion() ;
//    ServiceUser serviceUser = new ServiceUser() ;
//    User sender = new User() ;
//    //List<Disscussion> dis= serviceDisscussion.afficher();
//    int idSender = sender.getId_User();
//
//
//    @FXML
//    private ScrollPane discussion;
//
//    @FXML
//    private VBox dis;
//    @FXML
//    private VBox Msg;
//
//    @FXML
//    private Button Envoi;
//
//    @FXML
//    private TextField msg;
//
//    @FXML
//    private Button nonlu;
//
//    @FXML
//    private Button signal;
//
//
//    @FXML
//    void ajouter(ActionEvent event) throws IOException {
//        try {
//            if (msg.getText().isEmpty()) {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
//                alert .setTitle("Messsage vide !!");
//                alert.setContentText("Contenu de message manquant vous ne pouver pas envoyer un message vide");
//                alert.showAndWait();
//            }
//            else
//            {
//                serviceMessage.ajouter(new Message(idSender ,msg.getText() ,null));
//                Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
//                alert .setTitle("succes d'envoi");
//                alert.setContentText("message envoyé");
//                alert.showAndWait();}
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
//            alert.setTitle("Echec d'envoi");
//            alert.setContentText("Envoi echoue");
//            alert.showAndWait();
//        }
//    }
//
//    public void SignalerMessage(ActionEvent actionEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignalerMessage.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("Signaler Message");
//        stage.show();
//    }
//
////    public void loadDiscussions(){
////        disc.setText("loadDiscussions");
////    }
//
//
////
//@FXML
//public int AfficherDis(ActionEvent event) {
//
//    VBox vboxClicked = (VBox) event.getSource();
//    Label labelIdDiscussion = (Label) vboxClicked.getChildren().get(0);
//    String idText = labelIdDiscussion.getText();
//
//    try {
//        int discussionId = Integer.parseInt(idText); // Convertir l'ID de la discussion en entier
//        System.out.println("ID de la discussion sélectionnée : " + discussionId);
//
//        // Charger les messages associés à cette discussion
//        List<Message> messages = serviceMessage.afficheridDis(discussionId);
//
//        // Effacer les messages précédemment affichés
//        Msg.getChildren().clear();
//
//        // Afficher les nouveaux messages
//        for (Message message : messages) {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
//            VBox msgBox = fxmlLoader.load();
//            MessageController msgController = fxmlLoader.getController();
//            msgController.setData(message);
//            Msg.getChildren().add(msgBox);
//        }
//    } catch (NumberFormatException | SQLException | IOException e) {
//        System.err.println("Erreur lors de la récupération des messages de la discussion : " + e.getMessage());
//        e.printStackTrace();
//    }
//
//    try {
//        int discussionId = 3; // Remplacez ceci par l'ID de la discussion que vous souhaitez afficher
//
//        DiscussionController dc = new DiscussionController();
//        discussionId = AfficherDis(event);
//        System.out.println("ID de la discussion sélectionnée : " + discussionId);
//
//        List<Message> messages = serviceMessage.afficheridDis(discussionId);
//
//        for (Message message : messages) {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
//            VBox msgBox = fxmlLoader.load();
//            MessageController msgController = fxmlLoader.getController();
//            msgController.setData(message);
//            Msg.getChildren().add(msgBox);
//        }
//    } catch (SQLException | IOException e) {
//        e.printStackTrace();
//    }
//    return -1 ;
//}
//
//
//    @FXML
//    void initialize() {
//
//        int discussionId = 0;
//
//        try {
//            ObservableList<Disscussion> observableList = FXCollections.observableList(serviceDisscussion.afficher());
//            for (int i = 0; i < observableList.size(); i++) {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/discussion.fxml"));
//                HBox cardBox = fxmlLoader.load();
//                DiscussionController discController = fxmlLoader.getController();
//                discController.setData(observableList.get(i));
//                dis.getChildren().add(cardBox);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
////        try{
////            ObservableList<Message> messages = FXCollections.observableList(serviceMessage.afficher());
////            for (int i = 0; i < messages.size(); i++) {
////                FXMLLoader fxmlLoader= new FXMLLoader();
////                fxmlLoader.setLocation(getClass().getResource("/message.fxml"));
////
////                VBox msgBox = fxmlLoader.load();
////                MessageController MsgController = fxmlLoader.getController();
////
////                MsgController.setData(messages.get(i));
////                Msg.getChildren().add(msgBox);
////
////            }
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//
////  Afficher tous les discussions
////        try {
//////            int discussionId = 3; // Remplacez ceci par l'ID de la discussion que vous souhaitez afficher
////
////            DiscussionController dc = new DiscussionController() ;
////            discussionId = AfficherDis(new ActionEvent());
////            System.out.println("ID de la discussion sélectionnée : " + discussionId);
////
////            List<Message> messages = serviceMessage.afficheridDis(discussionId);
////
////            for (Message message : messages) {
////                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
////                VBox msgBox = fxmlLoader.load();
////                MessageController msgController = fxmlLoader.getController();
////                msgController.setData(message);
////                Msg.getChildren().add(msgBox);
////            }
////        } catch (SQLException | IOException e) {
////            e.printStackTrace();
////        }
//    }
//
//
////    public void onDisClicked (Disscussion diss){
////        idDis = diss.getIdDis() ;
////    }
//
//
//
//
////    @Override
////    public void onDisClicked(Disscussion disscussion) {
////        idDis = disscussion.getIdDis() ;
////    }
//
//    }
//
//
//
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

public class Envoi_MessageController implements Initializable {


    @FXML
    private TextField msg;

    @FXML
    private TextField username;

    @FXML
    private Button addDis;

    @FXML
    private Button signal;

    @FXML
    private Button notification;

    @FXML
    private VBox dis;

    @FXML
    private VBox Msg;

    @FXML
    private Button Envoi;




    private Disscussion discuss;


    private  ServiceMessage serviceMessage = new ServiceMessage();
    private  ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    private  ServiceUser serviceUser = new ServiceUser();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherDiscussions();
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

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setData(Disscussion disscussion) {
        this.discuss = disscussion;
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
    void AjouterDisc(ActionEvent event) throws SQLException {
        Disscussion d = new Disscussion( 1 , chercheruser(event).getId_User()) ;
//        System.out.println((serviceDisscussion.getDiscussionByReceiverId(chercheruser(event).getId_User()).getIdDis()));
//        System.out.println(chercheruser(event).getId_User());
//        System.out.println(d);


        if(serviceDisscussion.Compare(d)){
//            System.out.println(serviceDisscussion.Compare(d));
            showAlert(Alert.AlertType.INFORMATION, "Déjà disponible", "Discussion déjà existante");
            }
        else { showAlert(Alert.AlertType.CONFIRMATION, "Discussion Créee", "Discussion Créee avec : " +chercheruser(event).getUsername());
            serviceDisscussion.ajouter(d);
        }


//        System.out.println(serviceDisscussion.getDiscussionByReceiverId(chercheruser(event).getId_User())); ;

    }


    @FXML
    User chercheruser(Event event) throws SQLException {
        String userName = username.getText().trim() ;
        receriver = serviceUser.getbyUsername(userName) ;

        if(receriver==null){showAlert(Alert.AlertType.ERROR, "Erreur", "Utilisateur non trouvé " );}
        else {System.out.println(receriver.getUsername());}

        return receriver ;
    }
}