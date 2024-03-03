package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import services.ServiceMessage;

public class EmojipickerController {

    private Label messageLabel;
    ServiceMessage serviceMessage = new ServiceMessage() ;

    public void initData(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    @FXML
    void insertSmile() {
        messageLabel.setText(messageLabel.getText() + "😊");
        System.out.println("😊");

    }

    @FXML
    void insertHeart() {
        messageLabel.setText(messageLabel.getText() + "❤️");
        System.out.println("❤️");
    }

    @FXML
    void insertColors() {
        messageLabel.setText(messageLabel.getText() + "🎨");
        System.out.println("🎨");
    }

    @FXML
    void insertSparkle() {
        messageLabel.setText(messageLabel.getText() + "✨");
        System.out.println("✨");
    }

    @FXML
    void insertHand() {
        messageLabel.setText(messageLabel.getText() + "🤞");
        System.out.println("🤞");
    }

    @FXML
    void insertboss() {
        messageLabel.setText(messageLabel.getText() + "😎");
        System.out.println("😎");
    }

}
