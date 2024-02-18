package controllers;

import entities.ForumEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceForum;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class AfficherForumController {

    ServiceForum SF = new ServiceForum();
    @FXML
    private TableColumn<ForumEntity, String> col_f_desc;

    @FXML
    private TableColumn<ForumEntity, String> col_f_titre;

    @FXML
    private TableView<ForumEntity> TabForum;

    @FXML
    private TableColumn<ForumEntity, Integer> col_rep_number;

    @FXML
    private TableColumn<ForumEntity, Date> col_date;

    @FXML
    private VBox idVbox;

    @FXML
    void initialize()
    {
//        try {
//            ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());
//            TabForum.setItems(observableList);
//            col_f_titre.setCellValueFactory (new PropertyValueFactory<>("title"));
//            col_f_desc.setCellValueFactory (new PropertyValueFactory<>("description"));
//            col_rep_number.setCellValueFactory(new PropertyValueFactory<>("replies_num"));
//            col_date.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        try{
            ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher()) ;
            for (int i = 0; i < observableList.size(); i++) {
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ForumTemplate.fxml"));
                HBox cardBox = fxmlLoader.load();
                ForumTemplateController cardController = fxmlLoader.getController();
                cardController.setData(observableList.get(i));
                idVbox.getChildren().add(cardBox);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
