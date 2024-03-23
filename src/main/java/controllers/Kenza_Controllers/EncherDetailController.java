package controllers.Kenza_Controllers;

import Services.RatingService;
import entities.User;
import services.ServiceAuction;
import services.ServiceParticipant;
import entities.Auction;
import entities.Auction_participant;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.geometry.Pos;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import services.ServiceUser;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class EncherDetailController{
    int id_user ;

    @FXML
    private Button ParticiperButton;
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
    private Label tfNomProduit;

    @FXML
    private TextField montant;


    @FXML
    private Button effectuerButton;
    @FXML
    private HBox hboxFX;
    ServiceAuction serviceAuction = new ServiceAuction();
    @FXML
    private Label remainingTimeLabel;
    @FXML
    private ImageView photoTimer;
    private ScheduledExecutorService scheduler;

    @FXML
    private VBox ratingBox;



    List<Auction_participant> recentlyAdded;
    private Auction auc ;

    public void SetDataAgain()
    {
        photoTimer.setVisible(false);
        if(serviceAuction.getSituation(auc) == -1){
            ParticiperButton.setVisible(false);
            photoTimer.setVisible(false);
            showSuccessAlert("Enchère pas encore commencé !!");
        }else if(serviceAuction.getSituation(auc) == 1){
            ParticiperButton.setVisible(false);
            showSuccessAlert("Enchère Terminé" +
                    "!!");
            photoTimer.setVisible(false);
        }else{
            startTimer();
            photoTimer.setVisible(true);
        }
        montant.setVisible(false);

        try{
            System.out.println(auc.getId());
        }catch (NullPointerException j){
            j.printStackTrace();
        }

        String nomProduit = null; // Initialize to null or any default value
        try {
            nomProduit = serviceAuction.getNomProduit(auc.getId());
        } catch (SQLException e) {
            System.out.println("lalalalalalal");
        }

        tfNomProduit.setText(nomProduit);
        nomEnchere.setText(auc.getNom());
        prixInitial.setText("Commencez A Partir De : "+String.valueOf(auc.getPrix_initial())+ "DT Jusqu'à : "+String.valueOf(auc.getPrix_final())+"DT");
        dateLancement.setText(String.valueOf(auc.getDate_lancement()));
        dateCloture.setText(String.valueOf(auc.getDate_cloture()));
        try{
            Image image = new Image("file:" + loadImageFromDatabase(auc.getId_produit()));
            imageProduit.setImage(image);
        }catch(Exception e ){
            e.printStackTrace();
        }

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
                fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/Participant.fxml"));
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


    private void startTimer() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::updateRemainingTime, 0, 1, TimeUnit.SECONDS);
    }

    private void updateRemainingTime() {
        LocalDateTime endDateTime = auc.getDate_cloture().atStartOfDay();
        Timestamp endTimeStamp = Timestamp.valueOf(endDateTime);
        long currentTime = System.currentTimeMillis();
        long endTime = endTimeStamp.getTime();

        long remainingTimeMillis = endTime - currentTime;

        if (remainingTimeMillis > 0) {
            long remainingSeconds = remainingTimeMillis / 1000;
            long days = remainingSeconds / (24 * 3600);
            long hours = (remainingSeconds % (24 * 3600)) / 3600;
            long minutes = ((remainingSeconds % (24 * 3600)) % 3600) / 60;
            long seconds = ((remainingSeconds % (24 * 3600)) % 3600) % 60;

            String remainingTime = String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);

            // Update the JavaFX UI on the JavaFX Application Thread
            Platform.runLater(() -> remainingTimeLabel.setText("Remaining Time: " + remainingTime));
        } else {
            // Auction has ended, display a message or take appropriate action
            Platform.runLater(() -> remainingTimeLabel.setText("Auction has ended"));
            scheduler.shutdown(); // Optionally, you can stop the timer if needed
        }
    }


    @FXML
    void effectuerArgent(ActionEvent event) {
        Auction_participant auctionParticipant = new Auction_participant();
        auctionParticipant.setId_participant(id_user);
        auctionParticipant.setPrix(Integer.parseInt(montant.getText()));
        auctionParticipant.setId_auction(auc.getId());
        System.out.println("heeedha hoswa l participant "+auctionParticipant);
        System.out.println("****id auction : +"+ auctionParticipant.getId_auction());

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

    private VBox createRatingInterface() {
        VBox ratingBox = new VBox(10);
        ratingBox.setAlignment(Pos.CENTER);

        Label ratingLabel = new Label("Rating: 0");
        Slider ratingSlider = new Slider(0, 5, 0);

        Button submitButton = new Button("Submit Rating");
        submitButton.setOnAction(event -> {
            int rating = (int) ratingSlider.getValue();
            ratingLabel.setText("Rating: " + rating);
            RatingService.submitRating(rating);

            // Optionally, you can remove the ratingBox after submitting the rating
            hboxFX.getChildren().remove(ratingBox);
        });

        ratingBox.getChildren().addAll(ratingLabel, ratingSlider, submitButton);

        return ratingBox;
    }

    @FXML
    private void getVBox(ActionEvent event) {
        VBox ratingBox = createRatingInterface();
        Stage ratingStage = new Stage();
        ratingStage.setTitle("Rating Window");

        Scene ratingScene = new Scene(ratingBox, 250, 200);

        ratingStage.setScene(ratingScene);

        ratingStage.show();
    }


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


    Preferences preferences = Preferences.userNodeForPackage(EncherArtistController.class);

    ServiceUser serviceUser = new ServiceUser();
    String savedUsername = preferences.get("username", null);
    String savedPassword = preferences.get("Password", null);
    User userlogged = serviceUser.GetUser(savedUsername,savedPassword);
    private Parent loadEnchere() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/EncherDetail.fxml"));
        EncherDetailController controller = new EncherDetailController();
        loader.setController(controller);
        controller.initData(auc);
        Parent root = loader.load();
        return root;
    }

    //hedhy traja3 taswiret l produit mtaa auction
    private String loadImageFromDatabase(int id_produit) {
        String imageData = "";
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
    void retouner(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/Enchers.fxml"));


        Parent loginSuccessRoot = loader.load();
        Scene scene = dateLancement.getScene();
        scene.setRoot(loginSuccessRoot);
        EnchersController enchersController = loader.getController();
        enchersController.setuser(userlogged);
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
    public void setIDUser(int idUser) {
        this.id_user=idUser;
    }
}
