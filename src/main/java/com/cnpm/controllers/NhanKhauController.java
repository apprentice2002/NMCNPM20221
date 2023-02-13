package com.cnpm.controllers;

import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NhanKhauController implements Initializable {
    @FXML
    private TextField thong_tin_tim_kiem;
    @FXML
    private Button search;
    @FXML
    private ChoiceBox loc;

    @FXML
    private Button them_ho_khau;

    @FXML
    private Button dang_ky_tam_vang;
    @FXML
    private Button dang_ky_tam_tru;

    @FXML
    public void search(ActionEvent event) {

    }
    @FXML
    public void loc(ActionEvent event) {

    }

    @FXML
    public void themNhanKhau(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/scenes/them-nhan-khau.fxml");
    }

    @FXML
    public void dangKyTamVang(ActionEvent event) {

    }
    @FXML
    public void dangKyTamTru(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
