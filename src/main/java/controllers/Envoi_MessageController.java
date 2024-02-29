package controllers;

import entities.Disscussion;
import entities.Message;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Envoi_MessageController  {

    ServiceMessage serviceMessage = new ServiceMessage() ;
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion() ;
    ServiceUser serviceUser = new ServiceUser() ;
    User sender = new User() ;
    //List<Disscussion> dis= serviceDisscussion.afficher();
    int idSender = sender.getId_User();

    @FXML
    private Text disc;
    @FXML
    private ScrollPane discussion;

    @FXML
    private VBox dis;
    private List<Disscussion> disscussions ;

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

//    public void loadDiscussions(){
//        disc.setText("loadDiscussions");
//    }


    @FXML
    void initialize()
    {
        try{
            ObservableList<Disscussion> observableList = FXCollections.observableList(serviceDisscussion.afficher());
            for (int i = 0; i < observableList.size(); i++) {
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/discussion.fxml"));
                HBox cardBox = fxmlLoader.load();
                DiscussionController discController = fxmlLoader.getController();
                discController.setData(observableList.get(i));
                dis.getChildren().add(cardBox);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
