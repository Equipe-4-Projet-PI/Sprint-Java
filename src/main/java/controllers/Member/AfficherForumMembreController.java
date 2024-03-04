package controllers.Member;

import controllers.*;
import entities.ForumEntity;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceForumF;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class AfficherForumMembreController {

    public ImageView bell;
    public ImageView usericon;
    public Label nav_name;
    public Button inscrire;
    public ImageView logouticon;
    ServiceForumF SF = new ServiceForumF();
    @FXML
    private Button searchButt_id;
    Preferences preferences = Preferences.userNodeForPackage(AfficherForumMembreController.class);
    String savedUsername = preferences.get("username", null);
    String savedPassword = preferences.get("Password", null);
    @FXML
    private TextField searchbar_id;
    private User userlogged;
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
                    cardController.setData(f,userlogged);
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



    public void initUser(User user) {

        System.out.println(user);
        if(user == null){
            inscrire.setVisible(true);
            bell.setVisible(false);
            usericon.setVisible(false);
            logouticon.setVisible(false);
            System.out.println("el user mafamech");
            userlogged = new User();
            userlogged.setId_User(2);

            try{
                ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());
                for (int i = 0; i < observableList.size(); i++) {
                    FXMLLoader fxmlLoader= new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ForumPages/Member/ForumTemplateMembre.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    ForumTemplateMembreController cardController = fxmlLoader.getController();
                    cardController.setData(observableList.get(i),userlogged);
                    //   cardController.initData(userlogged);
                    idVbox.getChildren().add(cardBox);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            searchButt_id.setGraphic(Lojo);

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

            try{
                ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());
                for (int i = 0; i < observableList.size(); i++) {
                    FXMLLoader fxmlLoader= new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ForumPages/Member/ForumTemplateMembre.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    ForumTemplateMembreController cardController = fxmlLoader.getController();
                    cardController.setData(observableList.get(i),userlogged);
                    //   cardController.initData(userlogged);
                    idVbox.getChildren().add(cardBox);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            searchButt_id.setGraphic(Lojo);

        }



    }
//    @FXML
//   public void initialize()
//
//    {


//    }
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
    void CreateForum(ActionEvent event) throws IOException {
        if (userlogged.getId_User() ==2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vous n’avez pas de compte");
            alert.setContentText("Crée un account pour Creer un Forum");
            alert.showAndWait();
        }
        else {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AddForumMembre.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        System.out.println(userlogged);
        AddForumMembreController addForumMembreController = loader.getController();
        addForumMembreController.initData(userlogged);}
    }

    private Parent loadRootLayoutForForum() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AddForumMembre.fxml"));

        Parent root = loader.load();
        return root;
    }



    public void Go_To_Home(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        System.out.println(userlogged);
        HomeController homeController = loader.getController();
        homeController.initData(userlogged);
    }

    public void Go_To_Product(ActionEvent actionEvent)  throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Product.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProductController productController = loader.getController();
        productController.initUser(userlogged);
    }

    public void Go_To_Auction(ActionEvent actionEvent) throws IOException {
        if (userlogged != null){
            if (userlogged.getRole().equals("Member")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Enchers.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = nav_name.getScene();
                scene.setRoot(loginSuccessRoot);
                EnchersController enchersController = loader.getController();
                enchersController.setuser(userlogged);
            }
            else if
            (userlogged.getRole().equals("Artist")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/artistEnchers.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = nav_name.getScene();
                scene.setRoot(loginSuccessRoot);
                ArtistEnchersController artistEnchersController = loader.getController();
                artistEnchersController.setuser(userlogged);


            }}

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Enchers.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        EnchersController enchersController = loader.getController();
        enchersController.setuser(userlogged);

    }
    public void Go_To_Forum(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AfficherForumMembre.fxml"));
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginSuccess.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);

        LoginSuccess loginSuccess = loader.getController();
        loginSuccess.initData(userlogged);
    }

    public void sinscrire(ActionEvent actionEvent) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    public void Logout(MouseEvent mouseEvent)throws IOException {
        preferences.remove("username");
        preferences.remove("Password");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();
    }


}
