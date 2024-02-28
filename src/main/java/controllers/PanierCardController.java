package controllers;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import entities.Product;
import entities.ProductOrder;
import javafx.application.Platform;
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

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import java.net.URISyntaxException;
import java.net.URI;
import java.awt.Desktop;


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
        Double montant = Double.valueOf(order_price.getText());
        String title = order_title.getText();
        Stripe.apiKey = "sk_test_51Oop0OBQCHJCIBnOp9sP9YNzRUVOleVW4d5FgsXox60XUClnwh8ZiMMamUtpz8LgHkIfYzQw8q40ocGEf1fVkj7G00Qk2ILK7A";

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://your-website.com/success")
                    .setCancelUrl("https://your-website.com/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount((long) (montant * 100)) // Stripe expects the amount in cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(title)
                                                                    .addImage("https://i.imgur.com/vceNLz2.jpeg")
                                                                    .setDescription("ArtyVenci Votre Gallery Virtuelle")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);
            URI checkoutUri = new URI(session.getUrl());
            java.awt.Desktop.getDesktop().browse(checkoutUri); // Opens the URL in the default browser
            String paymentStatus = session.getPaymentStatus(); // Replace with the ID of the payment you want to retrieve data for
            System.out.println(paymentStatus);
        } catch (StripeException e) {
            System.err.println("Error creating Checkout Session: " + e.getMessage());
            e.printStackTrace();
            // Handle the error, display a message to the user, etc.
        } catch (Exception ex) {
            System.err.println("Error redirecting to Stripe Checkout: " + ex.getMessage());
            ex.printStackTrace();
            // Handle the error, display a message to the user, etc.
        }


    }
    public static PaymentIntent retrievePaymentData(String paymentId) throws StripeException {
        return PaymentIntent.retrieve(paymentId);
    }

}
