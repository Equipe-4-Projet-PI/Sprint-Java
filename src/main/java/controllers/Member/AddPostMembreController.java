package controllers.Member;
import entities.ForumEntity;
import entities.PostEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServicePost;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddPostMembreController {

    @FXML
    private Button forumPage_id;

    @FXML
    private Label forum_name_id;

    @FXML
    private VBox id_vbox_posts;

    private ForumEntity current_forum;

    //SERVICE POST
    ServicePost sp = new ServicePost();
    ServiceUser su = new ServiceUser();

    public void setData(ForumEntity forumEntity)
    {

        this.current_forum = forumEntity;

    }
    private void SetDataAgain()
    {
        this.forum_name_id.setText(current_forum.getTitle());
    }

    @FXML
    void initialize()
    {
        try {
            //SET FORUM NAME
            SetDataAgain();
            DoMath();
            DoMoreMath();
            box.getItems().setAll(choices);
            box.setValue("Oldest to Newest");

            ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());

            // Filter the list based on the search text
            List<PostEntity> filteredList = observableList.stream()
                    .filter(e -> e.getId_forum() == current_forum.getId_forum())
                    .collect(Collectors.toList());

            // Load and display filtered data
            for (PostEntity f : filteredList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForumPages/Member/PostTemplateMembre.fxml"));
                HBox cardBox = fxmlLoader.load();
                PostTemplateMembreController cardController = fxmlLoader.getController();
                cardController.setData(f);
                id_vbox_posts.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void GotoforumPage(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Member/AfficherForumMembre.fxml"));
            forumPage_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    ///ADD BUTTON LOGIC

    @FXML
    private Button Add_Post_Butt_Id;
    @FXML
    void AddForum(ActionEvent event) {
        try{
            Parent root = loadRootLayoutForForum();
            Add_Post_Butt_Id.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private Parent loadRootLayoutForForum() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/NewPostPageMembre.fxml"));
        NewPostPageMembreController controller = new NewPostPageMembreController();
        loader.setController(controller);
        controller.setData(current_forum);
        Parent root = loader.load();
        return root;
    }

    //Statistics

    @FXML
    private Label top_likes_is;

    @FXML
    private Label top_post_user_id;

    private void DoMath() throws SQLException {
        ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());

        // Filter the list based on the search text
        List<PostEntity> filteredList = observableList.stream()
                .filter(e -> e.getId_forum() == current_forum.getId_forum())
                .collect(Collectors.toList());
        Optional<PostEntity> mostLikedPost = filteredList.stream()
                .max(Comparator.comparingInt(PostEntity::getLike_num));
        if (mostLikedPost.isPresent())
        {
            top_likes_is.setText(""+mostLikedPost.get().getLike_num());
            top_post_user_id.setText(su.getbyid(mostLikedPost.get().getId_user()).getUsername());
        }else {
            top_likes_is.setText("No One :(");
            top_post_user_id.setText("No One :(");
        }


    }

    @FXML
    private Label likes_tot_id;

    @FXML
    private Label posts_num_id;

    private void DoMoreMath() throws SQLException {
        ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());

        // Filter the list based on the search text
        List<PostEntity> filteredList = observableList.stream()
                .filter(e -> e.getId_forum() == current_forum.getId_forum())
                .collect(Collectors.toList());

        int totalLikes = filteredList.stream()
                .mapToInt(PostEntity::getLike_num)
                .sum();
        long totalPosts = filteredList.size();

        likes_tot_id.setText(""+totalLikes);
        posts_num_id.setText(""+totalPosts);
    }

    /*======= ORDERING THE LIST =======*/
    @FXML
    private Button OrderButt;
    @FXML
    void OrderByLikeNumber(ActionEvent event) {
        try{
            // Retrieve the list of posts
            ObservableList<PostEntity> observableList = FXCollections.observableList(sp.afficher());

            // Filter the list based on the current forum ID
            List<PostEntity> filteredList = observableList.stream()
                    .filter(e -> e.getId_forum() == current_forum.getId_forum())
                    .collect(Collectors.toList());

            // Order the posts by their rank number (likes number) in descending order
//            List<PostEntity> orderedPosts = filteredList.stream()
//                    .sorted(Comparator.comparingInt(PostEntity::getLike_num).reversed())
//                    .collect(Collectors.toList());


            String c = box.getValue().toLowerCase();
            Comparator<PostEntity> comparator = null;

            switch (c) {
                case "id":
                    comparator = Comparator.comparingInt(PostEntity::getId_post);
                    break;
                case "title":
                    comparator = Comparator.comparing(PostEntity::getTitle);
                    break;
                case "newest to oldest":
                    comparator = Comparator.comparing(PostEntity::getTime).reversed();
                    break;
                case "oldest to newest":
                    comparator = Comparator.comparing(PostEntity::getTime);
                    break;
                case "likes":
                    comparator = Comparator.comparing(PostEntity::getLike_num).reversed();
                    break;
                default:
                    break;
            }

            if (comparator != null) {
                // Sort the filteredList using the selected comparator
                filteredList.sort(comparator);
            }

            // Clear Dat VBox Mah Man
            id_vbox_posts.getChildren().clear();
            // Load and display filtered data
            for (PostEntity f : filteredList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ForumPages/Member/PostTemplateMembre.fxml"));
                HBox cardBox = fxmlLoader.load();
                PostTemplateMembreController cardController = fxmlLoader.getController();
                cardController.setData(f);
                id_vbox_posts.getChildren().add(cardBox);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private ChoiceBox<String> box;
    private String[] choices = {"Id","Title","Oldest to Newest","Newest to oldest","Likes"};

}
