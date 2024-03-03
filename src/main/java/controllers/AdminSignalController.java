package controllers ;
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
    private TableColumn<Disscussion, String> receiver;

    @FXML
    private TableColumn<Disscussion, String> sender;

    @FXML
    private TableColumn<Disscussion, String> signal;

    @FXML
    private TableView<Disscussion> tableSignal;

    @FXML
    @SuppressWarnings("unchecked") // Supprime le warning relatif à la conversion en TableView<Disscussion>
    public void initialize() {
        try {
            ObservableList<Disscussion> observableList = FXCollections.observableList(serviceDisscussion.afficherSig());
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
