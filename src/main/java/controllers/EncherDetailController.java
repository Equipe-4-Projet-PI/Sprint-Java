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
import javafx.scene.control.*;


import javafx.scene.control.Button;
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
    private Spinner<Integer> spinner;

    @FXML
    private Button effectuerButton;
    @FXML
    private HBox hboxFX;
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceParticipant serviceParticipant = new ServiceParticipant();


    List<Auction_participant> recentlyAdded;



    public void initData(Auction auction) {
        try{
            initialize(auction.getId());
        }catch (NullPointerException j){
            j.printStackTrace();
        }
        nomEnchere.setText(auction.getNom());
        prixInitial.setText(String.valueOf(auction.getPrix_initial()));
        dateLancement.setText(String.valueOf(auction.getDate_lancement()));
        dateCloture.setText(String.valueOf(auction.getDate_cloture()));
        byte[] imageData = loadImageFromDatabase(auction.getId_produit());
        Image image = new Image(new ByteArrayInputStream(imageData));
        imageProduit.setImage(image);
        nbreParticipant.setText(String.valueOf(countPartcipant(auction.getId())));
    }

    public void initialize(int id_auction) {
        recentlyAdded =new ArrayList<>(recentlyAdded1());
        System.out.println("the size of data "+recentlyAdded.size());
        try {
            VBox mainVBox = new VBox();
            hboxFX.getChildren().add(mainVBox);
            HBox currentHBox = new HBox();
            currentHBox.setSpacing(10);
            for (int i = 0 ; i<recentlyAdded.size();i++){
                if(i> 0 && i % 1 == 0){
                    mainVBox.getChildren().add(currentHBox);
                    currentHBox = new HBox();
                }
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
    @FXML
    void handleParticipantsLabelClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ListeParticipant.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Liste Participants");
            newStage.setScene(new Scene(root));

            ListeParticipantsController listeParticipantsController = loader.getController();

            listeParticipantsController.initialize(2);

            newStage.show();


        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void effectuerArgent(ActionEvent event) {
        int id_user = 5;
        Auction_participant auctionParticipant = new Auction_participant();

        try {
            auctionParticipant = serviceParticipant.getParticipantById(id_user);
            auctionParticipant.setPrix(Integer.parseInt(spinner.getValue().toString()));
        } catch (SQLException e) {
            e.getMessage();
        }

        if(isMontantValide(auctionParticipant)){
            try {
                serviceParticipant.modifier(auctionParticipant);
                serviceAuction.modifierPrixFinal(serviceAuction.getById(auctionParticipant.getId_auction()));

                Stage stage = (Stage) nomEnchere.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EncherDetail.fxml"));
                Parent root = loader.load();

                EncherDetailController controller = loader.getController();
                controller.initData(serviceAuction.getById(auctionParticipant.getId_auction()));

                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("argent bien effectuée ! ");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Le montant doit être supérieur au prix initial et au dernier montant proposé.");
            alert.showAndWait();
        }
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

    public void participerClicked(ActionEvent actionEvent) {
        spinner.setVisible(true);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        spinner.setValueFactory(valueFactory);
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

    private List<Auction_participant> recentlyAdded1(){
        try {
            return serviceParticipant.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
