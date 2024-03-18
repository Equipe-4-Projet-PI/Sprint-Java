package controllers;

import entities.Disscussion;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

public class HomepageController implements Initializable {

    @FXML
    public Button chercher;

    @FXML
    public VBox dis;

    @FXML
    public ScrollPane discussions;

    @FXML
    public VBox discussionstrouvees;

    @FXML
    public ScrollPane discussionstrouves;

    @FXML
    public Button notification;

    @FXML
    public TextField username;

    // Déclarations des services
    private ServiceMessage serviceMessage = new ServiceMessage();
    private ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    private ServiceUser serviceUser = new ServiceUser();

    // Déclarations des autres variables
    private User receiver = new User();

    public HomepageController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherDiscussions();
        discussionstrouvees = new VBox();
    }

    // Afficher les discussions
    public void afficherDiscussions() {
        try {
            ObservableList<Disscussion> discussions = FXCollections.observableList(serviceDisscussion.afficher());
            for (Disscussion discussion : discussions) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/discussion.fxml"));
                HBox cardBox = fxmlLoader.load();
                DiscussionController discController = fxmlLoader.getController();
                discController.setData(discussion);
                dis.getChildren().add(cardBox);
                // Ajoutez un gestionnaire d'événements pour chaque HBox
                cardBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            // Chargez la page messages.fxml avec les données de discussion appropriées
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages.fxml"));
                            Parent root = loader.load();
                            MessagesController messagesController = loader.getController();
                            messagesController.setData(discussion); // Définissez les données de discussion dans MessagesController
                            messagesController.afficherMessages();
                            // Affichez la nouvelle page
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les discussions: " + e.getMessage());
        }
    }

    // Autres méthodes comme afficherDiscussionstrouvees, chercherDiscussion, showAlert, etc.

    @FXML
    public void afficherDiscussionstrouvees() {
        discussionstrouvees.getChildren().clear();
        try {
//            ObservableList<Disscussion> discussions = FXCollections.observableList(serviceDisscussion.afficher());
            ObservableList<Disscussion> discussions = FXCollections.observableList(chercherDiscussion());
            for (Disscussion discussion : discussions) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/discussion.fxml"));
                HBox cardBox = fxmlLoader.load();
                DiscussionController discController = fxmlLoader.getController();
                discController.setData(discussion);
                discussionstrouvees.getChildren().add(cardBox);
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les discussions: " + e.getMessage());
        }
    }

        // el recherche jawha fesfes
        @FXML
        public List<Disscussion> chercherDiscussion () throws SQLException {
            List<Disscussion> disTrouvees = new ArrayList<>();
            String userName = username.getText().trim();
            if (!userName.isEmpty()) {
                receiver = serviceUser.getbyUsername(userName);
                if (receiver != null) {
                    try {
                        Disscussion d = serviceDisscussion.getDiscussionByReceiverId(receiver.getId_User());
                        if (d != null) {
                            showAlert(Alert.AlertType.INFORMATION, "Discussion trouvée", "Discussion trouvée avec : " + userName);
                            disTrouvees.add(d);
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
            return null;
        }

        //Afficher les alertes
        private void showAlert (Alert.AlertType alertType, String title, String content){
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }
