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



    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> productNames = loadProductValuesFromDatabase(1);

        ObservableList<String> products = FXCollections.observableArrayList(productNames);
        tf_produit.setItems(products);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
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
            List<String> productNames = loadProductValuesFromDatabase(1);

            if (selectedProductName != null && productNames.contains(selectedProductName)) {
                int productId = serviceAuction.getProductID(selectedProductName);
                int id_artist = 1 ;
                serviceAuction.ajouter(new Auction( tf_nomAuction.getText() ,dateCloture,dateLancement, Integer.parseInt(tf_prix_initial.getValue().toString()) ,  productId ));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Enchère ajoutée");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEncheres.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = tf_nomAuction.getScene();
                scene.setRoot(loginSuccessRoot);

            } else {
                System.out.println("Selected product not found.");
            }
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    public void retourner(javafx.scene.input.MouseEvent mouseEvent) throws IOException{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeEncheres.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = tf_nomAuction.getScene();
                scene.setRoot(loginSuccessRoot);

            }catch (IOException e){
                System.out.println(e.getMessage());
            }

        }

}
