package controllers;

import Services.ServiceAuction;
import entities.Auction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class EnchereContainerController {
    ServiceAuction serviceAuction = new ServiceAuction();
    @FXML
    private Label prixInitialFx;
    @FXML
    private VBox vbox;
    @FXML
    private ImageView imageE;

    @FXML
    private Label titre;

    Auction auc;

    public void initData(Auction auction) {
        titre.setText(auction.getNom());
        prixInitialFx.setText("Estimée a partir de "+String.valueOf(auction.getPrix_initial()));
        byte[] imageData = loadImageFromDatabase(auction.getId_produit());
        javafx.scene.image.Image image = new Image(new ByteArrayInputStream(imageData));
        imageE.setImage(image);

        vbox.setStyle("-fx-background-color: #ACAFB6;"+
                " -fx-border-radius: 15; ");

        this.auc=auction;
    }


    //hedhy traja3 taswiret l produit mtaa auction
    private byte[] loadImageFromDatabase(int id_produit) {
        byte[] imageData = null;
        imageData = serviceAuction.loadImageFromDatabase(id_produit);
        return imageData;
    }

    @FXML
    void AfficherEncher(MouseEvent event) throws IOException {

        try{
            Parent root = loadEnchere();
            imageE      .getScene().setRoot(root);
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
