package com.cnpm.controllers;

import com.cnpm.entities.NhanKhauTableModel;
import com.cnpm.entities.SharedDataModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.cnpm.utilities.DBConnection.connection;

public class ChonNhanKhauController implements Initializable {
    @FXML
    private TableView nhanKhauTable;
    @FXML
    private TableColumn maNhanKhauCol;
    @FXML
    private TableColumn hoTenCol;
    @FXML
    private TableColumn quanHeVoiChuHoCol;
    @FXML
    private Button addNhanKhauBtn;
    private SharedDataModel sharedDataModel = new SharedDataModel();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        quanHeVoiChuHoCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));

        try {
            String nhanKhauSql = "SELECT DISTINCT nhan_khau.ID as MaNhanKhau, nhan_khau.hoTen FROM" +
                    " nhan_khau WHERE nhan_khau.daXoa is null EXCEPT (SELECT DISTINCT " +
                    "nhan_khau.ID as MaNhanKhau, nhan_khau.hoTen FROM nhan_khau," +
                    "thanh_vien_cua_ho WHERE nhan_khau.ID = thanh_vien_cua_ho.idNhanKhau AND " +
                    "thanh_vien_cua_ho.quanHeVoiChuHo is not null);";
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(nhanKhauSql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                NhanKhauTableModel nhanKhau = new NhanKhauTableModel(queryResult.getString("MaNhanKhau"),
                        queryResult.getString("hoTen"),"");
                nhanKhauTable.getItems().add(nhanKhau);
            }
            maNhanKhauCol.setSortType(TableColumn.SortType.ASCENDING);
            nhanKhauTable.getSortOrder().clear();
            nhanKhauTable.getSortOrder().add(maNhanKhauCol);
            nhanKhauTable.sort();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addNhanKhauBtn.setOnAction(event -> {
            ObservableList<NhanKhauTableModel> selectedRows = nhanKhauTable.getSelectionModel().getSelectedItems();
            for (NhanKhauTableModel selectedRow : selectedRows) {
                sharedDataModel.addSelectedRow(selectedRow);
            }
            nhanKhauTable.getSelectionModel().clearSelection();
        });
    }

    public void setSharedDataModel(SharedDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
    }
}
