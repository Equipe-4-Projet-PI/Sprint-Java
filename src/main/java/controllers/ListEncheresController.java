package controllers;

import Services.ServiceAuction;
import entities.Auction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class ListEncheresController {

    @FXML
    private Label enchere;
    @FXML
    private TableColumn<Auction, Date> fxDateD;

    @FXML
    private TableColumn<Auction, DatePicker> fxDateF;

    @FXML
    private TableColumn<Auction, Integer> fxID;

    @FXML
    private TableColumn<Auction, Integer> fxIdArtist;

    @FXML
    private TableColumn<Auction,Integer> fxIdProduit;

    @FXML
    private TableColumn<Auction, String> fxNom;

    @FXML
    private TableColumn<Auction, Float> fxPrixFinal;

    @FXML
    private TableColumn<Auction, Float> fxPrixInitial;

    @FXML
    private TableView<Auction> fxTableViw;
    ServiceAuction serviceAuction = new ServiceAuction();

    public void initialize() throws SQLException {

        try {
            ObservableList<Auction> observableList = FXCollections.observableList(serviceAuction.afficher());
            fxTableViw.setItems(observableList);
            fxPrixInitial.setCellValueFactory(new PropertyValueFactory<>("prix_initial"));
            fxPrixFinal.setCellValueFactory(new PropertyValueFactory<>("prix_final"));
            fxNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            fxDateF.setCellValueFactory(new PropertyValueFactory<>("date_cloture"));
            fxDateD.setCellValueFactory(new PropertyValueFactory<>("date_lancement"));
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML

    public void AfficherEncher(javafx.event.ActionEvent actionEvent) {
        Auction selectedItem = fxTableViw.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/EncherDetail.fxml"));
                Parent root = loader.load();
                Scene scene =fxPrixFinal.getTableView().getScene();
                scene.setRoot(root);
                EncherDetailController encheredetails = loader.getController();

                encheredetails.initData(selectedItem);

            } catch (IOException e) {
                e.getMessage();
            }
        }
    }
    @FXML

    public void AjouterEncher(javafx.event.ActionEvent actionEvent)throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AjouterAuction.fxml"));
        Parent root = loader.load();
        Scene scene =fxPrixFinal.getTableView().getScene();
        scene.setRoot(root);
    }

    @FXML

    public void supprimerButton(javafx.event.ActionEvent actionEvent) {
        Auction selectedItem = fxTableViw.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int id_auction = selectedItem.getId();
            try {
                serviceAuction.supprimer_auction(id_auction);

                ObservableList<Auction> userList = fxTableViw.getItems();
                userList.remove(selectedItem);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML

    public void modifier_enchere(javafx.event.ActionEvent actionEvent) {
        Auction selectedItem = fxTableViw.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier.fxml"));
                Parent editUserRoot = loader.load();

                ModifierController modifier = loader.getController();

                modifier.initData(selectedItem);

                Scene scene = fxPrixInitial.getTableView().getScene();

                scene.setRoot(editUserRoot);

                ObservableList<Auction> userList = fxTableViw.getItems();
                userList.remove(selectedItem);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
