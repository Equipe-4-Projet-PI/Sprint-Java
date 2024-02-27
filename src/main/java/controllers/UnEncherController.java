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
import javafx.scene.layout.HBox;
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

public class UnEncherController {
    int id ;
    @FXML
    private Label IDAuction;
    ServiceAuction serviceAuction = new ServiceAuction();

    @FXML
    private Label dateC;

    @FXML
    private Label dateL;

    @FXML
    private HBox hbox;

    @FXML
    private ImageView imageEncher;

    @FXML
    private Label prix_initial;

    @FXML
    private ImageView statu;

    @FXML
    private Label titreEncher;

    public void initData(Auction auction) {
        titreEncher.setText(auction.getNom());
        prix_initial.setText(String.valueOf(auction.getPrix_initial()));
        dateL.setText(String.valueOf(auction.getDate_lancement()));
        dateC.setText(String.valueOf(auction.getDate_cloture()));
        byte[] imageData = loadImageFromDatabase(auction.getId_produit());
        Image image = new Image(new ByteArrayInputStream(imageData));
        imageEncher.setImage(image);
        IDAuction.setText(String.valueOf(auction.getId()));
        setId(auction.getId());

    }

    public void setId(int id){
        this.id = id;
    }

    //hedhy traja3 taswiret l produit mtaa auction
    private byte[] loadImageFromDatabase(int id_produit) {
        byte[] imageData = null;
        imageData = serviceAuction.loadImageFromDatabase(id_produit);
        return imageData;
    }

    @FXML
    void AfficherEncher(MouseEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EncherDetail.fxml"));
            Parent root = loader.load();
            Scene scene = dateC.getScene();
            scene.setRoot(root);
            EncherDetailController encheredetails = loader.getController();

            try {
                encheredetails.initData(serviceAuction.getById(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            e.getMessage();
        }

    }
}
