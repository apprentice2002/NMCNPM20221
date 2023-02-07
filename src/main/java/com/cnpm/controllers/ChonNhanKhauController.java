package com.cnpm.controllers;

import com.cnpm.utilities.NhanKhauTableModel;
import com.cnpm.utilities.SharedDataModel;
import com.cnpm.utilities.themHoKhauNhanKhauTableModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import static com.cnpm.utilities.DBConnection.connection;
import static com.cnpm.utilities.DBConnection.getConnection;

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
    @FXML
    private Button deleteNhanKhauBtn;
    @FXML
    private Button deleteAllNhanKhauBtn;

    private SharedDataModel sharedDataModel = new SharedDataModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        quanHeVoiChuHoCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));

        try {
            String nhanKhauSql = "SELECT DISTINCT nhan_khau.ID as MaNhanKhau, hoTen FROM nhan_khau";
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(nhanKhauSql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                themHoKhauNhanKhauTableModel nhanKhau = new themHoKhauNhanKhauTableModel();
                nhanKhau.setMaNhanKhau(queryResult.getString("MaNhanKhau"));
                nhanKhau.setNgaySinh(queryResult.getDate("hoTen"));
                nhanKhau.setQuanHeVoiChuHo("");
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
            ObservableList<themHoKhauNhanKhauTableModel> selectedRows = nhanKhauTable.getSelectionModel().getSelectedItems();
            for (themHoKhauNhanKhauTableModel selectedRow : selectedRows) {
                if(sharedDataModel.getSelectedRows().contains(selectedRow)) break;
                sharedDataModel.addSelectedRow(selectedRow);
            }
            nhanKhauTable.getSelectionModel().clearSelection();
        });

        deleteNhanKhauBtn.setOnAction(event -> {
            ObservableList<themHoKhauNhanKhauTableModel> selectedItems = nhanKhauTable.getSelectionModel().getSelectedItems();
            for (int i = 0 ; i < sharedDataModel.getSelectedRows().size(); i++) {
                if(sharedDataModel.getSelectedRows().get(i).getMaNhanKhau().equals(selectedItems.get(0).getMaNhanKhau()))
                    sharedDataModel.getSelectedRows().remove(i);
            }
            nhanKhauTable.getSelectionModel().clearSelection();
        });

        deleteAllNhanKhauBtn.setOnAction(event -> {
            sharedDataModel.removeAllRow();
            nhanKhauTable.getSelectionModel().clearSelection();
        });
        }

    public void setSharedDataModel(SharedDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
    }
}
