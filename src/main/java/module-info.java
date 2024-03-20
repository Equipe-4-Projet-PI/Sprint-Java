module ArtyVenci3{

    requires javafx.graphics ;
    requires javafx.controls ;
    requires javafx.fxml;
    requires java.sql;

    opens controllers;
    exports test ;
    exports controllers ;

}