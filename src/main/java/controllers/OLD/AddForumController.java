package controllers.OLD;

        import entities.ForumEntity;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.control.Alert;
        import javafx.scene.control.TextField;
        import services.ServiceForumF;

        import java.io.IOException;
        import java.sql.SQLException;

public class AddForumController {

    ServiceForumF SF = new ServiceForumF();
    @FXML
    private TextField f_desc;

    @FXML
    private TextField f_title;

    @FXML
    void AfficherForum(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/ForumPages/Old/AfficherForumArtist.fxml"));
            f_title.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjouterForum(ActionEvent event) {
        try {
            SF.ajouter(new ForumEntity(f_title.getText(),f_desc.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Forum Ajouté");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
