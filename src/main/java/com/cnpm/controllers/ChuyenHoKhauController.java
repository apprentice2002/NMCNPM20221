package com.cnpm.controllers;

import com.cnpm.entities.HoKhau;
import com.cnpm.utilities.Utilities;
import com.cnpm.utilities.chuyenHoKhauTableModel;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.cnpm.utilities.DBConnection.connection;

public class ChuyenHoKhauController implements Initializable {

    @FXML
    private TextField nhapMaHoKhauTxt;
    @FXML
    private TextField maHoKhauTxt;
    @FXML
    private TextField tenChuHoTxt;
    @FXML
    private TextField maKhuVucTxt;
    @FXML
    private TextField diaChiHienTaiTxt;
    @FXML
    private TextField diaChiChuyenDenTxt;
    @FXML
    private TextArea lyDoChuyenDiTxt;
    @FXML
    private Button submitBtn;
    @FXML
    private Button cancleBtn;
    @FXML
    private TableView hoKhauTable;
    @FXML
    private TableColumn maChuHoCol;
    @FXML
    private TableColumn hoTenChuHoCol;
    @FXML
    private TableColumn diaChiCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maChuHoCol.setCellValueFactory(new PropertyValueFactory<>("maChuHo"));
        hoTenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("hoTenChuHo"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        String query = "SELECT ho_khau.MaChuHo, nhan_khau.HoTen, ho_khau.DiaChiHoKhau FROM ho_khau, nhan_khau" +
                " WHERE ho_khau.MaHoKhau = nhan_khau.MaHo AND nhan_khau.MaNhanKhau = ho_khau.MaChuHo";
        try {
            Statement preparedStmtFindName = connection.createStatement();
            ResultSet rs = preparedStmtFindName.executeQuery(query);
            while(rs.next()) {
                chuyenHoKhauTableModel hk = new chuyenHoKhauTableModel();
                hk.setMaChuHo(rs.getString("MaChuHo"));
                hk.setHoTenChuHo(rs.getString("HoTen"));
                hk.setDiaChi(rs.getString("DiaChiHoKhau"));
                hoKhauTable.getItems().add(hk);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        FilteredList<chuyenHoKhauTableModel> filteredData = new FilteredList<>(hoKhauTable.getItems(), b->true);
        nhapMaHoKhauTxt.textProperty().addListener(((observable, oldValue, newValue)->{
            filteredData.setPredicate(hoKhau->{
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(hoKhau.getHoTenChuHo().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        } ));
        // Sắp xếp thứ tự
        SortedList<chuyenHoKhauTableModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(hoKhauTable.comparatorProperty());
        hoKhauTable.setItems(sortedData);
    }
    @FXML
    public void submit() {
        String diaChiChuyenDen = diaChiChuyenDenTxt.getText();
        String maHoKhau =  maHoKhauTxt.getText();
    // Cập nhật địa chỉ hộ khẩu
        try {
            String updateDiaChiSql = "UPDATE ho_khau SET DiaChiHoKhau = ? WHERE MaHoKhau = ?";
            PreparedStatement preparedStmtUpdateDiaChi = connection.prepareStatement(updateDiaChiSql);
            preparedStmtUpdateDiaChi.setString(1, diaChiChuyenDen);
            preparedStmtUpdateDiaChi.setString(2, maHoKhau);
            preparedStmtUpdateDiaChi.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void cancle(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/views/ho-khau.fxml");
    }
}
