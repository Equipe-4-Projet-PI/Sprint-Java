package controllers.Member;

import entities.ForumEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceForum;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AfficherForumMembreController {

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
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForumPages/Member/ForumTemplateMembre.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    ForumTemplateMembreController cardController = fxmlLoader.getController();
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
                fxmlLoader.setLocation(getClass().getResource("/ForumPages/Member/ForumTemplateMembre.fxml"));
                HBox cardBox = fxmlLoader.load();
                ForumTemplateMembreController cardController = fxmlLoader.getController();
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
            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Member/AfficherForumMembre.fxml"));
            forumPage_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    //Creating Forums
    @FXML
    private Button Create_forum_butt_id;

    @FXML
    void CreateForum(ActionEvent event) {
        try{
            Parent root = loadRootLayoutForForum();
            Create_forum_butt_id.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Parent loadRootLayoutForForum() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AddForumMembre.fxml"));

        Parent root = loader.load();
        return root;
    }
}
