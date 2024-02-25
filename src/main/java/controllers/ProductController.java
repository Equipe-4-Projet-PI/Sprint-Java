package controllers;

import entities.Product;
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
           // String imageURL = product_image.getImage().getUrl();
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
    product_image.setImage(new Image("file:C:\\Users\\bigal\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\img.png"));
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
                    currentHBox.setSpacing(30);
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

    private List<Product> recentlyAdded(){
        try {
            return s.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void MagPage(MouseEvent event) {
    }

}
