package com.cnpm.controllers;

import java.sql.PreparedStatement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.sql.ResultSet;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class ThongKeNhanKhauController implements Initializable {
    @FXML
    private TextField fromAgeTxt;
    @FXML
    private TextField toAgeTxt;
    @FXML
    private TextField fromYearTxt;
    @FXML
    private TextField toYearTxt;
    @FXML
    private Button cancleBtn;
    @FXML
    private TableView nhanKhauTable;
    @FXML
    private TableColumn maNhanKhauCol;
    @FXML
    private TableColumn gioiTinhCol;
    @FXML
    private TableColumn diaChiCol;
    @FXML
    private TableColumn hoTenCol;
    @FXML
    private TableColumn ngaySinhCol;
    @FXML
    private CheckBox maleCheckBox;
    @FXML
    private CheckBox femaleCheckBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
