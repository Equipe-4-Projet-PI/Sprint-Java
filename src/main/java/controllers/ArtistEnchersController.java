package controllers;

import Services.ServiceAuction;
import entities.Auction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ArtistEnchersController implements Initializable {

    @FXML
    private TextField id_search;
    int id_artist =3;
    ServiceAuction serviceAuction = new ServiceAuction();

    @FXML
    private GridPane enchereContainer;

    @FXML
    private HBox HboxA;

    private List<Auction> mesEnchers;
    private List<Auction> autresEncheres;

    private static ArtistEnchersController instance;


    public ArtistEnchersController() {
        instance = this;
    }

    public static ArtistEnchersController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mesEnchers = new ArrayList<>(getMesEnchers());
        autresEncheres = new ArrayList<>(autresEncheres());
        int column = 0;
        int row = 1;

        System.out.println("the size of data " + mesEnchers.size());
        try {
            for (int i = 0; i < mesEnchers.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/EncherArtist.fxml"));
                HBox encherBox = fxmlLoader.load();
                EncherArtistController encherArtistController = fxmlLoader.getController();
                encherArtistController.initData(mesEnchers.get(i));
                HboxA.getChildren().add(encherBox);
            }
            for (Auction auction : autresEncheres) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/EnchereContainer.fxml"));
                VBox encherBox = fxmlLoader.load();
                EnchereContainerController enchereContainerController = fxmlLoader.getController();
                enchereContainerController.setIdArtist(id_artist);
                enchereContainerController.initData(auction);

                if (column == 5) {
                    column = 0;
                    row++;
                }
                enchereContainer.add(encherBox, column++, row);
                GridPane.setMargin(encherBox, new Insets(10));
            }
        } catch (IOException e) {
            System.out.println("here");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        HboxA.addEventHandler(AuctionEvent.AUCTION_DELETED, event -> {
            refreshData();
        });
        instance = this;

    }

    private List<Auction> autresEncheres() {
        try {
            return serviceAuction.getAutresEncheres(id_artist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshData() {
        HboxA.getChildren().clear();
        initialize(null, null);
    }

    private List<Auction> getMesEnchers() {
        try {
            return serviceAuction.getMesEnchers(id_artist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void Ajouter(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AjouterAuction.fxml"));
        Parent root = loader.load();
        Scene scene = HboxA.getScene();
        scene.setRoot(root);
    }

    @FXML
    public void searchForAuction(MouseEvent mouseEvent) {
        try {
            enchereContainer.getChildren().clear();
            String searchText = id_search.getText();
            ObservableList<Auction> observableList = FXCollections.observableList(serviceAuction.afficher());

            int column = 0;
            int row = 1;
            if(searchText != "")
            {
                List<Auction> filteredList = observableList.stream()
                        .filter(e -> e.getNom().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());

                ObservableList<Auction> newList = FXCollections.observableList(filteredList);
                for (Auction auction : newList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/EnchereContainer.fxml"));
                    VBox encherBox = fxmlLoader.load();
                    EnchereContainerController enchereContainerController = fxmlLoader.getController();
                    enchereContainerController.initData(auction);

                    if (column == 6) {
                        column = 0;
                        row++;
                    }
                    enchereContainer.add(encherBox, column++, row);
                    GridPane.setMargin(encherBox, new Insets(10));

                }
            }else{
            autresEncheres = new ArrayList<>(autresEncheres());
            for (Auction auction : autresEncheres) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/EnchereContainer.fxml"));
                VBox encherBox = fxmlLoader.load();
                EnchereContainerController enchereContainerController = fxmlLoader.getController();
                enchereContainerController.initData(auction);

                if (column == 6) {
                    column = 0;
                    row++;
                }
                enchereContainer.add(encherBox, column++, row);
                GridPane.setMargin(encherBox, new Insets(10));


            }
            }
        }catch (IOException | SQLException e)
        {
            e.printStackTrace();
        }
    }

    //aandi ghalta kif nhot like w mbaaed naaml partciper yetzedli ligne jdid fi 3oudh ma yaaml modifier
}