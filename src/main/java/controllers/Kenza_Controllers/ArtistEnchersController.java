package controllers.Kenza_Controllers;

import controllers.Ali_Controllers.ProductController;
import controllers.Yasser_Controllers.AfficherForumMembreController;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import services.ServiceAuction;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;


import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class ArtistEnchersController implements Initializable {
    Preferences preferences = Preferences.userNodeForPackage(ArtistEnchersController.class);
    String savedUsername = preferences.get("username", null);
    String savedPassword = preferences.get("Password", null);

    private User userlogged;
    public ImageView logouticon;
    public Button inscrire;
    public Label nav_name;
    public ImageView usericon;
    public ImageView bell;
    @FXML
    private TextField id_search;
    int id_artist =1;
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
                fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/EncherArtist.fxml"));
                HBox encherBox = fxmlLoader.load();
                EncherArtistController encherArtistController = fxmlLoader.getController();

                encherArtistController.initData(mesEnchers.get(i));

                HboxA.getChildren().add(encherBox);
            }
            for (Auction auction : autresEncheres) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/EnchereContainer.fxml"));
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
        loader.setLocation(getClass().getResource("/Interfaces/AuctionPages/AjouterAuction.fxml"));
        Parent root = loader.load();
        Scene scene = HboxA.getScene();
        scene.setRoot(root);
        AjouterAuctionController ajouterAuctionController = loader.getController();
        ajouterAuctionController.setuser(userlogged);
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
                    fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/EnchereContainer.fxml"));
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
                fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/EnchereContainer.fxml"));
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

    public void setuser(User user) {

        if(user == null || user.getId_User()==2){
            inscrire.setVisible(true);
            bell.setVisible(false);
            usericon.setVisible(false);
            logouticon.setVisible(false);
            System.out.println("el user mafamech");
            userlogged = null ;

        }



        else {


            nav_name.setText(user.getUsername());
            inscrire.setVisible(false);
            bell.setVisible(true);
            usericon.setVisible(true);
            logouticon.setVisible(true);


            userlogged = new User();
            userlogged.setGender(user.getGender());
            userlogged.setDOB(user.getDOB());
            userlogged.setPhone(user.getPhone());
            userlogged.setAdress(user.getAdress());
            userlogged.setUsername(user.getUsername());
            userlogged.setEmail(user.getEmail());
            userlogged.setFirstName(user.getFirstName());
            userlogged.setPassword(user.getPassword());
            userlogged.setLastName(user.getLastName());
            userlogged.setId_User(user.getId_User());
            userlogged.setRole(user.getRole());
            userlogged.setImageURL(user.getImageURL());


            mesEnchers = new ArrayList<>(getMesEnchers());
            autresEncheres = new ArrayList<>(autresEncheres());
            int column = 0;
            int row = 1;

            System.out.println("the size of data " + mesEnchers.size());
            try {
                for (int i = 0; i < mesEnchers.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/EncherArtist.fxml"));
                    HBox encherBox = fxmlLoader.load();
                    EncherArtistController encherArtistController = fxmlLoader.getController();
                    encherArtistController.initData(mesEnchers.get(i));
                    HboxA.getChildren().add(encherBox);
                }
                for (Auction auction : autresEncheres) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/EnchereContainer.fxml"));
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



    }
    public void Go_To_Home(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Home.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        System.out.println(userlogged);
        HomeController homeController = loader.getController();
        homeController.initData(userlogged);
    }

    public void Go_To_Product(ActionEvent actionEvent)  throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ProductPages/Product.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProductController productController = loader.getController();
        productController.initUser(userlogged);
    }

    public void Go_To_Auction(ActionEvent actionEvent) {
    }

    public void Go_To_Forum(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AfficherForumMembre.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        AfficherForumMembreController afficherForumMembreController = loader.getController();
        afficherForumMembreController.initUser(userlogged);

    }

    public void Go_To_Event(ActionEvent actionEvent) {
    }

    public void Go_To_Message(ActionEvent actionEvent) {
    }

    public void ProfileVisit(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/LoginSuccess.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);

        LoginSuccess loginSuccess = loader.getController();
        loginSuccess.initData(userlogged);
    }

    public void sinscrire(ActionEvent actionEvent) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    public void Logout(MouseEvent mouseEvent)throws IOException {
        preferences.remove("username");
        preferences.remove("Password");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();
    }
}