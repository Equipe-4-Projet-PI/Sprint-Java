package controllers;

        import Services.ServiceAuction;
        import Services.ServiceParticipant;
        import entities.Auction;
        import entities.Auction_participant;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Accordion;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TitledPane;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.Pane;

        import java.io.IOException;
        import java.io.InputStream;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.List;
        import java.util.ResourceBundle;

public class AfficherEncheresController implements Initializable{

    private ServiceAuction serviceAuction;
    private ServiceParticipant serviceParticipant;
    @FXML
    private Label tf_accueil;

    @FXML
    private Button tf_bouttonAjout;

    @FXML
    private Button tf_bouttonParticiper;

    @FXML
    private Label tf_desc_P;

    @FXML
    private AnchorPane tf_desc_produit;

    @FXML
    private Label tf_enchere;

    @FXML
    private Pane tf_enchereC;

    @FXML
    private Label tf_forum;

    @FXML
    private HBox tf_imageC;

    @FXML
    private ImageView tf_imageParticipant;

    @FXML
    private ImageView tf_imageProduit;

    @FXML
    private Label tf_magasin;

    @FXML
    private Label tf_nomParticipant;

    @FXML
    private Label tf_nomUser;

    @FXML
    private Accordion tf_participant;

    @FXML
    private Label tf_prixAjoute;

    @FXML
    private Label tf_prixProposé;

    @FXML
    private Label tf_proposNous;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceAuction = new ServiceAuction();
        serviceParticipant = new ServiceParticipant();
        try {
            int enchereId = 9;
            afficherEnchere(enchereId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void afficherEnchere(int enchereId) throws SQLException {
        Auction auction = serviceAuction.getById(enchereId);

        if (auction != null) {
            tf_imageProduit.setImage(new Image(auction.getCheminImageProduit()));
            List<Auction_participant> participants = serviceParticipant.list_by_auction(auction.getId());

            tf_enchereC.getChildren().clear();

            for (Auction_participant participant : participants) {
                Accordion participantAccordion = new Accordion();

                TitledPane titledPane = creerTitledPane(participant, auction);
                participantAccordion.getPanes().add(titledPane);

                tf_enchereC.getChildren().add(participantAccordion);
            }
        } else {
            System.out.println("L'enchère avec l'ID " + enchereId + " n'existe pas.");
        }
    }

    private TitledPane creerTitledPane(Auction_participant participant, Auction auction) {
        TitledPane titledPane = new TitledPane();
        titledPane.setText(participant.getNom());

        HBox participantBox = new HBox();
        try (InputStream imageStream = serviceParticipant.getCheminImageParticipant(participant.getId_participant())) {

        ImageView imageParticipant = new ImageView(new Image(imageStream));

        Label nomParticipant = new Label("Nom: " + participant.getNom());

        float dernierPrix = serviceParticipant.getPrixAjoute(participant.getId_participant());
        Label prixProposeLabel = new Label("Prix Proposé: " + participant.getPrix());
        Label prixAjouteLabel = new Label("Prix Ajouté: " + ( participant.getPrix() - dernierPrix));

        participantBox.getChildren().addAll(imageParticipant, nomParticipant, prixProposeLabel, prixAjouteLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        titledPane.setContent(participantBox);

        return titledPane;
    }

    @FXML
    void AjouterEnchereBoutton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAuction.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = tf_enchereC.getScene();
            scene.setRoot(loginSuccessRoot);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void ParticiperEnchere(ActionEvent event) {

    }

}

