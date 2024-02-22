package controllers;

import Services.ServiceAuction;
import Services.ServiceParticipant;
import entities.Auction;
import entities.Auction_participant;
import entities.Personne;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

public class EnchereController {

    @FXML
    private Label date_cloture;

    @FXML
    private Label date_lalncement;

    @FXML
    private Label prix_initial;

    @FXML
    private Label statu;

    @FXML
    private Label titre_produit;


    public void setData(Personne personne){
        //LocalDate dateActuelle = LocalDate.now();
        prix_initial.setText(String.valueOf(personne.getPrix()));
        titre_produit.setText(personne.getTitre());
        //date_lalncement.setText(auction.getFormattedDate1());
        //date_cloture.setText(auction.getFormattedDate2());
        /*if(auction.getFormattedDate1().isBefore(dateActuelle) && auction.getFormattedDate2().idAfter(dateActuelle)){
            statu.setText("LIVE");
        }*/

    }



}
