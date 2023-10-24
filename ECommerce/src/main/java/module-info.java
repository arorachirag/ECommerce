module com.example.snakeladder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.Ecommerce to javafx.fxml;
    exports com.example.Ecommerce;
}