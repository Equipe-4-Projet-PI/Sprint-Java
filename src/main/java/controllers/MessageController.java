package controllers;

import entities.Disscussion;
import entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;

public class MessageController {


    @FXML
    private Label messageLabel;

    @FXML
    private Button emojiButton;

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

    public void setData(Message message) throws SQLException {

        String content = serviceMessage.getContentById(message.getIdMsg()).getContent();
        String heure = serviceMessage.getContentById(message.getIdMsg()).getTime().toString() ;
        String reaction = serviceMessage.getContentById(message.getIdMsg()).getReaction();

//        System.out.println(content);
//        System.out.println(heure);

//        time.setText(heure);
        messagee.setText(content);
    }

    @FXML
    void openEmojiPicker(ActionEvent event) {
        try {
            // Charger la fenêtre de sélection d'emoji à partir du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Emojipicker.fxml"));
            Parent root = loader.load();
            EmojipickerController controller = loader.getController();

            // Créer une nouvelle scène pour la fenêtre de sélection d'emoji
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Sélectionner un Emoji");

            // Passer le contrôleur principal et l'étiquette de message à la fenêtre de sélection d'emoji
            controller.initData(curr_message);

            // Afficher la fenêtre de sélection d'emoji
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
