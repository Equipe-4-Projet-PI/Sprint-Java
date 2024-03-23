package controllers.Kenza_Controllers;

import controllers.Ali_Controllers.ProductController;
import controllers.Yasser_Controllers.AfficherForumMembreController;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.ServiceAuction;
import entities.Auction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;

public class AjouterAuctionController implements Initializable {

    public ImageView bell;
    public ImageView usericon;
    public Label nav_name;
    public Button inscrire;
    public ImageView logouticon;
    Preferences preferences = Preferences.userNodeForPackage(AjouterAuctionController.class);
    String savedUsername = preferences.get("username", null);
    String savedPassword = preferences.get("Password", null);
    ServiceAuction serviceAuction = new ServiceAuction();


    @FXML
    private TextField tf_nomAuction;
    @FXML
    private ChoiceBox<String> tf_produit;
    @FXML
    private Spinner<Integer> tf_prix_initial;
    @FXML
    private DatePicker tf_date;

    @FXML
    private DatePicker tf_dateC;
    private User userlogged;
    int id_Artist ;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        inscrire.setVisible(false);
        bell.setVisible(true);
        usericon.setVisible(true);
        logouticon.setVisible(true);
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
                    serviceAuction.ajouter(new Auction(tf_nomAuction.getText(), dateCloture, dateLancement, Integer.parseInt(tf_prix_initial.getValue().toString()), productId));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Enchère ajoutée");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/artistEnchers.fxml"));
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
        // Récupérer les valeurs des DatePicker en tant que LocalDate
        LocalDate dateDebut = tfDate.getValue();
        LocalDate dateFin = tfDateC.getValue();

        // Obtenir la date actuelle
        LocalDate dateActuelle = LocalDate.now();

        // Vérifier si tfDate est supérieur à la date actuelle
        if (dateDebut != null && (dateDebut.isAfter(dateActuelle) || dateActuelle.isEqual(dateDebut))) {
            // Vérifier si tfDateC est supérieur à tfDate
            if (dateFin != null && dateFin.isAfter(dateDebut)) {
                return true;  // Les dates sont valides
            }
        }

        // Les dates ne satisfont pas aux conditions
        return false;
    }


    @FXML
    public void retourner(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/artistEnchers.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ArtistEnchersController artistEnchersController = loader.getController();
        artistEnchersController.setuser(userlogged);

    }

    public void setuser(User user) {


            id_Artist = user.getId_User();
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

            List<String> productNames = loadProductValuesFromDatabase(id_Artist);

            ObservableList<String> products = FXCollections.observableArrayList(productNames);
            tf_produit.setItems(products);

            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory
                    .IntegerSpinnerValueFactory(1, 1000, 1);
            tf_prix_initial.setValueFactory(valueFactory);
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

    public void Go_To_Auction(ActionEvent actionEvent) throws IOException {
        if (userlogged != null){
            if (userlogged.getRole().equals("Member")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/Enchers.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = nav_name.getScene();
                scene.setRoot(loginSuccessRoot);
                EnchersController enchersController = loader.getController();
                enchersController.setuser(userlogged);
            }
            else if
            (userlogged.getRole().equals("Artist")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/artistEnchers.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = nav_name.getScene();
                scene.setRoot(loginSuccessRoot);
                ArtistEnchersController artistEnchersController = loader.getController();
                artistEnchersController.setuser(userlogged);


            }}

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/Enchers.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        EnchersController enchersController = loader.getController();
        enchersController.setuser(userlogged);

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


