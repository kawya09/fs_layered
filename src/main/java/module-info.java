module lk.ijse.furniture_shop_layered1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires lk.ijse.furniture_shop_layered1;
    requires java.sql;


    opens lk.ijse.demo to javafx.fxml;
    exports lk.ijse.demo;
}