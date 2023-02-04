package com.cnpm.controllers;

import com.cnpm.entities.HoKhau;
import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HoKhauController implements Initializable {
    @FXML
    private Button chinh_sua_ho_khau;
    @FXML
    private Button them_ho_khau;
    @FXML
    private Button tach_ho_khau;
    @FXML
    private Button chuyen_ho_khau;

    @FXML
    private Button tim_kiem_ho_khau;

    @FXML
    private Button xem_ho_khau;
    @FXML
    private TextField keywordTextField;

    @FXML
    private TableView<HoKhau> table;
    @FXML
    private TableColumn<HoKhau, String> maHoKhauCol;
    @FXML
    private TableColumn<HoKhau, String> ngayTaoCol;
    @FXML
    private TableColumn<HoKhau, String> diaChiCol;
    @FXML
    private TableColumn<HoKhau, String> maChuHoCol;
    @FXML
    private TableColumn<HoKhau, String> soThanhVienCol;

    ObservableList<HoKhau> listView = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM ho_khau";

        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        ngayTaoCol.setCellValueFactory(new PropertyValueFactory<>("ngayTao"));
        maChuHoCol.setCellValueFactory(new PropertyValueFactory<>("maChuHo"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChiHoKhau"));
        soThanhVienCol.setCellValueFactory(new PropertyValueFactory<>("soThanhVien"));


        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                listView.add(new HoKhau(queryResult.getString("MaHoKhau"),
                        queryResult.getString("NgayTao"),
                        queryResult.getString("MaChuHo"),
                        queryResult.getString("DiaChiHoKhau"),
                        queryResult.getString("SoThanhVien")));
            }
            table.setItems(listView);
            //Lọc theo TextField
            FilteredList<HoKhau> filteredData = new FilteredList<>(listView, b->true);
            keywordTextField.textProperty().addListener(((observable, oldValue, newValue)->{
                filteredData.setPredicate(hoKhau->{
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if(hoKhau.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if(hoKhau.getMaChuHo().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if(hoKhau.getSoThanhVien().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                    return false;
                });
            } ));
            // Sắp xếp thứ tự
            SortedList<HoKhau> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void themHoKhau(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/scenes/them-ho-khau.fxml", "Thêm hộ khẩu", 720, 600);
    }


    public void tachHoKhau(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/scenes/tach-ho-khau.fxml", "Tách hộ khẩu",720, 600);
    }


    public void xoaHoKhau(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/scenes/them-ho-khau.fxml", "Xóa hộ khẩu",720, 600);
    }

    public void chuyenHoKhau(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/scenes/chuyen-ho-khau.fxml", "Chuyển hộ khẩu",720, 600);
    }
}
