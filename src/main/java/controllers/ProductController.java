package controllers;

import entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import services.ServiceOrder;
import services.ServiceProduct;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProductController {

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
            String imageURL = product_image.getImage().getUrl();

        try {
            s.ajouter(new Product(userid,forsalevalue(),price,pr_title.getText(),pr_desc.getText(),formattedDate,imageURL));
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

}
