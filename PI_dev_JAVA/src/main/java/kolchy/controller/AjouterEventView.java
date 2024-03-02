package kolchy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import kolchy.MainFX;
import kolchy.entities.Event;
import kolchy.service.EventChangeListener;
import kolchy.service.ServiceEvent;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class AjouterEventView implements EventChangeListener<Event> {

    @FXML
    private AnchorPane anchore;

    @FXML
    private GridPane grid;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfplace;
    @FXML
    private DatePicker dpdate;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tfimage;
    ServiceEvent serviceEvent=new ServiceEvent();
    int idModifier=0;

    @FXML
    public void initialize(){
        refresh();
    }
    public void refresh(){
        grid.getChildren().clear();
        List<Event> events=serviceEvent.afficher();
        int column=0;
        int row=1;
        for(int i=0;i<events.size();i++){

            FXMLLoader card = new FXMLLoader(MainFX.class.getResource("afficher-one-event-view.fxml"));
            try {
                AnchorPane anchorPane=card.load();
                AfficherOneEventView item=card.getController();
                item.remplireData(events.get(i));
                item.setEventChangeListener(this);
                if(column==1){
                    column=0;
                    row++;
                }
                grid.add(anchorPane,column++,row);

                GridPane.setMargin(anchorPane,new Insets(10));
            }catch (IOException e){
                System.out.println("Erreur:"+e.getMessage());
            }



        }
    }
    @FXML
    void ajouterEvenement(ActionEvent event) {
        Event e=new Event();
        e.setE_Name(tfnom.getText());
        e.setPlace(tfplace.getText());
        e.setImage(tfimage.getText());
        e.setTicket_Price(Double.parseDouble(tfprix.getText()));
        e.setE_Date(Date.valueOf(dpdate.getValue()));
        serviceEvent.ajouter(e);
        refresh();
    }
    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(tfimage.getScene().getWindow());
        if(file!=null){
            tfimage.setText(file.getName());
        }
    }
    @FXML
    void modifierEvent(ActionEvent event) {
        if(idModifier!=0){
            Event e=new Event();
            e.setId_Event(idModifier);
            e.setE_Name(tfnom.getText());
            e.setPlace(tfplace.getText());
            e.setImage(tfimage.getText());
            e.setTicket_Price(Double.parseDouble(tfprix.getText()));
            e.setE_Date(Date.valueOf(dpdate.getValue()));
            serviceEvent.modifier(e);
            refresh();
        }

    }

    @Override
    public void onSupprimerClicked() {
        refresh();
    }

    @Override
    public void onModifierClicked(Event e) {
        idModifier=e.getId_Event();
        tfnom.setText(e.getE_Name());
        tfimage.setText(e.getImage());
        tfplace.setText(e.getPlace());
        tfprix.setText(e.getTicket_Price()+"");

        LocalDate localDate=e.getE_Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpdate.setValue(localDate);
    }
}
