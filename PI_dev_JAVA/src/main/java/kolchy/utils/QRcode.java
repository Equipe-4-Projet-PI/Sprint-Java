package kolchy.utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class QRcode {

    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    String myWeb = "http://java-buddy.blogspot.com/";
    int width = 300;
    int height = 300;
    String fileType = "png";

 /*   BufferedImage bufferedImage = null;
        try {
        BitMatrix byteMatrix = qrCodeWriter.encode(myWeb, BarcodeFormat.QR_CODE, width, height);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics();

        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        System.out.println("Success...");

    } catch (WriterException ex) {
        Logger.getLogger(JavaFX_QRCodeWriter.class.getName()).log(Level.SEVERE, null, ex);
    }

    ImageView qrView = new ImageView();
        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

    StackPane root = new StackPane();
        root.getChildren().add(qrView);

    Scene scene = new Scene(root, 350, 350);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();*/
}


