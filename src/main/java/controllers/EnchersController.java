package controllers;

import Services.ServiceAuction;
import entities.Auction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EnchersController implements Initializable {
    ServiceAuction serviceAuction = new ServiceAuction();


    @FXML
    private HBox hboxFX;

    @FXML
    private ScrollPane scrollPaneFX;
    @FXML
    private TextField id_searcj;

    private List<Auction> tousEncheres;

    int idUser = 6;
    @FXML
    void searchForAuction(MouseEvent event) {
        try {
            hboxFX.getChildren().clear();
            String searchText = id_searcj.getText().trim();
            ObservableList<Auction> observableList = FXCollections.observableList(serviceAuction.afficher());

            int column = 0;
            int row = 1;

            if (!searchText.isEmpty()) {
                List<Auction> filteredList = observableList.stream()
                        .filter(e -> e.getNom().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());

                ObservableList<Auction> newList = FXCollections.observableList(filteredList);

                for (Auction auction : newList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/UnEncher.fxml"));
                    HBox encherBox = fxmlLoader.load();
                    UnEncherController unEncherController = fxmlLoader.getController();
                    unEncherController.initData(auction);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                    hboxFX.getChildren().add(encherBox);
                    HBox.setMargin(encherBox, new Insets(10));
                }
            } else {
                tousEncheres = new ArrayList<>(serviceAuction.afficher());
                for (Auction auction : tousEncheres) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/UnEncher.fxml"));
                    HBox encherBox = fxmlLoader.load();
                    UnEncherController unEncherController = fxmlLoader.getController();
                    unEncherController.initData(auction);
                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                    hboxFX.getChildren().add(encherBox);
                    GridPane.setMargin(encherBox, new Insets(10));
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();

        }
    }
    private List<Auction> TousEncheres() {
        try {
            return serviceAuction.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Auction> recentlyAdded = new ArrayList<>(recentlyAdded());
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("the size of data "+recentlyAdded.size());
        try {
            VBox mainVBox = new VBox();
            hboxFX.getChildren().add(mainVBox);
            HBox currentHBox = new HBox();
            currentHBox.setSpacing(10);
            for (int i = 0 ; i<recentlyAdded.size();i++){
                if(i> 0 && i % 3 == 0){
                    mainVBox.getChildren().add(currentHBox);
                    currentHBox = new HBox();
                    currentHBox.setSpacing(10);
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/UnEncher.fxml"));
                HBox encherBox = fxmlLoader.load();
                UnEncherController unEncherController = fxmlLoader.getController();
//                System.out.println(recentlyAdded.get(i));
                unEncherController.setIdArtist(idUser);
                unEncherController.initData(recentlyAdded.get(i));
                currentHBox.getChildren().add(encherBox);
            }
            mainVBox.getChildren().add(currentHBox);
        } catch (IOException e) {
            System.out.println("here");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void refreshData() {
        hboxFX.getChildren().clear();
        initialize(null, null);
    }
    private List<Auction> recentlyAdded(){
        try {
            return serviceAuction.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    
}
