package com.cnpm.controllers;

import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ThayDoiController implements Initializable {

    @FXML
    private void thayDoiNhanKhau(ActionEvent event) {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/thay-doi-nhan-khau.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
