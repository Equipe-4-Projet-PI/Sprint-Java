package controllers;

import entities.Disscussion;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class RefreshController {

    @FXML
    private Label loadinglabel ;

    private Disscussion d;

    public void setMsgData(Disscussion d, Scene scene) {
        String idDis = String.valueOf(d.getIdReceiver());
        this.loadinglabel.setText(idDis);
        System.out.println(d);
        this.d = d;
    }
}
