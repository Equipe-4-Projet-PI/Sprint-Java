package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
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

    String raison ;


    @FXML
    void Signaler(ActionEvent event) throws IOException, SQLException {
        RadioButton selectedRadioButton = null;



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Avertissement");

        // Vérifie quel bouton radio est sélectionné
        if (Contenu_inapproprie.isSelected()) {
            selectedRadioButton = Contenu_inapproprie;
            raison = "Contenu_inapproprie";
        } else if (Contenu_offensant.isSelected()) {
            selectedRadioButton = Contenu_offensant;
            raison = "Contenu_offensant";
        } else if (Faux_contenu.isSelected()) {
            selectedRadioButton = Faux_contenu;
            raison = "Faux_contenu";
        } else if (harcelement.isSelected()) {
            selectedRadioButton = harcelement;
            raison = "harcelement";
        } else if (spam.isSelected()) {
            selectedRadioButton = spam;
            raison = "spam";
        }


        ServiceDisscussion sd = new ServiceDisscussion() ;
        sd.signal(sd.getDiscussionById(5) , raison);
        if (selectedRadioButton == null) {
            // Aucun bouton radio sélectionné
            alert.setContentText("Veuillez sélectionner une raison pour signaler le message.");
        } else {
            // Bouton radio sélectionné, affichage de l'alerte avec la raison
            alert.setContentText("Une information sera envoyée à l'administrateur pour la raison suivante :\n"
                    + selectedRadioButton.getText());
        }

        alert.showAndWait();

    }


}
