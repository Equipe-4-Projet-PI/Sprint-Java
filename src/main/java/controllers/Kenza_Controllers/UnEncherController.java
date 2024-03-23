package controllers.Kenza_Controllers;

import entities.User;
import services.ServiceAuction;
import services.ServiceParticipant;
import entities.Auction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.input.MouseEvent;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import services.ServiceUser;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

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
    private User userlogged;
     int iduser ;

ServiceUser serviceUser = new ServiceUser();

    private boolean isFavorite = false;


    public void setIdArtist(User user) {
        System.out.println("hethi 1");
        if (user!=null) {
            id_user = user.getId_User();

            userlogged = new User();
            userlogged.setGender(user.getGender());
            userlogged.setDOB(user.getDOB());
            userlogged.setPhone(user.getPhone());
            userlogged.setAdress(user.getAdress());
            userlogged.setUsername(user.getUsername());
            userlogged.setEmail(user.getEmail());
            userlogged.setFirstName(user.getFirstName());
            userlogged.setPassword(user.getPassword());
            userlogged.setLastName(user.getLastName());
            userlogged.setId_User(user.getId_User());
            userlogged.setRole(user.getRole());
            userlogged.setImageURL(user.getImageURL());
        }


    }


    public void initData(Auction auction) {
        System.out.println(id_user);
        this.auc = auction;
        titreEncher.setText(auction.getNom());
        prix_initial.setText(String.valueOf(auction.getPrix_initial())+"DT");
        dateL.setText(String.valueOf(auction.getDate_lancement()));
        dateC.setText(String.valueOf(auction.getDate_cloture()));
        try{
            Image image = new Image(loadImageFromDatabase(auc.getId_produit()));

            imageEncher.setImage(image);
        }catch(Exception e ){
            e.printStackTrace();
        }


        IDAuction.setText(String.valueOf(auction.getId()));

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
    private String loadImageFromDatabase(int id_produit) {
        String imageData = null;
        imageData = serviceAuction.loadImageFromDatabase(id_produit);
        return imageData;
    }

    @FXML
    void AfficherEncher(MouseEvent event) throws IOException {





        int verif = serviceAuction.getSituation(auc);
        if(verif == 1){
            System.out.println(getIdGagnant(auc));


            if (id_user != getIdGagnant(auc)) {
                try{
                    Parent root = loadEnchere();
                    imageEncher.getScene().setRoot(root);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bravo VOUS AVEZ GAGNE !");
                alert.setContentText("Bravo VOUS AVEZ GAGNE !!");

                ButtonType detailsButton = new ButtonType("Commencer le payement.", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().add(detailsButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == detailsButton) {
                        try {
                            PayFlouci(event);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }else{
            try{
                Parent root = loadEnchere();
                imageEncher.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}



    private Parent loadEnchere() throws Exception {
        System.out.println(userlogged);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/EncherDetail.fxml"));
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


    void PayFlouci(MouseEvent event) throws JSONException {
        Double montant = null;
        try {
            montant = serviceAuction.getPrixFinal(auc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("app_token", "aebe6188-4587-4ff0-9b1c-a26c7898ee73");
        jsonBody.put("app_secret", "83c2f9f3-0fdc-4dec-9bf2-e642c1cce53d");
        jsonBody.put("accept_card", true); // Use boolean value, not string
        jsonBody.put("amount", (long) (montant * 1000));
        jsonBody.put("success_link", "https://ruperhat.com/wp-content/uploads/2020/06/Paymentsuccessful21.png");
        jsonBody.put("fail_link", "https://hypixel.net/attachments/1690923493412-png.3230490/");
        jsonBody.put("session_timeout_secs", 1200);
        jsonBody.put("developer_tracking_id", "df9dd458-65ed-4d8b-b354-302077358ef2");


        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonBody.toString());
        Request request = new Request.Builder()
                .url("https://developers.flouci.com/api/generate_payment")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("Payment generated successfully");
                String responseBody = response.body().string();
                handleResponse(responseBody);
                delete(auc.getId());

            } else {
                System.out.println("Error generating payment: " + response.code());
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void delete(int id_auction){
        try {
            serviceAuction.supprimer_auction(id_auction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleResponse(String responseBody) throws JSONException {
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONObject result = jsonResponse.getJSONObject("result");
        String linkString = result.getString("link");
        URI link = null;
        try {
            link = new URI(linkString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        //open browser
        try {
            Desktop.getDesktop().browse(link);
            System.out.println("t7al");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdGagnant(Auction auc){
        return serviceParticipant.getIdGagnat(auc);
    }

    public void setID(int idUser) {
    }
}
