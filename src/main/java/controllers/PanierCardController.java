package controllers;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import entities.Product;
import entities.ProductOrder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.JSONException;
import services.ServiceOrder;
import javafx.scene.web.WebView;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import org.json.JSONObject;


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
            //loadCheckoutPage(checkoutUri.toString());

            java.awt.Desktop.getDesktop().browse(checkoutUri); // Opens the URL in the default browser
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

    @FXML
    void PayFlouci(ActionEvent event) {
        Double montant = Double.valueOf(order_price.getText());
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("app_token", "aebe6188-4587-4ff0-9b1c-a26c7898ee73");
        jsonBody.put("app_secret", "83c2f9f3-0fdc-4dec-9bf2-e642c1cce53d");
        jsonBody.put("accept_card", true); // Use boolean value, not string
        jsonBody.put("amount", (long) (montant * 1000));
        jsonBody.put("success_link", "https://example.website.com/success");
        jsonBody.put("fail_link", "https://example.website.com/fail");
        jsonBody.put("session_timeout_secs", 1200);
        jsonBody.put("developer_tracking_id", "df9dd458-65ed-4d8b-b354-302077358ef2");


        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonBody.toString());
        Request request = new Request.Builder()
                .url("https://developers.flouci.com/api/generate_payment")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("Payment generated successfully");
                String responseBody = response.body().string();
                handleResponse(responseBody);

            } else {
                System.out.println("Error generating payment: " + response.code());
                System.out.println(response.body().string());
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleResponse(String responseBody) {
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONObject result = jsonResponse.getJSONObject("result");
        String linkString = result.getString("link");
        URI link = null;
        try {
            link = new URI(linkString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            Desktop.getDesktop().browse(link);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadCheckoutPage(String url) {
        Stage stage = new Stage();
        WebView webView = new WebView();
        webView.getEngine().load(url);
        Scene scene = new Scene(webView);
        stage.setScene(scene);
        stage.show();
    }
}


