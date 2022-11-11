package com.cnpm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NhanKhauController implements Initializable {
    @FXML
    private Button chinh_sua_nhan_khau;

    @FXML
    private Button them_nhan_khau;

    @FXML
    private Button thong_ke_nhan_khau;

    @FXML
    public void chinhSuaNhanKhau(ActionEvent event) {

    }

    @FXML
    public void themNhanKhau(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "them-nhan-khau.fxml", "Them nhan khau");
    }

    @FXML
    public void thongKeNhanKhau(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
