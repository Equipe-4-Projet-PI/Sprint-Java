package controllers;

import entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyCardController {

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

    @FXML
    void delete_item(ActionEvent event) {

    }

    @FXML
    void edit_item(ActionEvent event) {

    }
    public void setData(Product product){
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

}
