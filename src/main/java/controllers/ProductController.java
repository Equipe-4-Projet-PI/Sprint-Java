package controllers;

import entities.Product;
import entities.ProductOrder;
import entities.User;
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
import services.MyListner;
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

public class ProductController {



    int userid;
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
    private ImageView product_image_ed;
    @FXML
    private TextField pr_date_ed;
    @FXML
    private TextField pr_id_ed;
    @FXML
    private Pane Edit_panel;

    @FXML
    private TextArea pr_desc;

    @FXML
    private RadioButton pr_oui;

    @FXML
    private TextField pr_price;

    @FXML
    private ImageView product_image;
    @FXML
    private ScrollPane Affichage_panel;
    @FXML
    private HBox PanierCard;
    @FXML
    private ScrollPane afficher_panier;

    @FXML
    private ChoiceBox<String> pr_title;
    @FXML
    private ChoiceBox<String> pr_title_ed;

    @FXML
    private ChoiceBox<String> FilterBox;
    private List<Product> fa;
    @FXML
    private CheckBox box_fsale;

    @FXML
    private CheckBox box_nosale;
    @FXML
    private ScrollPane afficher_ma_list;

    @FXML
    private HBox ma_list_box;
    @FXML
    private HBox CardLayout;
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = currentDate.format(formatter);
    ServiceProduct s = new ServiceProduct();
    ServiceOrder o = new ServiceOrder();
    private List<Product> fo(){
        try {
            return s.maList(userid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    int forsalevalue(){
        if (pr_oui.isSelected()){
            return 1;
        }
        else return 0;
    }

    void ini(){
        pr_title.getItems().addAll("peinture","sculpture","photography");
    }
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
                MyListner myListner= new MyListner() {
                    @Override
                    public void onClick(int value,String value1,String value2,String value3,int value4,Double value5,String value6) {
                        ini1();
                        Edit_panel.setVisible(true);
                        afficher_ma_list.setVisible(false);
                        add_panel.setVisible(false);
                        Affichage_panel.setVisible(false);
                        afficher_panier.setVisible(false);
                        pr_id_ed.setText(String.valueOf(value));
                        pr_title_ed.setValue(value1);
                        pr_desc_ed.setText(value2);
                        pr_date_ed.setText(String.valueOf(value3));
                        if (value4!=0){
                            pr_oui_ed.setSelected(true);
                        }else {
                            pr_oui_ed.setSelected(false);
                        }
                        pr_price_ed.setText(String.valueOf(value5));

                        String imagePath = value6;
                        System.out.println(imagePath);
                        Image image = new Image(imagePath);
                        product_image_ed.setImage(image);
                    }
                };
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/MyCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                MyCardController myCardController = fxmlLoader.getController();
                myCardController.setData(fa.get(i),myListner);
                currentHBox.getChildren().add(cardBox);
            }

            mainVBox.getChildren().add(currentHBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void ini1(){
        pr_title_ed.getItems().addAll("peinture","sculpture","photography");
    }
    void ini2(){
        FilterBox.getItems().addAll("peinture","sculpture","photography");
    }

    private List<Product> recentlyAdded1(){
        try {
            return s.saleList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Product> recentlyAdded;
    private List<Product> recentlyAdded2(){
        try {
            return s.NosaleList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Product> recentlyAdded3(){
        try {
            return s.FilterShow(FilterBox.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Product> recentlyAdded(){
        try {
            return s.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(URL location, ResourceBundle resources){
        if (box_fsale.isSelected()){
            recentlyAdded =new ArrayList<>(recentlyAdded1());
            box_nosale.setSelected(false);
            FilterBox.setValue(null);
        } else if (box_nosale.isSelected()) {
            recentlyAdded =new ArrayList<>(recentlyAdded2());
            box_fsale.setSelected(false);
            FilterBox.setValue(null);
        } else if (FilterBox.getValue()!=null) {
            recentlyAdded =new ArrayList<>(recentlyAdded3());
            box_fsale.setSelected(false);
            box_nosale.setSelected(false);
        } else {
            recentlyAdded = new ArrayList<>(recentlyAdded());
            box_nosale.setSelected(false);
            box_fsale.setSelected(false);
        }
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



    @FXML
    void AddProduct(ActionEvent event) {

        try{
            price = Double.parseDouble(pr_price.getText());
        }catch(NumberFormatException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Price feild error");
            alert.setContentText("invalid integer input");
            alert.showAndWait();
        }


        String ImageURL="";
        Image image = product_image.getImage();
        if (image != null) {
            String imageUrl = image.getUrl();
            ImageURL = imageUrl;
//            if (imageUrl.startsWith("file:/")) {
//                imagePath = imageUrl.substring("file:/".length());
//            }
        }
        try {
            s.ajouter(new Product(userid,forsalevalue(),price,pr_title.getValue(),pr_desc.getText(),formattedDate,ImageURL));
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("succes");
            alert.setContentText("product added");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText("Verifier les champs et contraint de saisir");
            alert.showAndWait();
        }
        clear();

    }
    void clear(){
        pr_title.setValue("");
        pr_desc.setText("");
        pr_price.setText("");
        pr_oui.setSelected(false);
        product_image.setImage(new Image("file:D:\\Heikun\\ESPRIT 2023-2024\\JAVA\\TD Revision\\Sprint-Java-DEV\\src\\main\\resources\\images\\img.png"));
    }

    @FXML
    void BoxExitFilter(MouseEvent event) {
        refreshData();
    }

    @FXML
    void CancelAddProduct(ActionEvent event) {
        add_panel.setVisible(false);
        Affichage_panel.setVisible(true);
        afficher_panier.setVisible(false);
        afficher_ma_list.setVisible(false);
        Edit_panel.setVisible(false);
        box_fsale.setVisible(false);
        box_nosale.setVisible(false);
        FilterBox.setVisible(false);
        refreshData();

    }

    @FXML
    void CancelEditProduct(ActionEvent event) {
        afficher_ma_list.setVisible(true);
        add_panel.setVisible(false);
        box_fsale.setVisible(false);
        box_nosale.setVisible(false);
        Affichage_panel.setVisible(false);
        afficher_panier.setVisible(false);
        Edit_panel.setVisible(false);
        FilterBox.setVisible(false);
        refreshMyCards();
    }

    @FXML
    void EditProduct(ActionEvent event) {
        try{
            price_ed = Double.parseDouble(pr_price_ed.getText());
        }catch(NumberFormatException e){
            System.out.println("invalid integer input");
        }
        int id = Integer.parseInt(pr_id_ed.getText());
        String title = pr_title_ed.getValue();
        String desc = pr_desc_ed.getText();
        String date = pr_date_ed.getText();
        Double price = Double.valueOf(pr_price_ed.getText());
        String imagePath = "";
        Image image = product_image_ed.getImage();
        if (image != null) {

            String imageUrl = image.getUrl();
            imagePath = imageUrl;
//            if (imageUrl.startsWith("file:/")) {
//                imagePath = imageUrl.substring("file:/".length());
//            }
        }
        try {
            s.modifier(new Product(id,userid,forsalevalue_ed(),price,title,desc,date,imagePath));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        afficher_ma_list.setVisible(true);
        add_panel.setVisible(false);
        Affichage_panel.setVisible(false);
        box_fsale.setVisible(false);
        box_nosale.setVisible(false);
        afficher_panier.setVisible(false);
        Edit_panel.setVisible(false);
        FilterBox.setVisible(false);

        refreshMyCards();

    }



    @FXML
    void MagPage(MouseEvent event) {
        add_panel.setVisible(false);
        Affichage_panel.setVisible(true);
        afficher_panier.setVisible(false);
        afficher_ma_list.setVisible(false);
        Edit_panel.setVisible(false);
        box_fsale.setVisible(true);
        box_nosale.setVisible(true);
        FilterBox.setVisible(true);
        refreshData();
    }

    @FXML
    void ResetAddProduct(ActionEvent event) {
clear();
    }

    @FXML
    void add_form_button(MouseEvent event) {
        ini();
    add_panel.setVisible(true);
    Affichage_panel.setVisible(false);
        afficher_panier.setVisible(false);
        Edit_panel.setVisible(false);
        afficher_ma_list.setVisible(false);
        box_fsale.setVisible(false);
        box_nosale.setVisible(false);
        FilterBox.setVisible(false);
        product_image.setImage(new Image("file:D:\\Heikun\\ESPRIT 2023-2024\\JAVA\\TD Revision\\Sprint-Java-DEV\\src\\main\\resources\\images\\img.png"));

    }

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

    @FXML
    void add_image_dialog_ed(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            product_image_ed.setImage(image);

        }
    }

    @FXML
    void all_art(MouseEvent event) {
        add_panel.setVisible(false);
        box_fsale.setVisible(true);
        box_nosale.setVisible(true);
        Affichage_panel.setVisible(true);
        afficher_panier.setVisible(false);
        afficher_ma_list.setVisible(false);
        Edit_panel.setVisible(false);
        FilterBox.setVisible(true);
        ini2();
        refreshData();

    }

    @FXML
    void fsale_box_clicked(ActionEvent event) {
        refreshData();
    }

    @FXML
    void my_art(MouseEvent event) {
        afficher_ma_list.setVisible(true);
        add_panel.setVisible(false);
        Affichage_panel.setVisible(false);
        afficher_panier.setVisible(false);
        Edit_panel.setVisible(false);
        box_fsale.setVisible(false);
        box_nosale.setVisible(false);
        FilterBox.setVisible(false);
        refreshMyCards();

    }
    public void refreshMyCards() {
        fa = new ArrayList<>(fo());
        ma_list_box.getChildren().clear();
        initialized();
    }

    @FXML
    void no_sale_box_clicked(ActionEvent event) {
//        refreshData();
    }

    @FXML
    void panier_button(MouseEvent event) {
        refreshPanierCards();
        add_panel.setVisible(false);
        Affichage_panel.setVisible(false);
        afficher_panier.setVisible(true);
        afficher_ma_list.setVisible(false);
        Edit_panel.setVisible(false);
        box_fsale.setVisible(false);
        box_nosale.setVisible(false);
        FilterBox.setVisible(false);
    }
    private void removePanierCards() {
        PanierCard.getChildren().clear();
    }

    public void initUser(User user) {
        userid = user.getId_User();
    }











    private List<ProductOrder> ro(){
        try {
            return o.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<ProductOrder> ra;
    public void initializee(){
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
       initializee();
    }
    int forsalevalue_ed(){
        if (pr_oui_ed.isSelected()){
            return 1;
        }
        else return 0;
    }







//
    /////////////////////////add product form ////////////////////////////////////////
  //
//
//
//
//
//
//
//
//
//
//
//
//
//
//    ///partie ma liste ////////////////////////
//    @FXML
//    private ScrollPane afficher_ma_list;
//    @FXML
//    private HBox ma_list_box;
//    int id=2;
//    private List<Product> fo(){
//        try {
//            return s.maList(id);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    void ini1(){
//        pr_title_ed.getItems().addAll("peinture","sculpture","photography");
//    }
//    private List<Product> fa;
//    public void initialized(){
//        fa =new ArrayList<>(fo());
//        System.out.println("the size of data "+fa.size());
//        try {
//            VBox mainVBox = new VBox();
//            ma_list_box.getChildren().add(mainVBox);
//
//            HBox currentHBox = new HBox();
//            currentHBox.setSpacing(50);
//
//            for (int i = 0; i < fa.size(); i++) {
//                if (i > 0 && i % 3 == 0) {
//                    mainVBox.getChildren().add(currentHBox);
//                    currentHBox = new HBox();
//                    currentHBox.setSpacing(50);
//                }
//                MyListner myListner= new MyListner() {
//                    @Override
//                    public void onClick(int value,String value1,String value2,String value3,int value4,Double value5,String value6) {
//                        ini1();
//                        Edit_panel.setVisible(true);
//                        afficher_ma_list.setVisible(false);
//                        add_panel.setVisible(false);
//                        Affichage_panel.setVisible(false);
//                        afficher_panier.setVisible(false);
//                        pr_id_ed.setText(String.valueOf(value));
//                        pr_title_ed.setValue(value1);
//                        pr_desc_ed.setText(value2);
//                        pr_date_ed.setText(String.valueOf(value3));
//                        if (value4!=0){
//                            pr_oui_ed.setSelected(true);
//                        }else {
//                            pr_oui_ed.setSelected(false);
//                        }
//                        pr_price_ed.setText(String.valueOf(value5));
//
//                        String imagePath = value6;
//                        Image image = new Image(new File(imagePath).toURI().toString());
//                        product_image_ed.setImage(image);
//                    }
//                };
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/MyCard.fxml"));
//                HBox cardBox = fxmlLoader.load();
//                MyCardController myCardController = fxmlLoader.getController();
//                myCardController.setData(fa.get(i),myListner);
//                currentHBox.getChildren().add(cardBox);
//            }
//
//            mainVBox.getChildren().add(currentHBox);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void refreshMyCards() {
//        fa = new ArrayList<>(fo());
//        ma_list_box.getChildren().clear();
//        initialized();
//    }
//    //////////////////////ma liste end ////////////////////////////
/////////////////////////////////edit post from my list /////////////////////////////
//    @FXML
//    void my_art(MouseEvent event) {
//        afficher_ma_list.setVisible(true);
//        add_panel.setVisible(false);
//        Affichage_panel.setVisible(false);
//        afficher_panier.setVisible(false);
//        Edit_panel.setVisible(false);
//        box_fsale.setVisible(false);
//        box_nosale.setVisible(false);
//        FilterBox.setVisible(false);
//        refreshMyCards();
//    }
//    @FXML
//    void CancelEditProduct(ActionEvent event) {
//        afficher_ma_list.setVisible(true);
//        add_panel.setVisible(false);
//        box_fsale.setVisible(false);
//        box_nosale.setVisible(false);
//        Affichage_panel.setVisible(false);
//        afficher_panier.setVisible(false);
//        Edit_panel.setVisible(false);
//        FilterBox.setVisible(false);
//
//        refreshMyCards();
//    }
//
//    int forsalevalue_ed(){
//        if (pr_oui_ed.isSelected()){
//            return 1;
//        }
//        else return 0;
//    }
//
//    @FXML
//    void add_image_dialog_ed(MouseEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Choose Image File");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
//        );
//        File selectedFile = fileChooser.showOpenDialog(null);
//        if (selectedFile != null) {
//            Image image = new Image(selectedFile.toURI().toString());
//            product_image_ed.setImage(image);
//
//        }
//    }
//    @FXML
//    void EditProduct(ActionEvent event) {
//        try{
//            price_ed = Double.parseDouble(pr_price_ed.getText());
//        }catch(NumberFormatException e){
//            System.out.println("invalid integer input");
//        }
//        int id = Integer.parseInt(pr_id_ed.getText());
//        String title = pr_title_ed.getValue();
//        String desc = pr_desc_ed.getText();
//        String date = pr_date_ed.getText();
//        Double price = Double.valueOf(pr_price_ed.getText());
//        String imagePath = "";
//        Image image = product_image_ed.getImage();
//        if (image != null) {
//            String imageUrl = image.getUrl();
//            if (imageUrl.startsWith("file:/")) {
//                imagePath = imageUrl.substring("file:/".length());
//            }
//        }
//        try {
//            s.modifier(new Product(id,userid,forsalevalue_ed(),price,title,desc,date,imagePath));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        afficher_ma_list.setVisible(true);
//        add_panel.setVisible(false);
//        Affichage_panel.setVisible(false);
//        box_fsale.setVisible(false);
//        box_nosale.setVisible(false);
//        afficher_panier.setVisible(false);
//        Edit_panel.setVisible(false);
//        FilterBox.setVisible(false);
//
//        refreshMyCards();
//    }

}
