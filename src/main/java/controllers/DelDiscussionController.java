package controllers;


import entities.Disscussion;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

public class DelDiscussionController {

    @FXML
    private Button Supprimer;

    @FXML
    private Button chercher;

    @FXML
    private VBox discussionstrouvees;

    @FXML
    private ScrollPane discussionstrouves;

    @FXML
    private TextField username;

    private ServiceMessage serviceMessage = new ServiceMessage();
    private ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    private ServiceUser serviceUser = new ServiceUser();

    // Déclarations des autres variables
    private User receiver = new User();

    public void afficherDiscussionstrouvees() {
        try {
            ObservableList<Disscussion> discussions = FXCollections.observableList(chercherDiscussion());
            if (chercherDiscussion() ==null)  showAlert(Alert.AlertType.INFORMATION, "Discussion non trouvée", "Discussion non trouvée " );
            for (Disscussion discussion : discussions) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/discussion.fxml"));
                HBox cardBox = fxmlLoader.load();
                DiscussionController discController = fxmlLoader.getController();
                discController.setData(discussion);
                discussionstrouvees.getChildren().add(cardBox);
                // Ajoutez un gestionnaire d'événements pour chaque HBox
                cardBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                    }
                });
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les discussions: " + e.getMessage());
        }
    }

    @FXML
    public List<Disscussion> chercherDiscussion () throws SQLException {
        discussionstrouvees.getChildren().clear();
        List<Disscussion> disTrouvees = new ArrayList<>();
        String userName = username.getText().trim();
        if (!userName.isEmpty()) {
            receiver = serviceUser.getbyUsername(userName);
            if (receiver != null) {
                try {
                    Disscussion d = serviceDisscussion.getDiscussionByReceiverId(receiver.getId_User());
                    if (d != null) {
                        disTrouvees.add(d);
//                        System.out.println(disTrouvees);
                        return disTrouvees;
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Discussion non trouvée");
                    }
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue: " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Utilisateur non trouvé");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir le username");
        }
        System.out.println(disTrouvees);
        return disTrouvees;
    }

    private void showAlert (Alert.AlertType alertType, String title, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void SupprimerDiscussion() {
    }

}
