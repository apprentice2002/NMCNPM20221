package com.cnpm.controllers;

import com.cnpm.entities.NhanKhau;
import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.ResultSet;

public class NhanKhauController implements Initializable {

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ObservableList<NhanKhau> NhanKhauList = FXCollections.observableArrayList();
    NhanKhau nhanKhau = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    public void refresh() {
        NhanKhauList.clear();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                NhanKhauList.add(new NhanKhau(
                        resultSet.getInt("ID"),
                        resultSet.getString("hoTen"),
                        formatter.format(resultSet.getDate("ngaySinh")),
                        resultSet.getString("gioiTinh"),
                        resultSet.getString("diaChiHienNay")
                ));
            }
            tableView.setItems(NhanKhauList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void add(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/them-nhan-khau.fxml");
    }

    @FXML private TableColumn<NhanKhau, String> dia_chi_hien_nay;
    @FXML private TableColumn<NhanKhau, String> gioi_tinh;
    @FXML private TableColumn<NhanKhau, String> ho_ten;
    @FXML private TableColumn<NhanKhau, Integer> id;
    @FXML private TableColumn<NhanKhau, String> ngay_sinh;
    @FXML private TableView<NhanKhau> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();
    }

    private void load() {
        getQuery();
        connection = DBConnection.getConnection();

        dia_chi_hien_nay.setCellValueFactory(new PropertyValueFactory<>("dia_chi_hien_nay"));
        gioi_tinh.setCellValueFactory(new PropertyValueFactory<>("gioi_tinh"));
        ho_ten.setCellValueFactory(new PropertyValueFactory<>("ho_ten"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        ngay_sinh.setCellValueFactory(new PropertyValueFactory<>("ngay_sinh"));

        refresh();
    }

    private void getQuery() {
        query = "SELECT ID, hoTen, ngaySinh, gioiTinh, diaChiHienNay FROM nhan_khau";
    }
}
