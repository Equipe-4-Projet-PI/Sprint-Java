package Model;

import controllers.HomepageController;
import controllers.MessagesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import test.Main;

import java.io.IOException;
import java.util.Objects;

//public class SceneSwitch {
//    public SceneSwitch(AnchorPane currentAnchorPane , String fxml)throws IOException {
////        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(HomepageController.class.getResource(fxml)));
//        AnchorPane nextAnchorPane = FXMLLoader.load(HomepageController.class.getResource(fxml)) ;
//        currentAnchorPane.getChildren().removeAll();
//        currentAnchorPane.getChildren().setAll(nextAnchorPane) ;
//    }
//}

public class SceneSwitch {
    public SceneSwitch(AnchorPane currentAnchorPane, String fxml) {
        try {
            // Charger le fichier FXML à partir du chemin spécifié
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            AnchorPane nextAnchorPane = loader.load();

            // Récupérer le contrôleur de la nouvelle scène si nécessaire
            Object controller = loader.getController();

            // Remplacer le contenu actuel par le contenu du prochain AnchorPane
            currentAnchorPane.getChildren().clear();
            currentAnchorPane.getChildren().add(nextAnchorPane);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement dans votre application
        }

}

}


