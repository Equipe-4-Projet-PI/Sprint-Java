package controllers;

import entities.ForumEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceForum;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AfficherForumController {

    ServiceForum SF = new ServiceForum();
    @FXML
    private Button searchButt_id;

    @FXML
    private TextField searchbar_id;

    @FXML
    void SearchForForum(ActionEvent event) {
            try {
                idVbox.getChildren().clear(); // Clear previous content

                String searchText = searchbar_id.getText(); // Assuming id_search is the TextField where the user enters the search text

                ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());

                // Filter the list based on the search text
                List<ForumEntity> filteredList = observableList.stream()
                        .filter(e -> e.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());

                // Load and display filtered data
                for (ForumEntity f : filteredList) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForumTemplate.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    ForumTemplateController cardController = fxmlLoader.getController();
                    cardController.setData(f);
                    idVbox.getChildren().add(cardBox);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

        @FXML
    private VBox idVbox;

    @FXML
    void initialize()
    {
        try{
            ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());
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
        searchButt_id.setGraphic(Lojo);
    }
    @FXML
    private ImageView Lojo;

    @FXML
    private Button forumPage_id;
    @FXML
    void GotoforumPage(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/AfficherForum.fxml"));
            forumPage_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
