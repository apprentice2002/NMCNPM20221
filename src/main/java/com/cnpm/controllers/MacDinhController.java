package com.cnpm.controllers;

import com.cnpm.utilities.UserSession;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MacDinhController implements Initializable {
    @FXML private Label hoKhauLab;
    @FXML private Label nhanKhauLab;
    @FXML private Label nhanKhauTamTruLab;
    @FXML private Label nhanKhauTamVangLab;

    @FXML
    void logout(ActionEvent event) {
        Utilities.changeScene(event, "/com/cnpm/scenes/dang-nhap.fxml");
        UserSession.setUsername(null);
        UserSession.setPrivileges(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
