package controllers;

import entities.Disscussion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import services.ServiceDisscussion;

import java.io.IOException;
import java.sql.SQLException;

public class SignalerMessageController {

    @FXML
    private RadioButton Contenu_inapproprie;

    @FXML
    private RadioButton Contenu_offensant;

    @FXML
    private RadioButton Faux_contenu;

    @FXML
    private RadioButton harcelement;

    @FXML
    private Button signaler;

    @FXML
    private RadioButton spam;

    //autres variables
    public Disscussion discussion;
    String raison ;


    public void setData(Disscussion discussion) {
        this.discussion = discussion;
    }

    @FXML
    void Signaler() throws IOException, SQLException {

        if (discussion != null) {
            int discussionId = discussion.getIdDis(); // Supposons que getId() renvoie l'ID de la discussion


            RadioButton selectedRadioButton = null;
            int compteur = 0;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Avertissement");

            // Vérifie quel bouton radio est sélectionné
            if (Contenu_inapproprie.isSelected()) {
                selectedRadioButton = Contenu_inapproprie;
                raison = "Contenu_inapproprie";
                compteur = compteur+1;
            } else if (Contenu_offensant.isSelected()) {
                selectedRadioButton = Contenu_offensant;
                raison = "Contenu_offensant";
                compteur = compteur+1;
            } else if (Faux_contenu.isSelected()) {
                selectedRadioButton = Faux_contenu;
                raison = "Faux_contenu";
                compteur = compteur+1;
            } else if (harcelement.isSelected()) {
                selectedRadioButton = harcelement;
                raison = "harcelement";
                compteur = compteur+1;
            } else if (spam.isSelected()) {
                selectedRadioButton = spam;
                raison = "spam";
                compteur = compteur+1;
            }


            ServiceDisscussion sd = new ServiceDisscussion();
            sd.signal(sd.getDiscussionById(discussionId), raison);
            if (selectedRadioButton == null) {
                // Aucun bouton radio sélectionné
                alert.setContentText("Veuillez sélectionner une raison pour signaler le message.");
            } else {
                if (compteur == 1){
                // Bouton radio sélectionné, affichage de l'alerte avec la raison
                alert.setContentText("Une information sera envoyée à l'administrateur pour la raison suivante :\n"
                        + selectedRadioButton.getText());}
                else if(compteur>1)
                    alert.setContentText("Veuillez sélectionner une seule raison pour signaler le message.");

            }

            alert.showAndWait();
        }
        else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Discussion non sélectionnée");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
