package com.cnpm.controllers;

import com.cnpm.entities.HoKhau;
import com.cnpm.utilities.Utilities;
import com.cnpm.utilities.thayDoiNhanKhauTableModel;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.cnpm.utilities.DBConnection.connection;

public class LichSuThayDoiController implements Initializable {
    @FXML
    private TableView thayDoiTable;
    @FXML
    private TableColumn maHoKhauCol;
    @FXML
    private TableColumn tenChuHoCol;
    @FXML
    private TableColumn themNhanKhauCol;
    @FXML
    private TableColumn xoaNhanKhauCol;
    @FXML
    private TableColumn ngayThayDoiCol;
    @FXML
    private TableColumn ghiChuCol;
    @FXML
    private TextField findTxt;
    @FXML
    private TextField maHoKhauTxt;
    @FXML
    private TextField tenChuHoTxt;
    @FXML
    private TextField nhanKhauTxt;
    @FXML
    private TextField ghiChuTxt;
    @FXML
    private Button cancleBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sqlQuery = "SELECT ho_khau.MaHoKhau, nhan_khau.HoTen, lich_su_thay_doi.NgayThayDoi," +
                "                lich_su_thay_doi_ho_khau.ThemNhanKhau, lich_su_thay_doi_ho_khau.XoaNhanKhau, lich_su_thay_doi.GhiChu FROM " +
                "                lich_su_thay_doi, lich_su_thay_doi_ho_khau, ho_khau, nhan_khau WHERE" +
                "                ho_khau.MaHoKhau = nhan_khau.MaHo AND lich_su_thay_doi.MaLSTD = lich_su_thay_doi_ho_khau.MaLSTD AND" +
                "               nhan_khau.MaNhanKhau = lich_su_thay_doi_ho_khau.MaNhanKhau AND ho_khau.MaHoKhau = lich_su_thay_doi_ho_khau.MaHoKhau" +
                "               ";

        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        ngayThayDoiCol.setCellValueFactory(new PropertyValueFactory<>("ngayThayDoi"));
        tenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("tenChuHo"));
        themNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("themNhanKhau"));
        xoaNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("xoaNhanKhau"));
        ghiChuCol.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));

        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(sqlQuery);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                thayDoiTable.getItems().add(new thayDoiNhanKhauTableModel(
                        queryResult.getString("MaHoKhau"),
                        queryResult.getString("HoTen"),
                        queryResult.getDate("NgayThayDoi"),
                        queryResult.getInt("ThemNhanKhau"),
                        queryResult.getInt("XoaNhanKhau"),
                        queryResult.getString("GhiChu")));
            }
            thayDoiTable.setItems(thayDoiTable.getItems());
            FilteredList<thayDoiNhanKhauTableModel> filteredData = new FilteredList<>(thayDoiTable.getItems(), b->true);
            findTxt.textProperty().addListener(((observable, oldValue, newValue)->{
                filteredData.setPredicate(thayDoiNhanKhauTableModel->{
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if(thayDoiNhanKhauTableModel.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if(thayDoiNhanKhauTableModel.getTenChuHo().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;
                });
            } ));
            // Sắp xếp thứ tự
            SortedList<thayDoiNhanKhauTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(thayDoiTable.comparatorProperty());
            thayDoiTable.setItems(sortedData);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void cancle(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/views/ho-khau.fxml", "Hộ khẩu",720, 600);
    }

}
