package controllers;

import Services.ServiceAuction;
import entities.Personne;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Test implements Initializable {

    @FXML
    private HBox encherLayout;
    ServiceAuction serviceAuction = new ServiceAuction();
    private List<Personne> listePersonne;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listePersonne = new ArrayList<>(afficherList());
        try {
            for(int i=0 ;i<listePersonne.size();i++){
                FXMLLoader fxmlLoader=new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Encher.fxml"));
                HBox encherBox = fxmlLoader.load();
                EnchereController enchereController= fxmlLoader.getController();
                enchereController.setData(listePersonne.get(i));
                encherLayout.getChildren().add(encherBox);
         }
        }catch (IOException e) {
                e.printStackTrace();
            }

    }

    private List<Personne> afficherList(){
        List<Personne> list = new ArrayList<>();
        Personne personne = new Personne(200 , "kenzaTestProduit" , new Date() , new Date());
        list.add(personne);
        return list;
    }

}
