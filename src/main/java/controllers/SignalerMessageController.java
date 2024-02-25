package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class SignalerMessageController {

    @FXML
    private RadioButton Autre;

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

    @FXML
    void Signaler(ActionEvent event) {
        RadioButton selectedRadioButton = null;

        // Vérifie quel bouton radio est sélectionné
        if (Autre.isSelected()) {
            selectedRadioButton = Autre;
        } else if (Contenu_inapproprie.isSelected()) {
            selectedRadioButton = Contenu_inapproprie;
        } else if (Contenu_offensant.isSelected()) {
            selectedRadioButton = Contenu_offensant;
        } else if (Faux_contenu.isSelected()) {
            selectedRadioButton = Faux_contenu;
        } else if (harcelement.isSelected()) {
            selectedRadioButton = harcelement;
        } else if (spam.isSelected()) {
            selectedRadioButton = spam;
        }

        if (selectedRadioButton == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setContentText("Veuillez sélectionner une raison pour signaler le message.");
            alert.showAndWait();
        } else {
            String reason = selectedRadioButton.getText();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation de signalement");
            alert.setHeaderText(null);
            alert.setContentText("Vous avez signalé le message pour la raison suivante : " + reason);
            alert.showAndWait();
        }
    }
}
