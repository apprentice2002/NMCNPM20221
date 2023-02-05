package com.cnpm.controllers;

import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DangKyController implements Initializable {

    @FXML
    public void dangKyTamVang(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/dang-ky-tam-vang.fxml");
    }

    @FXML
    public void dangKyTamTru(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/dang-ky-tam-tru.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

