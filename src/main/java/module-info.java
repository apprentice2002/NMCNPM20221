module com.cnpm {

    requires java.sql;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires lombok;
    requires javafx.graphics;


    opens com.cnpm to javafx.fxml;
    exports com.cnpm;
    exports com.cnpm.controllers;
    opens com.cnpm.controllers to javafx.fxml;
    exports com.cnpm.utilities;
    opens com.cnpm.utilities to javafx.fxml;
    exports com.cnpm.entities;
    opens com.cnpm.entities to javafx.fxml;

    exports com.cnpm.controllers.phat_thuong;
    opens com.cnpm.controllers.phat_thuong to javafx.fxml;

    exports com.cnpm.controllers.ho_khau;
    opens com.cnpm.controllers.ho_khau to javafx.fxml;

    exports com.cnpm.controllers.phat_qua;
    opens com.cnpm.controllers.phat_qua to javafx.fxml;
    exports com.cnpm.controllers.minh_chung;
    opens com.cnpm.controllers.minh_chung to javafx.fxml;
}
