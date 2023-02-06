package com.cnpm.controllers;

import com.cnpm.utilities.DBConnection;
import com.cnpm.entities.NhanKhau;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ThemHoKhauController implements Initializable {
    @FXML
    private TextField ma_ho_khau;
    @FXML
    private TextField ma_chu_ho;
    @FXML
    private TextField ma_khu_vuc;
    @FXML
    private TextField dia_chi;
    @FXML
    private Button xac_nhan;
    @FXML
    private Button huy;
    @FXML
    private Button them_thanh_vien;

    @FXML
    public void huy(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/scenes/home.fxml");
    }

    @FXML
    public void xacnhan(ActionEvent event) throws IOException {

    }
    @FXML
    public void themthanhvien(ActionEvent event) throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
