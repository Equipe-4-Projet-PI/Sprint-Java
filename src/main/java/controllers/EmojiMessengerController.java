package controllers ;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class EmojiMessengerController {

    @FXML
    private Label messageLabel;

    @FXML
    private Button emojiButton;

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
            controller.initData(messageLabel);

            // Afficher la fenêtre de sélection d'emoji
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
