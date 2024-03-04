package kolchy.controller;

import controllers.EncherArtistController;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kolchy.MainFX;
import kolchy.entities.Event;
import kolchy.entities.Workshop;
import kolchy.service.EventChangeListener;
import kolchy.service.ServiceEvent;
import kolchy.service.ServiceWorkshop;
import services.ServiceUser;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class AfficherWorkshopView implements EventChangeListener<Workshop> {
    Preferences preferences = Preferences.userNodeForPackage(AfficherWorkshopView.class);

    ServiceUser serviceUser = new ServiceUser();
    String savedUsername = preferences.get("username", null);
    String savedPassword = preferences.get("Password", null);
    User userlogged = serviceUser.GetUser(savedUsername,savedPassword);

    public ImageView usericon;
    public Button inscrire;
    public ImageView bell;
    public ImageView logouticon;
    public Label nav_name;
    @FXML
    private AnchorPane anchore;
    int userid;
    @FXML
    private TextField tftitre;

    @FXML
    private TextArea tadescription;

    @FXML
    private TextField tfimage;

    @FXML
    private ComboBox<String> cbevenement;
    ServiceEvent serviceEvent=new ServiceEvent();
    ServiceWorkshop sw=new ServiceWorkshop();


    @FXML
    private GridPane grid;
    String user;

    int idModifier=0;
    private int idEvent;
    public void initEventId(int idEvent,String username){
        this.idEvent=idEvent;
        user = username;
        refresh();
        user = username;
        System.out.println(username);

    }
    @FXML
    public void initialize(){
        System.out.println(user);

        refresh();
    }
    public void refresh(){

        grid.getChildren().clear();
        List<Workshop> workshops=sw.afficherParIdEvent(idEvent);
        int column=0;
        int row=1;
        for(int i=0;i<workshops.size();i++){

            FXMLLoader card = new FXMLLoader(MainFX.class.getResource("afficher-one-workshop-view-2.fxml"));
            try {
                AnchorPane anchorPane=card.load();
                AfficherOneWorkshopView item=card.getController();
                item.remplireData(workshops.get(i));
                item.setEventChangeListener(this);
                if(column==2){
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

    /* @FXML
     void ajouterWorkshop(ActionEvent event) {
         Workshop w=new Workshop();
         w.setDetails(tadescription.getText());
         w.setImage(tfimage.getText());
         w.setTitle(tftitre.getText());
         //w.setId_Workshop(cbevenement.getValue());
         /*String selectedEvent=cbevenement.getValue();
         String[] parts=selectedEvent.split(" - ");
         w.setId_Event(Integer.parseInt(parts[0]));
         System.out.println(w);*/
       /* w.setId_Event(idEvent);
        sw.ajouter(w);
        refresh();

    }*/
    @FXML
    void modifierWorkshop(ActionEvent event) {
        if(idModifier!=0){
            Workshop w=new Workshop();
            w.setId_Workshop(idModifier);
            w.setDetails(tadescription.getText());
            w.setImage(tfimage.getText());
            w.setTitle(tftitre.getText());
            //w.setId_Workshop(cbevenement.getValue());
            String selectedEvent=cbevenement.getValue();
            String[] parts=selectedEvent.split(" - ");
            w.setId_Event(Integer.parseInt(parts[0]));

            sw.modifier(w);
            refresh();
            cbevenement.setDisable(true);
        }
    }
    @FXML
    void uploadImage(ActionEvent event) {

    }

    @Override
    public void onSupprimerClicked() {
        refresh();
    }

    @Override
    public void onModifierClicked(Workshop workshop) {
        cbevenement.setDisable(false);
        idModifier=workshop.getId_Workshop();
        tfimage.setText(workshop.getImage());
        tadescription.setText(workshop.getDetails());
        tftitre.setText(workshop.getTitle());


    }
   @FXML
    void gotoEvent(ActionEvent event) throws IOException {

       FXMLLoader loader = new FXMLLoader(getClass().getResource("/kolchy/afficher-event-view.fxml"));
       Parent loginSuccessRoot = loader.load();
       Scene scene = nav_name.getScene();
       scene.setRoot(loginSuccessRoot);
       AfficherEventView afficherEventView = loader.getController();
       afficherEventView.initData(userlogged);
    }

    public void sinscrire(ActionEvent actionEvent) {
    }

    public void Logout(MouseEvent mouseEvent) {
    }

    public void ProfileVisit(MouseEvent mouseEvent) {
    }

    public void Go_To_Event(ActionEvent actionEvent) {
    }

    public void Go_To_Message(ActionEvent actionEvent) {
    }

    public void Go_To_Forum(ActionEvent actionEvent) {
    }

    public void Go_To_Auction(ActionEvent actionEvent) {
    }

    public void Go_To_Product(ActionEvent actionEvent) {
    }

    public void Go_To_Home(ActionEvent actionEvent) {
    }
/* @FXML
 void gotoEvent(ActionEvent event){
     try{
         Parent root = FXMLLoader.load(getClass().getResource("afficher-event-view.fxml"));
         grid.getScene().setRoot(root);
     } catch (IOException ex) {
         System.out.println(ex.getMessage());
     }
 }*/

}


