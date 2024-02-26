package controllers;

import Services.ServiceAuction;
import entities.Auction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModifierController{
    ServiceAuction serviceAuction = new ServiceAuction();

    @FXML
    private Label nomProduit;

    @FXML
    private TextField tf_nomAuction;
    @FXML
    private TextField fxprix_inital;
    @FXML
    private DatePicker tf_date;

    @FXML
    private DatePicker tf_dateC;
    @FXML
    private TextField id_auction;

    @FXML
    void ModifierAuction() {
        int id_enchere = Integer.parseInt(id_auction.getText());
        String nom_auction = tf_nomAuction.getText();
        String nom_produit = nomProduit.getText();
        int prix_initial = Integer.parseInt(fxprix_inital.getText());
        LocalDate date_lancement = tf_date.getValue();
        LocalDate date_cloture = tf_dateC.getValue();

        // Create a new User object with the edited values
        Auction editAuction = new Auction();
        editAuction.setId(id_enchere);
        editAuction.setNom(nom_auction);
        editAuction.setPrix_initial(prix_initial);
        editAuction.setDate_lancement(date_lancement);
        editAuction.setDate_cloture(date_cloture);

        try {
            // Update the user details using the ServiceUser
            serviceAuction.modifier(editAuction);

            // Show a confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit User");
            alert.setHeaderText(null);
            alert.setContentText("Auction details updated successfully");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeEncheres.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = tf_nomAuction.getScene();
            scene.setRoot(loginSuccessRoot);
        } catch (SQLException | IOException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error updating user details: " + e.getMessage());
            alert.showAndWait();
        }

    }

    public void initData(Auction auction) {
        tf_nomAuction.setText(auction.getNom());
        try {
            nomProduit.setText(serviceAuction.loadProductFromDatabase(auction.getId()));
            fxprix_inital.setText(String.valueOf(auction.getPrix_initial()));
            tf_date.setValue(LocalDate.parse(String.valueOf(auction.getDate_lancement())));
            tf_dateC.setValue(LocalDate.parse(String.valueOf(auction.getDate_cloture())));
            id_auction.setText(String.valueOf(auction.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
