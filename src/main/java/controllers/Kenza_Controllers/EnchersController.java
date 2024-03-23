package controllers.Kenza_Controllers;

import controllers.Ali_Controllers.ProductController;
import controllers.Yasser_Controllers.AfficherForumMembreController;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.User;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import controllers.Montaha_Controllers.AfficherEventView;
import services.ServiceAuction;
import entities.Auction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
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

public class EnchersController implements Initializable {

    Preferences preferences = Preferences.userNodeForPackage(EnchersController.class);
    String savedUsername = preferences.get("username", null);
    String savedPassword = preferences.get("Password", null);
    public ImageView logouticon;
    public Button inscrire;
    public Label nav_name;
    public ImageView usericon;
    public ImageView bell;
    ServiceAuction serviceAuction = new ServiceAuction();
    private User userlogged;

    @FXML
    private ImageView imageAfficherEncheres;


    @FXML
    private HBox hboxFX;

    @FXML
    private ScrollPane scrollPaneFX;
    @FXML
    private TextField id_searcj;

    private List<Auction> tousEncheres;

    int idUser  ;
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
                    fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/UnEncher.fxml"));
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
                    fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/UnEncher.fxml"));
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
        System.out.println(userlogged);
        System.out.println("eluuuserrr loged ??");

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
                fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/UnEncher.fxml"));
                HBox encherBox = fxmlLoader.load();
                UnEncherController unEncherController = fxmlLoader.getController();
//                System.out.println(recentlyAdded.get(i));
                unEncherController.setIdArtist(userlogged);
                unEncherController.initData(recentlyAdded.get(i));
                unEncherController.setID(idUser);
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

    @FXML
    void filtrerLive(MouseEvent event) {
        filterAndDisplayAuctions();
    }

    private void filterAndDisplayAuctions() {
        try {
            hboxFX.getChildren().clear();
            ObservableList<Auction> observableList = FXCollections.observableList(serviceAuction.afficher());

            int column = 0;
            int row = 1;

            List<Auction> filteredList = observableList.stream()
                    .filter(e -> serviceAuction.getSituation(e) == 0)
                    .collect(Collectors.toList());

            ObservableList<Auction> newList = FXCollections.observableList(filteredList);

            for (Auction auction : newList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/UnEncher.fxml"));
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
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tousEnchers(MouseEvent event) {
        refreshData();
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
            idUser = user.getId_User();

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
                    fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/UnEncher.fxml"));
                    HBox encherBox = fxmlLoader.load();
                    UnEncherController unEncherController = fxmlLoader.getController();
//                System.out.println(recentlyAdded.get(i));
                    unEncherController.setIdArtist(userlogged);
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

    public void Go_To_Event(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/EventPages/afficher-event-view.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        AfficherEventView afficherEventView = loader.getController();
        afficherEventView.initData(userlogged);




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
