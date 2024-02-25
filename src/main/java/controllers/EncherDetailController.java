package controllers;

import Services.ServiceAuction;
import Services.ServiceParticipant;
import com.google.protobuf.StringValue;
import entities.Auction;
import entities.Auction_participant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;


import javax.xml.crypto.Data;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class EncherDetailController {

    @FXML
    private Label nbreParticipant;

    @FXML
    private Label dateCloture;

    @FXML
    private Label dateLancement;

    @FXML
    private Label nomEnchere;

    @FXML
    private Label prixFinal;

    @FXML
    private Label prixInitial;

    @FXML
    private ImageView imageProduit;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private Button effectuerButton;
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceParticipant serviceParticipant = new ServiceParticipant();

    public void initData(Auction auction) {
        nomEnchere.setText(auction.getNom());
        prixInitial.setText(String.valueOf(auction.getPrix_initial()));
        prixFinal.setText(String.valueOf(auction.getPrix_final()));
        dateLancement.setText(String.valueOf(auction.getDate_lancement()));
        dateCloture.setText(String.valueOf(auction.getDate_cloture()));
        byte[] imageData = loadImageFromDatabase(auction.getId_produit());
        Image image = new Image(new ByteArrayInputStream(imageData));
        imageProduit.setImage(image);
        nbreParticipant.setText(String.valueOf(countPartcipant(auction.getId())));

    }
    @FXML
    void handleParticipantsLabelClick(MouseEvent event) {
        try {
            // Load the FXML file for the new window
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ListeParticipant.fxml"));
            Parent root = loader.load();

            // Create a new stage for the new window
            Stage newStage = new Stage();
            newStage.setTitle("Liste Participants");
            newStage.setScene(new Scene(root));

            // Get the controller of the new window
            ListeParticipantsController listeParticipantsController = loader.getController();

            // Pass data to the controller of the new window
            listeParticipantsController.initialize(2);

            // Show the new window
            newStage.show();

            // Close the current window if needed
            // ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void effectuerArgent(ActionEvent event) {
        int id_user = 5;
        Auction_participant auctionParticipant= new Auction_participant();
        try {
            auctionParticipant = serviceParticipant.getParticipantById(id_user);
            auctionParticipant.setPrix(Integer.parseInt(spinner.getValue().toString()));

        } catch (SQLException e) {
            e.getMessage();
        }
        try {
            serviceParticipant.modifier(auctionParticipant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            serviceAuction.modifierPrixFinal(serviceAuction.getAuctionById(auctionParticipant.getId_auction()));
        } catch (SQLException e) {
            e.getMessage();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("argent bien effectuée ! ");
        alert.showAndWait();

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
}
