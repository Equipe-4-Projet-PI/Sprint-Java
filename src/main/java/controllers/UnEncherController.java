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
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class UnEncherController {
    int id_user ;
    @FXML
    private Label IDAuction;
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceParticipant serviceParticipant = new ServiceParticipant();

    @FXML
    private Label dateC;

    @FXML
    private Label dateL;

    @FXML
    private HBox hbox;
    @FXML
    private ImageView loveFX;

    @FXML
    private ImageView imageEncher;

    @FXML
    private Label prix_initial;

    @FXML
    private ImageView statu;

    @FXML
    private Label titreEncher;

    private Auction auc;
    String imagePath1;

    private boolean isFavorite = false;

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
        byte[] imageStau = new byte[0];
        int verif = serviceAuction.getSituation(auction);
        if(verif == -1){
            imagePath1 = "E:\\\\ESPRIT\\\\pi\\\\bientot-disponible.png";
        }else if (verif == 0 ){
            imagePath1 = "E:\\\\ESPRIT\\\\pi\\\\habitent.png";
        }else if(verif == 1){
            imagePath1 = "E:\\\\ESPRIT\\\\pi\\\\vendu.png";
        }else{
            System.out.println("there is a probleme with the statu");
        }

        javafx.scene.image.Image image1 = new Image(new File(imagePath1).toURI().toString());
        statu.setImage(image1);
        //hbox.setStyle("-fx-background-color: #ACAFB6;"+
                //" -fx-border-radius: 15; ");
        if(serviceAuction.getSituation(auc) == -1 || serviceAuction.getSituation(auc) == 0){
            //image favori
            byte[] imageFavori = new byte[0];
            int verifFavori = serviceParticipant.getEtatFavori(auc.getId() , id_user);
            if(verifFavori == 0){
                loveFX.setImage(new Image(new File("E:\\\\ESPRIT\\\\pi\\\\favori.png").toURI().toString()));
            }else if (verifFavori == 1){
                isFavorite = true;
                loveFX.setImage(new Image(new File("E:\\\\ESPRIT\\\\pi\\\\prefere.png").toURI().toString()));
            }
        }


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
        int verif = serviceAuction.getSituation(auc);
        if(verif == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Cet enchère est Bien Terminé !");
            alert.showAndWait();
        }else{
            try{
                Parent root = loadEnchere();
                imageEncher.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private Parent loadEnchere() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EncherDetail.fxml"));
        EncherDetailController controller = new EncherDetailController();
        loader.setController(controller);
        controller.setIDUser(id_user);
        controller.initData(auc);
        Parent root = loader.load();
        return root;
    }

    @FXML
    void changerFavori(MouseEvent event) {
        if (isFavorite) {
            // L'utilisateur a déjà marqué l'enchère comme favorite, changez à l'image non favorite
            loveFX.setImage(new Image(new File("E:\\\\ESPRIT\\\\pi\\\\favori.png").toURI().toString()));
            serviceParticipant.deleteFavori(auc.getId(),id_user);
            isFavorite = false;
        } else {
            // L'utilisateur n'a pas encore marqué l'enchère comme favorite, changez à l'image favorite
            loveFX.setImage(new Image(new File("E:\\\\ESPRIT\\\\pi\\\\prefere.png").toURI().toString()));
            serviceParticipant.addFavori(auc.getId(),id_user);
            isFavorite = true;
        }
    }
    public void setIdArtist(int idArtist) {
        this.id_user=idArtist;
    }
}
