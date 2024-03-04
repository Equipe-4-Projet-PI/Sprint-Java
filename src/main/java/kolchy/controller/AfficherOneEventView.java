package kolchy.controller;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kolchy.MainFX;
import kolchy.entities.Event;
import kolchy.service.EventChangeListener;
import kolchy.service.ServiceEvent;
import kolchy.service.ServiceWorkshop;
import kolchy.utils.QRcode;

import java.io.File;
import java.io.IOException;

public class AfficherOneEventView {

    @FXML
    private ImageView img;

    @FXML
    private Label lnom;

    @FXML
    private Label lplace;
    @FXML
    private Label lprix;

    @FXML
    private Label ldate;
    @FXML
    private ImageView imgqr;
    String username;
    Event e;
    ServiceEvent se=new ServiceEvent();
    ServiceWorkshop sw=new ServiceWorkshop();
    private EventChangeListener eventChangeListener;


    public void setEventChangeListener(EventChangeListener eventChangeListener) {
        this.eventChangeListener = eventChangeListener;
    }

    public void remplireData(Event e){



        this.e=e;
        lnom.setText(e.getE_Name());
        lplace.setText(e.getPlace());
        lprix.setText(String.valueOf(e.getTicket_Price()));
        ldate.setText(String.valueOf(e.getE_Date()));

        File file=new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\java\\kolchy\\image\\"+e.getImage());
        System.out.println("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\java\\kolchy\\image\\"+e.getImage());
        Image image=new Image(file.toURI().toString());

        img.setImage(image);
        QRcode.generateQrCode(e.toString(),e.getId_Event());
        File file2=new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\java\\qrcode\\Event_"+e.getId_Event()+".png");
        Image image2=new Image(file2.toURI().toString());


        imgqr.setImage(image2);

    }

    @FXML
    void modifierEventcarte(ActionEvent event) {
        if(eventChangeListener!=null){
            eventChangeListener.onModifierClicked(e);
        }
    }

    @FXML
    void supprimerEventcarte(ActionEvent event) {
        se.supprimer(e);

        if(eventChangeListener!=null){
            eventChangeListener.onSupprimerClicked();
        }

    }
   /* @FXML
    void gotoModify(ActionEvent event) {
        Stage stage=(Stage) img.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("ajouter-event-view.fxml"));


            Parent root = fxmlLoader.load();
            AfficherEventView controller=fxmlLoader.getController();
            controller.initEventId(e.getId_Event());
            Stage newStage=new Stage();
            newStage.setTitle("Hello!");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }*/
    @FXML
    void gotoWorkshop(ActionEvent event) {
        Stage stage=(Stage) img.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("ajouter-workshop-view.fxml"));


            Parent root = fxmlLoader.load();
            AjouterWorkshopView controller=fxmlLoader.getController();
            controller.initEventId(e.getId_Event());
            Stage newStage=new Stage();
            newStage.setTitle("Hello!");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }
    @FXML
    void gotoWorkshop2(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kolchy/afficher-workshop-view.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = lnom.getScene();
        scene.setRoot(loginSuccessRoot);
        AfficherWorkshopView afficherWorkshopView = loader.getController();
        afficherWorkshopView.initEventId(e.getId_Event(),username);









//        Stage stage=(Stage) lnom.getScene().getWindow();
//        stage.close();


//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("afficher-workshop-view.fxml"));
//
//            Parent root = fxmlLoader.load();
//            AfficherWorkshopView controller=fxmlLoader.getController();
//            controller.initEventId(e.getId_Event());
//            Stage newStage=new Stage();
//            newStage.setTitle("workshops");
//            newStage.setScene(new Scene(root));
//            newStage.show();
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }


    }
  /*  @FXML
    void gotoWorkshop2(ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("afficher-workshop-view.fxml"));
            lnom.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
}
