module com.cnpm {

    requires java.sql;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires lombok;


    opens com.cnpm to javafx.fxml;
    exports com.cnpm;
    exports com.cnpm.controllers;
    opens com.cnpm.controllers to javafx.fxml;
    exports com.cnpm.utilities;
    opens com.cnpm.utilities to javafx.fxml;
    exports com.cnpm.entities;
    opens com.cnpm.entities to javafx.fxml;
}
