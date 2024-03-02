package kolchy.controller;
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
        ldate.setText(e.getE_Date().getDate()+"/"+e.getE_Date().getMonth()+1+"/"+e.getE_Date().getYear());
        File file=new File("C:\\Users\\ASUS\\Desktop\\PI_dev_VER2\\src\\main\\java\\kolchy\\image\\"+e.getImage());
        Image image=new Image(file.toURI().toString());
        img.setImage(image);

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
    void gotoWorkshop2(ActionEvent event) {
        Stage stage=(Stage) lnom.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("afficher-workshop-view.fxml"));


            Parent root = fxmlLoader.load();
            AfficherWorkshopView controller=fxmlLoader.getController();
            controller.initEventId(e.getId_Event());
            Stage newStage=new Stage();
            newStage.setTitle("workshops");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


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
