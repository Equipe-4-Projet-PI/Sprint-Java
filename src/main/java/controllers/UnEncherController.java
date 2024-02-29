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

    private Auction auc;

    public void initData(Auction auction) {
        titreEncher.setText(auction.getNom());
        prix_initial.setText(String.valueOf(auction.getPrix_initial()));
        dateL.setText(String.valueOf(auction.getDate_lancement()));
        dateC.setText(String.valueOf(auction.getDate_cloture()));
        byte[] imageData = loadImageFromDatabase(auction.getId_produit());
        Image image = new Image(new ByteArrayInputStream(imageData));
        imageEncher.setImage(image);
        IDAuction.setText(String.valueOf(auction.getId()));
        this.auc = auction;
        //hbox.setStyle("-fx-background-color: #ACAFB6;"+
                //" -fx-border-radius: 15; ");

    }



    //hedhy traja3 taswiret l produit mtaa auction
    private byte[] loadImageFromDatabase(int id_produit) {
        byte[] imageData = null;
        imageData = serviceAuction.loadImageFromDatabase(id_produit);
        return imageData;
    }

//    @FXML
//    void AfficherEncher(MouseEvent event) throws IOException {
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("EnchereDetail"));
//            EncherDetailController cont = new EncherDetailController();
//            loader.setController(cont);
//            Parent root = loader.load();
//            Scene scene = prix_initial.getScene();
//            scene.setRoot(root);
//
//            try {
//                Auction auc = serviceAuction.getById(id);
//                System.out.println(auc);
//                cont.initData(auc);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//        } catch (IOException e) {
//            e.getMessage();
//        }catch(NullPointerException j ){
//            j.printStackTrace();
//        }
//
//    }

    @FXML
    void AfficherEncher(MouseEvent event) throws IOException {

        try{
            Parent root = loadEnchere();
            imageEncher.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private Parent loadEnchere() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EncherDetail.fxml"));
        EncherDetailController controller = new EncherDetailController();
        loader.setController(controller);
        controller.initData(auc);
        Parent root = loader.load();
        return root;
    }
}
