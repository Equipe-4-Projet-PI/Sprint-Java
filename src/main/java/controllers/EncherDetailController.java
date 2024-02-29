package controllers;

import Services.ServiceAuction;
import Services.ServiceParticipant;
import com.google.protobuf.StringValue;
import entities.Auction;
import entities.Auction_participant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.*;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;


import javax.xml.crypto.Data;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EncherDetailController{
    ServiceParticipant serviceParticipant = new ServiceParticipant();

    @FXML
    private Label nbreParticipant;

    @FXML
    private Label dateCloture;

    @FXML
    private Label dateLancement;

    @FXML
    private Label nomEnchere;


    @FXML
    private Label prixInitial;

    @FXML
    private ImageView imageProduit;

    @FXML
    private TextField montant;


    @FXML
    private Button effectuerButton;
    @FXML
    private HBox hboxFX;
    ServiceAuction serviceAuction = new ServiceAuction();


    List<Auction_participant> recentlyAdded;
    private Auction auc ;

    public void SetDataAgain()
    {
//        System.out.println(auction.getId());
        montant.setVisible(false);

        try{
            System.out.println(auc.getId());
        }catch (NullPointerException j){
            j.printStackTrace();
        }
        nomEnchere.setText(auc.getNom());
        prixInitial.setText(String.valueOf(auc.getPrix_initial()));
        dateLancement.setText(String.valueOf(auc.getDate_lancement()));
        dateCloture.setText(String.valueOf(auc.getDate_cloture()));
        byte[] imageData = loadImageFromDatabase(auc.getId_produit());
        Image image = new Image(new ByteArrayInputStream(imageData));
        imageProduit.setImage(image);
        nbreParticipant.setText(String.valueOf(countPartcipant(auc.getId())));
    }
    public void initData(Auction auction) {
        this.auc = auction;
        System.out.println("Controle From Detail Controller  :");
        System.out.println(auction);
    }

    public void initialize() {

        SetDataAgain();
        recentlyAdded =new ArrayList<>(recentlyAddedParticipant());
        System.out.println("the number of Participant "+recentlyAdded.size());
        System.out.println(recentlyAdded);
        try {
            VBox mainVBox = new VBox();
            hboxFX.getChildren().add(mainVBox);
            HBox currentHBox = new HBox();
            for (int i = 0 ; i<recentlyAdded.size();i++){
                mainVBox.getChildren().add(currentHBox);
                currentHBox = new HBox();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Participant.fxml"));
                HBox participantBox = fxmlLoader.load();
                ParticipantController participantController = fxmlLoader.getController();
                try{
                    participantController.initData(recentlyAdded.get(i));
                }catch (NullPointerException j){
                    j.printStackTrace();
                }
                currentHBox.getChildren().add(participantBox);
            }
            mainVBox.getChildren().add(currentHBox);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    int id_user = 2;
    @FXML
    void effectuerArgent(ActionEvent event) {
        Auction_participant auctionParticipant = new Auction_participant();
        auctionParticipant.setId_participant(id_user);
        auctionParticipant.setPrix(Integer.parseInt(montant.getText()));
        auctionParticipant.setId_auction(auc.getId());
        System.out.println("heeedha hoswa l participant "+auctionParticipant);
        System.out.println("****id auction : +"+ auctionParticipant.getId_auction());// Set the correct auction ID

        try {
            if (serviceParticipant.search(id_user, auc.getId())) {
                // User exists, modify
                if (isMontantValide(auctionParticipant)) {
                    serviceParticipant.modifier(auctionParticipant);
                    System.out.println("hedhaa l auction li yra fih  : "+serviceAuction.getById(auctionParticipant.getId_auction()) );
                    serviceAuction.modifierPrixFinal(serviceAuction.getById(auctionParticipant.getId_auction()));

                    showSuccessAlert("L'enchère a été modifiée avec succès !");
                    refreshData();
                } else {
                    showErrorAlert("Le montant doit être supérieur au prix initial et au dernier montant proposé.");
                }
            } else {
                // User doesn't exist, add new participant
                if (isMontantValide(auctionParticipant)) {
                    serviceParticipant.ajouter(auctionParticipant);
                    serviceAuction.modifierPrixFinal(serviceAuction.getById(auctionParticipant.getId_auction()));

                    showSuccessAlert("L'enchère a été ajoutée avec succès !");
                    refreshData();
                } else {
                    showErrorAlert("Le montant doit être supérieur au prix initial et au dernier montant proposé.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

// Helper methods for showing alerts
        private void showSuccessAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText(message);
            alert.showAndWait();
        }

        private void showErrorAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(message);
            alert.showAndWait();
        }



    private Parent loadEnchere() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EncherDetail.fxml"));
        EncherDetailController controller = new EncherDetailController();
        loader.setController(controller);
        controller.initData(auc);
        Parent root = loader.load();
        return root;
    }

    //hedhy traja3 taswiret l produit mtaa auction
    private byte[] loadImageFromDatabase(int id_produit) {
        byte[] imageData = null;
        imageData = serviceAuction.loadImageFromDatabase(id_produit);
        return imageData;
    }

    //hedhy traja3 nombre de participant
    private int countPartcipant(int id_auction){
        int nbre= 0;
        try {
            nbre = serviceParticipant.countParticipant(id_auction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nbre;

    }

    @FXML
    public void participerClicked(ActionEvent actionEvent) {
        montant.setVisible(true);
        effectuerButton.setVisible(true);

    }



    private boolean isMontantValide(Auction_participant participant) {
        try {
            Auction auction = serviceAuction.getById(participant.getId_auction());
            int prixInitial = auction.getPrix_initial();
            int dernierPrixParticipant = (int) serviceParticipant.getDernierPrix(participant.getId_auction());

            return (participant.getPrix() > prixInitial) && (participant.getPrix() > dernierPrixParticipant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void retouner(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Enchers.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = prixInitial.getScene();
            scene.setRoot(loginSuccessRoot);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    private List<Auction_participant> recentlyAddedParticipant(){
        try {
            return serviceParticipant.list_by_auction(auc.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshData() {
        hboxFX.getChildren().clear();
        initialize();
    }

}
