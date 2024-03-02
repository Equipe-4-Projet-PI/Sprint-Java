package controllers;

import com.sun.nio.sctp.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.ServiceDisscussion;
import services.ServiceUser;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
//import services.ServiceDisscussion;

import java.sql.SQLException;

public class NotificationController {

    @FXML
    private Button notification;

    ServiceUser su = new ServiceUser() ;
    ServiceDisscussion sd = new ServiceDisscussion() ;
    @FXML
    void btnNotificationOnAction(ActionEvent event) throws SQLException {
        Image image = new Image("/images/bell.png") ;
//        Notifications notifications = Notifications.create() ;
//        //notifications.graphic(new ImageView(image)) ;
//        notifications.text("Nouveau message from :" ) ;
//        notifications.title("Nouveau message ") ;
////        notifications.hideAfter(Duration.seconds(50)) ;
//        notifications.show();

//        Notifications.create().title("hi").text("hello").showWarning();

        TrayNotification tray = new TrayNotification() ;
        AnimationType type = AnimationType.POPUP ;

        tray.setAnimationType(type);
        tray.setTitle("Nouveau Message");
        tray.setMessage("Nouveau Message from  :" + su.getbyid(sd.getDiscussionById(1).getIdSender()).getUsername() );
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));




    }
}