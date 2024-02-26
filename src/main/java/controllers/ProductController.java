package controllers;

import entities.Product;
import entities.ProductOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import services.ServiceOrder;
import services.ServiceProduct;
import javafx.fxml.Initializable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable{

    int userid,userid_ed,pr_pid;
    double price,price_ed;
    @FXML
    private Pane add_panel;
    @FXML
    private TextArea pr_desc_ed;
    @FXML
    private RadioButton pr_oui_ed;
    @FXML
    private TextField pr_price_ed;

    @FXML
    private TextField pr_title_ed;
    @FXML
    private ImageView product_image_ed;
    @FXML
    private Pane Edit_panel;

    @FXML
    private TextArea pr_desc;

    @FXML
    private RadioButton pr_oui;

    @FXML
    private TextField pr_price;

    @FXML
    private TextField pr_title;

    @FXML
    private TextField pr_userid;

    @FXML
    private ImageView product_image;
    @FXML
    private ScrollPane Affichage_panel;
    @FXML
    private HBox PanierCard;
    @FXML
    private ScrollPane afficher_panier;

    int forsalevalue(){
        if (pr_oui.isSelected()){
            return 1;
        }
        else return 0;
    }
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = currentDate.format(formatter);
    ServiceProduct s = new ServiceProduct();
    ServiceOrder o = new ServiceOrder();
    /////////////////////////add product form ////////////////////////////////////////
    @FXML
    void AddProduct(ActionEvent event) {
        try{
            userid = Integer.parseInt(pr_userid.getText());
        }catch(NumberFormatException e){
            System.out.println("invalid integer input");
        }
        try{
            price = Double.parseDouble(pr_price.getText());
        }catch(NumberFormatException e){
            System.out.println("invalid integer input");
        }
        String imagePath = "";
        Image image = product_image.getImage();
        if (image != null) {
            String imageUrl = image.getUrl();
            if (imageUrl.startsWith("file:/")) {
                imagePath = imageUrl.substring("file:/".length());
            }
        }
        try {
            s.ajouter(new Product(userid,forsalevalue(),price,pr_title.getText(),pr_desc.getText(),formattedDate,imagePath));
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("succes");
            alert.setContentText("product added");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        clear();
    }

    @FXML
    void CancelAddProduct(ActionEvent event) {
        add_panel.setVisible(false);
        Affichage_panel.setVisible(true);
        afficher_panier.setVisible(false);
        afficher_ma_list.setVisible(false);
        refreshData();
    }
    @FXML
    void ResetAddProduct(ActionEvent event) {
        clear();
    }
    void clear(){
        pr_userid.setText("");
        pr_title.setText("");
        pr_desc.setText("");
        pr_price.setText("");
        pr_oui.setSelected(false);
        product_image.setImage(new Image("file:C:\\Users\\bigal\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\img.png"));
    }

    @FXML
    void add_form_button(MouseEvent event) {
    add_panel.setVisible(true);
    Affichage_panel.setVisible(false);
        afficher_panier.setVisible(false);
        product_image.setImage(new Image("file:C:\\Users\\bigal\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\img.png"));
    }

    /////////////////////////add image pop up dialog //////////////////////////////////////////////////
    @FXML
    void add_image_dialog(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            product_image.setImage(image);

        }
    }

    ////////////////////////load data from product ////////////////////////////////////
    private List<Product> recentlyAdded;
    @FXML
    private HBox CardLayout;
    public void initialize(URL location, ResourceBundle resources){
        recentlyAdded =new ArrayList<>(recentlyAdded());
        System.out.println("the size of data "+recentlyAdded.size());
        try {
            VBox mainVBox = new VBox();
            CardLayout.getChildren().add(mainVBox);

            HBox currentHBox = new HBox();
            currentHBox.setSpacing(50);

            for (int i = 0; i < recentlyAdded.size(); i++) {
                if (i > 0 && i % 4 == 0) {
                    mainVBox.getChildren().add(currentHBox);
                    currentHBox = new HBox();
                    currentHBox.setSpacing(50);
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardsController cardsController = fxmlLoader.getController();
                cardsController.setData(recentlyAdded.get(i));
                currentHBox.getChildren().add(cardBox);
            }

            mainVBox.getChildren().add(currentHBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshData() {
        CardLayout.getChildren().clear();
        initialize(null, null);
    }
    private List<Product> recentlyAdded(){
        try {
            return s.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void MagPage(MouseEvent event) {
        add_panel.setVisible(false);
        Affichage_panel.setVisible(true);
        afficher_panier.setVisible(false);
        afficher_ma_list.setVisible(false);
        refreshData();
    }
    @FXML
    void all_art(MouseEvent event) {
        add_panel.setVisible(false);
        Affichage_panel.setVisible(true);
        afficher_panier.setVisible(false);
        afficher_ma_list.setVisible(false);
        refreshData();
    }


    private void removePanierCards() {
        PanierCard.getChildren().clear();
    }
    @FXML
    void panier_button(MouseEvent event) {
        refreshPanierCards();
        add_panel.setVisible(false);
        Affichage_panel.setVisible(false);
        afficher_panier.setVisible(true);
        afficher_ma_list.setVisible(false);

    }
    ///////////////////////////load data from order ///////////////////////////////////////////
    private List<ProductOrder> ro(){
        try {
            return o.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<ProductOrder> ra;
    public void initialize(){
        ra =new ArrayList<>(ro());
        System.out.println("the size of data "+ra.size());
        try {
            VBox mainVBox = new VBox();
            PanierCard.getChildren().add(mainVBox);

            HBox currentHBox = new HBox();
            currentHBox.setSpacing(50);

            for (int i = 0; i < ra.size(); i++) {
                if (i > 0 && i % 3 == 0) {
                    mainVBox.getChildren().add(currentHBox);
                    currentHBox = new HBox();
                    currentHBox.setSpacing(50);
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/PanierCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                PanierCardController panierCardController = fxmlLoader.getController();
                panierCardController.setData(ra.get(i));
                currentHBox.getChildren().add(cardBox);
            }

            mainVBox.getChildren().add(currentHBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //refrech fn for orders
    public void refreshPanierCards() {
        ra = new ArrayList<>(ro());
        PanierCard.getChildren().clear();
        initialize();
    }
    @FXML
    private ScrollPane afficher_ma_list;

    @FXML
    private HBox ma_list_box;
    @FXML
    void my_art(MouseEvent event) {
        afficher_ma_list.setVisible(true);
        add_panel.setVisible(false);
        Affichage_panel.setVisible(false);
        afficher_panier.setVisible(false);
        refreshMyCards();
    }
    ///partie ma liste ////////////////////////
    int id=2;
    private List<Product> fo(){
        try {
            return s.maList(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Product> fa;
    public void initialized(){
        fa =new ArrayList<>(fo());
        System.out.println("the size of data "+fa.size());
        try {
            VBox mainVBox = new VBox();
            ma_list_box.getChildren().add(mainVBox);

            HBox currentHBox = new HBox();
            currentHBox.setSpacing(50);

            for (int i = 0; i < fa.size(); i++) {
                if (i > 0 && i % 3 == 0) {
                    mainVBox.getChildren().add(currentHBox);
                    currentHBox = new HBox();
                    currentHBox.setSpacing(50);
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/MyCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                MyCardController myCardController = fxmlLoader.getController();
                myCardController.setData(fa.get(i));
                currentHBox.getChildren().add(cardBox);
            }

            mainVBox.getChildren().add(currentHBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshMyCards() {
        fa = new ArrayList<>(fo());
        ma_list_box.getChildren().clear();
        initialized();
    }
    //////////////////////ma liste end ////////////////////////////
///////////////////////////////edit post from my list /////////////////////////////
    @FXML
    void CancelEditProduct(ActionEvent event) {

    }

    @FXML
    void EditProduct(ActionEvent event) {

    }
}
