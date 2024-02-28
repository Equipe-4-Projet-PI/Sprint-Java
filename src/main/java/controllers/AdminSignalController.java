package controllers;//package controllers;
//
//import entities.Disscussion;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import services.ServiceDisscussion;
//
//import java.awt.event.ActionEvent;
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class AdminSignalController {
//
//    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
//    @FXML
//    private TableColumn<Disscussion, Integer> discussion;
//
//    @FXML
//    private TableColumn<Disscussion, Integer> receiver;
//
//    @FXML
//    private TableColumn<Disscussion, Integer> sender;
//
//    @FXML
//    private TableColumn<Disscussion, Integer> signal;
//
//    @FXML
//    private TableView<Disscussion> tableSignal;
//
////    @FXML
////    public void AfficherSignal(){
////        try {
////            Parent root = FXMLLoader.load(getClass().getResource("/Admin_Signal.fxml"));
////            Scene scene = new Scene(root) ;
////            stage.setScene(scene);
////            stage.setTitle("Envoi Message");
////            stage.show();
////        } catch (IOException e) {
////            System.out.println(e.getMessage());
////        }
////    }
//    @FXML
//    void Initialize(){
//        try {
//            ObservableList<Disscussion> observableList = FXCollections.observableList(serviceDisscussion.afficher()) ;
//            tableSignal.setItems(observableList);
//            discussion.setCellValueFactory(new PropertyValueFactory<>("idDis"));
//            sender.setCellValueFactory(new PropertyValueFactory<>("idSender"));
//            receiver.setCellValueFactory(new PropertyValueFactory<>("idReceiver"));
//            signal.setCellValueFactory(new PropertyValueFactory<>("Signal"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//}
import entities.Disscussion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceDisscussion;

import java.sql.SQLException;

public class AdminSignalController {

    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();

    @FXML
    private TableColumn<Disscussion, Integer> discussion;

    @FXML
    private TableColumn<Disscussion, Integer> receiver;

    @FXML
    private TableColumn<Disscussion, Integer> sender;

    @FXML
    private TableColumn<Disscussion, Integer> signal;

    @FXML
    private TableView<Disscussion> tableSignal;

    @FXML
    @SuppressWarnings("unchecked") // Supprime le warning relatif à la conversion en TableView<Disscussion>
    public void initialize() {
        try {
            ObservableList<Disscussion> observableList = FXCollections.observableList(serviceDisscussion.afficher());
            tableSignal.setItems(observableList);
            discussion.setCellValueFactory(new PropertyValueFactory<>("idDis"));
            sender.setCellValueFactory(new PropertyValueFactory<>("idSender"));
            receiver.setCellValueFactory(new PropertyValueFactory<>("idReceiver"));
            signal.setCellValueFactory(new PropertyValueFactory<>("Signal"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
