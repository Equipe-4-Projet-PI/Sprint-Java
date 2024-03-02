package controllers;

import entities.Disscussion;
import entities.Message;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceMessage;
import services.ServiceUser;
import services.ServiceDisscussion;


import java.io.File;
import java.io.IOException;
import java.security.cert.PolicyNode;
import java.sql.SQLException;
import java.util.List;
//import com.github.michaelederau.emojipicker.EmojiPicker;



public class DiscussionController {


    @FXML
    public Button Afficherdis;

    @FXML
    private VBox vbox;

    @FXML
    private HBox box;
    @FXML
    private Label content;

    @FXML
    private ImageView img;

    @FXML
    private Label receiver;

    private Disscussion dis;
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    ServiceUser serviceUser = new ServiceUser();
    ServiceMessage serviceMessage = new ServiceMessage();

    public DiscussionController() {
    }

    public void setData(Disscussion disscussion) throws SQLException {
        User user = new User() ;
//        Image image = new Image("bell.png");
//        img.setImage(image);
        File file = new File("C:\\Users\\Lenovo\\Desktop\\ArtyVenci\\src\\main\\resources\\images\\pers1.png"  ) ;
        Image image = new Image(file.toURI().toString());
        img.setImage(image);


        receiver.setText(serviceUser.getbyid(disscussion.getIdReceiver()).getUsername());
        content.setText(serviceMessage.getContentById(disscussion.getIdDis()).getContent());
        dis = disscussion;
    }

    @FXML
    public void AfficherDis(ActionEvent event) {
//        Button boutonClique = (Button) event.getSource();
//        HBox parentHBox = (HBox) boutonClique.getParent();
//        VBox vb = (VBox) parentHBox.getChildren().get(1);
//        Label labelIdDiscussion = (Label) vb.getChildren().get(0);
//        int discussionId = 0;
//        try {
//            discussionId = Integer.parseInt(String.valueOf(serviceUser.getbyUsername(labelIdDiscussion.getText()).getId_User()));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(discussionId);
//
//        try {
//            discussionId = serviceDisscussion.afficher().get(1).getIdDis();
//            System.out.println("ID de la discussion sélectionnée : " + discussionId);
//            return discussionId;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        try{
            Parent root = loadRootMessages();
            content.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AddForum(ActionEvent event) {

    }


    public Parent loadRootMessages() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Envoi_Message.fxml"));
        RefreshController controller = new RefreshController();
        loader.setController(controller);
        Parent root = loader.load();
        RefreshController initedCont = loader.getController();
        initedCont.setMsgData(serviceDisscussion.getDiscussionById(serviceMessage.getContentById(dis.getIdDis()).getIdMsg()),content.getScene()); // Add data to the controller

//        AnchorPane box = loader.load();
//        Envoi_MessageController controller = loader.getController();
//        controller.setData(dis);
        return root;

    }
}
//    @FXML
//    void initialize() {
//    try {
//            int discussionId = 3; // Remplacez ceci par l'ID de la discussion que vous souhaitez afficher
//
//            DiscussionController dc = new DiscussionController() ;
//            discussionId = AfficherDis(new ActionEvent());
//            System.out.println("ID de la discussion sélectionnée : " + discussionId);
//
//            List<Message> messages = serviceMessage.afficheridDis(discussionId);
//
//            for (Message message : messages) {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
//                VBox msgBox = fxmlLoader.load();
//                MessageController msgController = fxmlLoader.getController();
//                msgController.setData(message);
//                Msg.getChildren().add(msgBox);
//            }
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//    }}


//    public int AfficherDis(ActionEvent event) {
//        if (event.getSource() instanceof Button) {
//            Button boutonClique = (Button) event.getSource();
//            HBox parentHBox = (HBox) boutonClique.getParent();
//            VBox vb = (VBox) parentHBox.getChildren().get(1);
//            Label labelIdDiscussion = (Label) vb.getChildren().get(0);
//
//            String idText = labelIdDiscussion.getText();
//            try {
//                int discussionId = serviceUser.getbyUsername(idText).getId_User();
////                System.out.println("ID de la discussion sélectionnée : " + discussionId);
//                return discussionId;
//            } catch (NumberFormatException | SQLException e) {
//                System.err.println("Invalid discussion ID: " + idText);
//                e.printStackTrace();
//                return -1;
//            }
//        } else {
//            System.err.println("Event source is not a Button");
//            return -1;
//        }
//    }






