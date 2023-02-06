module com.cnpm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
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
