package controllers;

import entities.Disscussion;
import entities.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceDisscussion;
import services.ServiceMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Envoi_MessageController {

    private  int idSender = 1 ;

    ServiceMessage serviceMessage = new ServiceMessage() ;
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion() ;

    @FXML
    private Button Envoi;

    @FXML
    private TextField msg;

    @FXML
    private Button nonlu;

    @FXML
    private Button signal;




    @FXML
    void ajouter(ActionEvent event) throws IOException {
        try {
            if (msg.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
                alert .setTitle("Messsage vide !!");
                alert.setContentText("Contenu de message manquant vous ne pouver pas envoyer un message vide");
                alert.showAndWait();
            }
            else
            {
                serviceMessage.ajouter(new Message(idSender ,msg.getText() ,null));
                Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
                alert .setTitle("succes d'envoi");
                alert.setContentText("message envoyé");
                alert.showAndWait();}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
            alert.setTitle("Echec d'envoi");
            alert.setContentText("Envoi echoue");
            alert.showAndWait();
        }
    }

    public void SignalerMessage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignalerMessage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Signaler Message");
        stage.show();
    }

}
