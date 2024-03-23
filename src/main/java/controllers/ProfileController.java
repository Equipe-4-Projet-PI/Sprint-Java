package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.time.LocalDate;
import java.io.File;
import java.net.MalformedURLException;

public class ProfileController {
    public ImageView profile_Pic;
    public Button inscrire;
    @FXML
    private Label id_Nom;

    @FXML
    private Label id_adress;

    @FXML
    private Label id_date;

    @FXML
    private Label id_email;

    @FXML
    private Label id_presnom;

    @FXML
    private Label id_tel;

    @FXML
    private Label nav_name;

    @FXML
    private Label statue_name;

    @FXML
    private Label statue_name1;

    @FXML
    private Label statue_name11;

    @FXML
    private Label statue_name12;

    @FXML
    private Label statue_name13;

    @FXML
    private Label statue_name131;

    @FXML
    private Label statue_name132;
    private User userlogged;
    @FXML
    void Details(ActionEvent event) {

    }

    @FXML
    void Edit(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsProfile.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        DetailsProfileController detailsProfileController = loader.getController();
        detailsProfileController.initData(userlogged);

    }

    @FXML
    void Gallerie(ActionEvent event)throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile_Gallerie.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProfileGallerieController profileGallerieController = loader.getController();
        profileGallerieController.initData(userlogged);
    }

    @FXML
    void Post_Blog(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginSuccess.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        LoginSuccess loginSuccess = loader.getController();
        loginSuccess.initData(userlogged);
    }


    public void initData(User user) throws MalformedURLException {
        inscrire.setVisible(false);
        id_Nom.setText(user.getFirstName());
        statue_name.setText(user.getFirstName() + " " + user.getLastName());
        nav_name.setText(user.getUsername());
        id_presnom.setText(user.getLastName());
        id_adress.setText(user.getAdress());
        id_email.setText(user.getEmail());
        id_tel.setText(String.valueOf(user.getPhone()));
        id_date.setText(String.valueOf(LocalDate.parse(user.getDOB())));

        String filePath = user.getImageURL().toString();


        filePath = filePath.replace("/", "\\");


        Image image = new Image(filePath);
        Circle clip = new Circle(profile_Pic.getFitWidth() / 2, profile_Pic.getFitHeight() / 2, Math.min(profile_Pic.getFitWidth(), profile_Pic.getFitHeight()) / 2);
        profile_Pic.setClip(clip);
        profile_Pic.setImage(image);


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

    }


    public void Go_To_Home(ActionEvent actionEvent) {
    }

    public void Go_To_Product(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Product.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProductController productController = loader.getController();
        productController.initUser(userlogged);
    }

    public void Go_To_Auction(ActionEvent actionEvent) {
    }

    public void Go_To_Forum(ActionEvent actionEvent) {
    }

    public void Go_To_Event(ActionEvent actionEvent) {
    }

    public void Go_To_Message(ActionEvent actionEvent) {
    }

    public void ProfileVisit(MouseEvent mouseEvent) {
    }

    public void Logout(MouseEvent mouseEvent)throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();

    }

    public void sinscrire(ActionEvent actionEvent) {
    }
}
