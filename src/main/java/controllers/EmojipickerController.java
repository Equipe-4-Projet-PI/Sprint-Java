package controllers;

import entities.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import services.ServiceMessage;

public class EmojipickerController {

//    private Label messageLabel;
    ServiceMessage serviceMessage = new ServiceMessage() ;
    String reaction ;

    public void initData(Message message) {
//        this.messageLabel = messageLabel;
        message.setReaction(reaction);
    }

    @FXML
    void insertSmile() {
//        messageLabel.setText(messageLabel.getText() + "😊");
        System.out.println("😊");
        reaction = "smile" ;

    }

    @FXML
    void insertHeart() {
//        messageLabel.setText(messageLabel.getText() + "❤️");
        System.out.println("❤️");
        reaction = "heart" ;

    }

    @FXML
    void insertColors() {
//        messageLabel.setText(messageLabel.getText() + "🎨");
        System.out.println("🎨");
        reaction = "artistic" ;

    }

    @FXML
    void insertSparkle() {
//        messageLabel.setText(messageLabel.getText() + "✨");
        System.out.println("✨");
        reaction = "brilliant" ;

    }

    @FXML
    void insertHand() {
//        messageLabel.setText(messageLabel.getText() + "🤞");
        System.out.println("🤞");
        reaction = "hand heart" ;

    }

    @FXML
    void insertboss() {
//        messageLabel.setText(messageLabel.getText() + "😎");
        System.out.println("😎");
        reaction = "boss" ;
    }

}
