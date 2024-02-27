package controllers;

import Services.ServiceAuction;
import com.sun.javafx.scene.control.VirtualScrollBar;
import entities.Auction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.security.Provider;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EnchersController implements Initializable {
    ServiceAuction serviceAuction = new ServiceAuction();


    @FXML
    private HBox hboxFX;

    @FXML
    private ScrollPane scrollPaneFX;

    List<Auction> recentlyAdded;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recentlyAdded =new ArrayList<>(recentlyAdded());
        System.out.println("the size of data "+recentlyAdded.size());
        try {
            VBox mainVBox = new VBox();
            hboxFX.getChildren().add(mainVBox);
            HBox currentHBox = new HBox();
            currentHBox.setSpacing(50);
            for (int i = 0 ; i<recentlyAdded.size();i++){
                if(i> 0 && i % 3 == 0){
                    mainVBox.getChildren().add(currentHBox);
                    currentHBox = new HBox();
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/UnEncher.fxml"));
                HBox encherBox = fxmlLoader.load();
                UnEncherController unEncherController = fxmlLoader.getController();
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
