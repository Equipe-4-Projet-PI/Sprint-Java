package kolchy.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import kolchy.MainFX;
import kolchy.entities.Event;
import kolchy.service.EventChangeListener;
import kolchy.service.ServiceEvent;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class AfficherEventView implements EventChangeListener<Event> {

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
    private TextField tfrecherche;


    @FXML
    public void initialize(){

        recherche_avance();
    }
    public void refresh(List<Event> events){
        grid.getChildren().clear();
        int column=0;
        int row=1;
        for(int i=0;i<events.size();i++){

            FXMLLoader card = new FXMLLoader(MainFX.class.getResource("afficher-one-event-view-2.fxml"));
            try {
                AnchorPane anchorPane=card.load();
                AfficherOneEventView item=card.getController();
                item.remplireData(events.get(i));
                item.setEventChangeListener(this);
                if(column==3){
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
        refresh(serviceEvent.afficher());
    }
    @FXML
    void uploadImage(ActionEvent event) {

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
            refresh(serviceEvent.afficher());
        }

    }

    @Override
    public void onSupprimerClicked() {
        refresh(serviceEvent.afficher());
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
    public void recherche_avance(){
        refresh(serviceEvent.afficher());
        ObservableList<Event> events= FXCollections.observableArrayList(serviceEvent.afficher());
        FilteredList<Event> filteredList=new FilteredList<>(events,e->true);
        tfrecherche.textProperty().addListener(((observableValue, oldValue, newValue) ->{
            filteredList.setPredicate(
                    e->{
                        if(newValue==null || newValue.isEmpty()){
                            return true;
                        }
                        if(e.getPlace().toLowerCase().contains(newValue.toLowerCase())){
                            return true;
                        } else if (e.getE_Name().toLowerCase().contains(newValue.toLowerCase())) {
                            return true;
                        }else if (String.valueOf(e.getTicket_Price()).contains(newValue.toLowerCase())) {
                            return true;
                        }
                        else if (String.valueOf(e.getE_Date()).contains(newValue.toLowerCase())) {
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
            );
            refresh(filteredList);
        } ));
    }

}
