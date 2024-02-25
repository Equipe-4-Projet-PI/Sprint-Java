package controllers;

import entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceMessage;

import java.io.IOException;
import java.sql.SQLException;

public class EnvoiMessageController {

    //temporairement
    private  int idSender = 1 , idReceiver =1 ;

    ServiceMessage serviceMessage = new ServiceMessage() ;
    @FXML
    private Button Envoi;

    @FXML
    private TextField msg;

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        try {
            serviceMessage.ajouter(new Message(idSender ,msg.getText() ,null));
            Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
            alert.setTitle("succes d'envoi");
            alert.setContentText("message envoyé");
            alert.showAndWait();
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

    public void Creergroupe(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterGroupe.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Créer un groupe");
        stage.show();
    }
}
