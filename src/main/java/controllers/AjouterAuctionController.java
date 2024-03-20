package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Services.ServiceAuction;
import entities.Auction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.time.LocalDate;

public class AjouterAuctionController implements Initializable {

    ServiceAuction serviceAuction = new ServiceAuction();



    @FXML
    private TextField tf_nomAuction;
    @FXML
    private ChoiceBox<String> tf_produit;
    @FXML
    private Spinner<Integer> tf_prix_initial ;
    @FXML
    private DatePicker tf_date;

    @FXML
    private DatePicker tf_dateC;

    int id_Artist = 1;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> productNames = loadProductValuesFromDatabase(id_Artist);

        ObservableList<String> products = FXCollections.observableArrayList(productNames);
        tf_produit.setItems(products);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory
                .IntegerSpinnerValueFactory(1, 1000, 1);
        tf_prix_initial.setValueFactory(valueFactory);
    }



    private List<String> loadProductValuesFromDatabase(int userId) {
        try {
            ServiceAuction serviceAuction = new ServiceAuction();
            List<String> productNames = serviceAuction.loadProductValuesFromDatabase(userId);

            return productNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @FXML
    void AjouterAuction() {
        LocalDate dateLancement = tf_date.getValue();
        LocalDate dateCloture = tf_dateC.getValue();
        try {
            String selectedProductName = tf_produit.getValue();
            List<String> productNames = loadProductValuesFromDatabase(id_Artist);

            if (selectedProductName != null && productNames.contains(selectedProductName)) {
                int productId = serviceAuction.getProductID(selectedProductName);

                if (verifierDate(tf_date, tf_dateC)) {
                    serviceAuction.ajouter_by_artist(new Auction(tf_nomAuction.getText(), dateCloture, dateLancement, Integer.parseInt(tf_prix_initial.getValue().toString()), productId), id_Artist);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Enchère ajoutée");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/artistEnchers.fxml"));
                    Parent loginSuccessRoot = loader.load();
                    Scene scene = tf_nomAuction.getScene();
                    scene.setRoot(loginSuccessRoot);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Date non valide !");
                    alert.setContentText("La date de l'enchère n'est pas valide.");
                    alert.showAndWait();
                }
            } else {
                System.out.println("Selected product not found.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Selected product not found.");
                alert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    public boolean verifierDate(DatePicker tfDate, DatePicker tfDateC) {
        LocalDate dateDebut = tfDate.getValue();
        LocalDate dateFin = tfDateC.getValue();

        LocalDate dateActuelle = LocalDate.now();

        if (dateDebut != null && (dateDebut.isAfter(dateActuelle) ||dateActuelle.isEqual(dateDebut))) {
            if (dateFin != null && dateFin.isAfter(dateDebut)) {
                return true;
            }
        }
        return false;
    }


    @FXML
    public void retourner(javafx.scene.input.MouseEvent mouseEvent) throws IOException{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/artistEnchers.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = tf_nomAuction.getScene();
                scene.setRoot(loginSuccessRoot);

            }catch (IOException e){
                System.out.println(e.getMessage());
            }

        }

}
