package controllers;
import entities.Auction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.ServiceAuction;
import services.ServiceUser;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
public class AdminAuction {
    @FXML
    private Label Num_Arts;

    @FXML
    private Label Num_Auction;

    @FXML
    private Label Num_Events;

    @FXML
    private Label Num_Messages;

    @FXML
    private Label Num_Forum;

    @FXML
    private Label Num_Users;

    @FXML
    private ImageView logout;
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


    ServiceUser serviceUser = new ServiceUser();

    public void initialize() throws SQLException {
        Num_Users.setText(String.valueOf(serviceUser.CountUsers()));
        System.out.println("Welcome Auction");

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
        try{
            Parent root = loadEnchre();
            fxPrixFinal.getTableView().getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Parent loadEnchre() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EncherDetail.fxml"));
        EncherDetailController controller = new EncherDetailController();
        loader.setController(controller);
        controller.initData(fxTableViw.getSelectionModel().getSelectedItem());
        Parent root = loader.load();
        return root;
    }

    @FXML
    public void supprimerButton(javafx.event.ActionEvent actionEvent) {
        Auction selectedItem = fxTableViw.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int id_auction = selectedItem.getId();
            try {
                serviceAuction.supprimer_auction(id_auction);
                ObservableList<Auction> AuctionList = fxTableViw.getItems();
                AuctionList.remove(selectedItem);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void Go_To_Auction(MouseEvent event) {

    }

    @FXML
    void Go_To_Event(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Event.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
    }



    @FXML
    void Go_To_Forum(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Forum.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Messages(MouseEvent event)throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Message.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Product(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Produit.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    @FXML
    void Go_To_User(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/AdminUI.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void logout(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();}
}
