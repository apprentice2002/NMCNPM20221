module com.example.cnpm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires lombok;


    opens com.cnpm to javafx.fxml;
    exports com.cnpm;
}