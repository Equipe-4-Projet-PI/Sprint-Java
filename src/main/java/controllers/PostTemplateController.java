package controllers;

import entities.PostEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PostTemplateController {

    @FXML
    private Label Like_num_id;

    @FXML
    private Label post_label_id;

    @FXML
    private Label timestamp_label_id;

    @FXML
    private Label title_label_id;

    @FXML
    private Label user_label_id;

    public void setData(PostEntity postEntity)
    {
        title_label_id.setText(postEntity.getTitle());
        post_label_id.setText(postEntity.getText());
        Like_num_id.setText(""+postEntity.getLike_num());
        timestamp_label_id.setText(""+postEntity.getTime());
//        user_label_id = ?
    }
}
