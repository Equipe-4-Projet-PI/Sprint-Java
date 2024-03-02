package controllers;

import Services.ServiceAuction;
import Services.ServiceParticipant;
import entities.Auction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;


public class EnchereContainerController {

    int idGagnant = 6;
    int id_user;
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceParticipant serviceParticipant = new ServiceParticipant();
    @FXML
    private ImageView tfFavori;
    @FXML
    private Label prixInitialFx;
    @FXML
    private VBox vbox;
    @FXML
    private ImageView imageE;

    @FXML
    private Label titre;

    Auction auc;
    @FXML
    private ImageView tfStatu;
    String imagePath;

    private boolean isFavorite = false;

    public void initData(Auction auction) {
        this.auc=auction;
        titre.setText(auction.getNom());
        prixInitialFx.setText("Estimée a partir de "+String.valueOf(auction.getPrix_initial()));
        //image produit
        byte[] imageData = loadImageFromDatabase(auction.getId_produit());
        javafx.scene.image.Image image = new Image(new ByteArrayInputStream(imageData));
        imageE.setImage(image);
        //image favori
        byte[] imageFavori = new byte[0];
        int verifFavori = serviceParticipant.getEtatFavori(auc.getId() , id_user);
        if(serviceAuction.getSituation(auc) == -1 || serviceAuction.getSituation(auc) == 0){
            if(verifFavori == 0){
                tfFavori.setImage(new Image(new File("E:\\\\ESPRIT\\\\pi\\\\favori.png").toURI().toString()));
            }else if (verifFavori == 1){
                isFavorite = true;
                tfFavori.setImage(new Image(new File("E:\\\\ESPRIT\\\\pi\\\\prefere.png").toURI().toString()));
            }
        }

        //image Etat
        byte[] imageStau = new byte[0];
        int verif = serviceAuction.getSituation(auction);
        if(verif == -1){
            imagePath = "E:\\\\ESPRIT\\\\pi\\\\bientot-disponible.png";
        }else if (verif == 0 ){
            imagePath = "E:\\\\ESPRIT\\\\pi\\\\habitent.png";
        }else if(verif == 1){
            imagePath = "E:\\\\ESPRIT\\\\pi\\\\vendu.png";
        }else{
            System.out.println("there is a probleme with the statu");
        }

        javafx.scene.image.Image image1 = new Image(new File(imagePath).toURI().toString());
        tfStatu.setImage(image1);

        vbox.setStyle("-fx-background-color: #E5F1FA;"+
                " -fx-border-radius: 15; ");


    }


    //hedhy traja3 taswiret l produit mtaa auction
    private byte[] loadImageFromDatabase(int id_produit) {
        byte[] imageData = null;
        imageData = serviceAuction.loadImageFromDatabase(id_produit);
        return imageData;
    }

    @FXML
    void AfficherEncher(MouseEvent event) throws IOException {
        int verif = serviceAuction.getSituation(auc);
        if(verif == 1 ){

            if(id_user!= idGagnant){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Cet enchère est Bien Terminé !");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bravo VOUS AVEZ GAGNE !");
                alert.setContentText("Enchère ajoutée");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Payment.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = tfFavori.getScene();
                scene.setRoot(loginSuccessRoot);
            }

        }else{
            try{
                Parent root = loadEnchere();
                imageE.getScene().setRoot(root);
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
            tfFavori.setImage(new Image(new File("E:\\\\ESPRIT\\\\pi\\\\favori.png").toURI().toString()));
            serviceParticipant.deleteFavori(auc.getId(),id_user);
            isFavorite = false;
        } else {
            // L'utilisateur n'a pas encore marqué l'enchère comme favorite, changez à l'image favorite
            tfFavori.setImage(new Image(new File("E:\\\\ESPRIT\\\\pi\\\\prefere.png").toURI().toString()));
            serviceParticipant.addFavori(auc.getId(),id_user);
            isFavorite = true;
        }
    }

    public void setIdArtist(int idArtist) {
        this.id_user=idArtist;
    }
}
