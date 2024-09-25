module com.example.projektjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projektjava to javafx.fxml;
    opens com.example.projektjava.entity to javafx.base;
    exports com.example.projektjava;
}
