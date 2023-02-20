package com.cnpm.controllers.ho_khau;

import com.cnpm.entities.NhanKhauTableModel;
import com.cnpm.utilities.DBConnection;
import com.cnpm.entities.HoKhauTableModel;
import com.cnpm.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HoKhauController implements Initializable {
    @FXML
    private Button them_ho_khau;
    @FXML
    private Button tach_ho_khau;
    @FXML
    private Button chuyen_ho_khau;

    @FXML
    private Button tim_kiem_ho_khau;

    @FXML
    private ChoiceBox<String> optionChoiceBox;

    @FXML
    private Button findBtn;
    @FXML
    private Label errorLab;
    @FXML
    private TextField keywordTextField;

    @FXML
    private TableView<HoKhauTableModel> table;
    @FXML
    private TableColumn<HoKhauTableModel, String> maHoKhauCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> hoTenChuHoCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> diaChiCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> soThanhVienCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> xoaCol;

    ObservableList<HoKhauTableModel> listView = FXCollections.observableArrayList();

    public void refreshTable() {
        table.getItems().clear();
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT ho_khau.ID,nhan_khau.hoTen AS hoTenChuHo, diaChi, COUNT(thanh_vien_cua_ho.idHoKhau) " +
                "AS soThanhVien FROM ho_khau, thanh_vien_cua_ho, nhan_khau " +
                "WHERE ho_khau.idChuHo = nhan_khau.ID and thanh_vien_cua_ho.idHoKhau = ho_khau.ID " +
                "AND ho_khau.daXoa is NULL and ho_khau.ngayChuyenDi is null " +
                "GROUP BY thanh_vien_cua_ho.idHoKhau";
        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                listView.add(new HoKhauTableModel(queryResult.getString("ID"),
                        queryResult.getString("hoTenChuHo"),
                        queryResult.getString("diaChi"),
                        queryResult.getString("SoThanhVien")));
            }
            table.setItems(listView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLab.setText("");

        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        hoTenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("hoTenChuHo"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChiHoKhau"));
        soThanhVienCol.setCellValueFactory(new PropertyValueFactory<>("soThanhVien"));

        optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm theo ID", "Tìm theo số thành viên");
        optionChoiceBox.setValue("Chọn tìm kiếm theo ...");


        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<HoKhauTableModel> filteredData = new FilteredList<>(listView, b->true);
            keywordTextField.textProperty().addListener(((observable1, oldValue1, newValue1)->{
                filteredData.setPredicate(hoKhau->{
                    if(newValue1.isEmpty() || newValue1.isBlank() || newValue1 == null) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if(hoKhau.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo ID" ) {
                        return true;
                    } else if(hoKhau.getHoTenChuHo().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên") {
                        return true;
                    } else if(hoKhau.getSoThanhVien().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo số thành viên") {
                        return true;
                    } else
                        return false;
                });
            } ));
            // Sắp xếp thứ tự
            SortedList<HoKhauTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });

        refreshTable();

    }
    @FXML
    public void themHoKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/them-ho-khau.fxml");
    }
    public void doiChuHo(ActionEvent event) throws  IOException {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/doi-chu-ho.fxml");
    }

    public void tachHoKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/tach-ho-khau.fxml");
    }

    public void chuyenHoKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/chuyen-ho-khau.fxml");
    }
    public void lichSuThayDoi(ActionEvent event) {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/lich-su-thay-doi.fxml");
    }


    public void themThanhVien(ActionEvent event) {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/them-thanh-vien.fxml");
    }
}
