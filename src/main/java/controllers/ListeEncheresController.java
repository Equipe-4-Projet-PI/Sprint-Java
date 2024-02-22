package controllers;

import Services.ServiceAuction;
import entities.Auction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListeEncheresController implements Initializable {

    @FXML
    private HBox enchereLayout;

    ServiceAuction serviceAuction = new ServiceAuction();


    List<Auction> auctionList;

    {
        try {
            auctionList = new ArrayList<>(serviceAuction.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

       /* try {
            for (int i = 0; i < auctionList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Encher.fxml"));
                HBox encherBox = fxmlLoader.load();
                EnchereController enchereController = fxmlLoader.getController();
                enchereController.setData(auctionList.get(i));
                enchereLayout.getChildren().add(encherBox);
            }
        }catch (IOException e) {
                e.getMessage();
            }*/
        }


}

/*
* hat scrollpane aataha taille li yheb aalih
* hat fi westha Hbox w hallou ala toulha
* aatahom style transparent
* w aatahom fx:id sammeh cardlayout
* khdhe l attribut w hatou fel controller
* w sna3 file fxml sammeh card.fxml
* w hatlou controller sameh cardcontroller
* w bde yekhdem fih l exemple mtaa l book mtee3ou
* bde yraka7 fih
*Sna3 new pacakeges w sameh models
* w sna3 fih class jdida esmha book
* w l book edheka hat fih les atribut li yest7a9hom
* w aamalhom l getter w setter mteehom
* w mbaaed rjaa l scene builder
* w aata l kol haja fel interface fx:id
* w rjaa lel cardController w hat les attribut m sample
* w bde yetrety fehom ghady
* aamal methode sameh seData()
* heya li bech yetsabou feha les valeurs edhoukom mtaa les attribut li jebnehom
* tawa rjaa lel controler Controller li howa fih l methode initialize
* w li howa fih l cardLayout
* w sna3 fih methode traja3 liste mtaa l books li hachtou howa bech yaffichehom
* snaa instance men book w bde ysobelha fi des valeurs
* w zed l book edheka lel liste
* mche lel initialize methode w snaa array liste li bech yhot feha l books
* w hal boucle for
* w loada l page card.fxl
* w 3ayet l cardController
* w aamal appel lel methode setData
* w aamal getChildren.add(aka l cardBox)
*  behy tawa awed hal scrollpane okhra
* hattelha girdpane w ataha fxid bookcontainer
* w hazou lel Controller w tawa hall fxml jdid sameh book
* w controller jdid sameh BookController
*  bde yraka7 fel book.fxml
* tansech l vBox taht taswira kif tabda theb taaml desc
* aata leli hachtou bih des fxid
* haz les attribut w hathom fi bookController
* w aamal feha methode setData
* kel aada bde yhot fl les attribut li jebhom des valeurs
* rjaa l controller
* aamal methode books traja3 liste mtaa l books
* dkhal lel methode initialize w hatha aka liste fi liste
* w aamal boucle for w kel aada kaad yloady fel page book.fxml
* 31 minute
* hne hat kadeh men colonne howa yhebha tafficha makenech 9allou ahbet l star w aawed star jdid
* rja3 lel scene builder mche l partie controller
* w aata l controller Class
*
*
* */
