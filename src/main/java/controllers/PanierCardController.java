package controllers;

import entities.Product;
import entities.ProductOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ServiceOrder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class PanierCardController {

    @FXML
    private Label order_date;

    @FXML
    private Label order_id;

    @FXML
    private ImageView order_image;

    @FXML
    private Label order_price;

    @FXML
    private Label order_title;

    @FXML
    private Label prod_id;
    ServiceOrder o = new ServiceOrder();



    private String imagePath;
    public void setData(ProductOrder productOrder){
        this.imagePath = productOrder.getProd_img();
        try {
            order_image.setImage(new Image(new FileInputStream(this.imagePath)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        order_title.setText(productOrder.getTitle());
        order_date.setText(productOrder.getOrderDate());
        order_id.setText(String.valueOf(productOrder.getId_Order()));
        prod_id.setText(String.valueOf(productOrder.getId_Product()));
        order_price.setText(String.valueOf(productOrder.getPrice()));
    }
    @FXML
    void cancel_order_btn(ActionEvent event) {
        int id_prod = Integer.parseInt(prod_id.getText());
        int id_order = Integer.parseInt(order_id.getText());
        String title = order_title.getText();
        String date = order_date.getText();
        Double price = Double.valueOf(order_price.getText());
        String image = this.imagePath;
        try {
            o.supprimer(new ProductOrder(id_order,id_prod,title,date,price,image));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void payer_order_btn(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stripe API Portal");
        alert.setContentText("Stripe Payement Goes here");
        alert.showAndWait();
    }

}
