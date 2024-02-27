package controllers;

import entities.Product;
import entities.ProductOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.MyListner;
import services.ServiceProduct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class MyCardController {
    ServiceProduct s = new ServiceProduct();

    @FXML
    private Label my_date;

    @FXML
    private Label my_desc;

    @FXML
    private Label my_fsale;

    @FXML
    private Label my_id;

    @FXML
    private ImageView my_image;

    @FXML
    private Label my_price;

    @FXML
    private Label my_title;

    private String imagePath;

    private MyListner myListner;


    public void setData(Product product,MyListner myListner){
        this.myListner=myListner;
        this.imagePath = product.getProductImage();
        try {
            my_image.setImage(new Image(new FileInputStream(this.imagePath)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        my_title.setText(product.getTitle());
        my_desc.setText(product.getDescription());
        my_fsale.setText(String.valueOf(product.getForSale()));
        my_price.setText(String.valueOf(product.getPrice()));
        my_date.setText(product.getCreationDate());
        my_id.setText(String.valueOf(product.getId_Product()));
    }
    @FXML
    void delete_item(ActionEvent event) {
        int idp = Integer.parseInt(my_id.getText());
        String title = my_title.getText();
        String desc = my_desc.getText();
        String date = my_date.getText();
        int fsale = Integer.parseInt(my_fsale.getText());
        Double price = Double.valueOf(my_price.getText());
        String image = this.imagePath;
        try {
            s.supprimer(new Product(idp,fsale,price,title,desc,date,image));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void edit_item(ActionEvent event) {
        int idp = Integer.parseInt(my_id.getText());
        String title = my_title.getText();
        String desc = my_desc.getText();
        String date = my_date.getText();
        int fsale = Integer.parseInt(my_fsale.getText());
        Double price = Double.valueOf(my_price.getText());
        String image = this.imagePath;
        myListner.onClick(idp,title,desc,date,fsale,price,image);

    }
}
