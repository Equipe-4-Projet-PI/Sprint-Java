package controllers;

import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class CardsController {
    @FXML
    private Label Date;

    @FXML
    private Label Desc;

    @FXML
    private Label Fsale;

    @FXML
    private Label Price;

    @FXML
    private ImageView ProductImage;

    @FXML
    private Label Title;

    @FXML
    private HBox box;

    public void setData(Product product){
        //Image image=new Image(getClass().getResourceAsStream(product.getProductImage()));
        try {
            ProductImage.setImage(new Image(new FileInputStream(product.getProductImage())));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Title.setText(product.getTitle());
        Desc.setText(product.getDescription());
        Fsale.setText(String.valueOf(product.getForSale()));
        Price.setText(String.valueOf(product.getPrice()));
        Date.setText(product.getCreationDate());

    }
}
