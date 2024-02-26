module PI.dev.VER2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens kolchy to javafx.fxml;
    exports kolchy;
    exports kolchy.test;
    opens kolchy.test to javafx.fxml;
    exports kolchy.controller;
    opens kolchy.controller to javafx.fxml;
}