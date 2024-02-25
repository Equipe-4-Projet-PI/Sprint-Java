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

public class ModifierController implements Initializable {
    ServiceAuction serviceAuction = new ServiceAuction();

    @FXML
    private TextField tf_nomAuction;
    @FXML
    private TextField tf_produit;
    @FXML
    private Spinner<Integer> tf_prix_initial ;
    @FXML
    private DatePicker tf_date;

    @FXML
    private DatePicker tf_dateC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int encher_id = 10;

        try {
            tf_produit.setText(serviceAuction.loadProductFromDatabase(encher_id));
            tf_produit.setDisable(true);
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
            tf_prix_initial.setValueFactory(valueFactory);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void ModifierAuction() {
        int auctionId = 1;

        try {
            Auction existingAuction = serviceAuction.getAuctionById(auctionId);

            existingAuction.setNom(tf_nomAuction.getText());
            existingAuction.setPrix_initial(tf_prix_initial.getValue());
            existingAuction.setDate_lancement(tf_date.getValue());
            existingAuction.setDate_cloture(tf_dateC.getValue());

            serviceAuction.modifier(existingAuction);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
