package kolchy.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kolchy.entities.Workshop;
import kolchy.service.EventChangeListener;
import kolchy.service.ServiceWorkshop;

import java.io.File;

public class AfficherOneWorkshopView {

    @FXML
    private ImageView img;

    @FXML
    private Label lnom;

    @FXML
    private Label lplace;
    Workshop w;
    private EventChangeListener eventChangeListener;
    ServiceWorkshop sw=new ServiceWorkshop();

    public void setEventChangeListener(EventChangeListener eventChangeListener) {
        this.eventChangeListener = eventChangeListener;
    }
    public void remplireData(Workshop w){
        this.w=w;
        lnom.setText(w.getTitle());
        lplace.setText(w.getDetails());
        File file=new File("C:\\Users\\ASUS\\Desktop\\PI_dev_VER2\\src\\main\\java\\kolchy\\image\\"+w.getImage());
        Image image=new Image(file.toURI().toString());
        img.setImage(image);
    }

    @FXML
    void modifierEvent(ActionEvent event) {
        if(eventChangeListener!=null){
            eventChangeListener.onModifierClicked(w);
        }

    }

    @FXML
    void supprimerEvent(ActionEvent event) {
        sw.supprimer(w);
        if(eventChangeListener!=null){
            eventChangeListener.onSupprimerClicked();
        }
    }

}

