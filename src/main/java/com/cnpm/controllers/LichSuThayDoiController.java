package com.cnpm.controllers;

import com.cnpm.utilities.HoKhauTableModel;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    @FXML
    private ChoiceBox<String> optionChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sqlQuery = "SELECT ho_khau.ID, nhan_khau.hoTen, lich_su_thay_doi.NgayThayDoi," +
                "                lich_su_thay_doi_ho_khau.ThemNhanKhau, lich_su_thay_doi_ho_khau.XoaNhanKhau, lich_su_thay_doi.GhiChu FROM " +
                "                lich_su_thay_doi, thanh_vien_cua_ho, lich_su_thay_doi_ho_khau, ho_khau, nhan_khau WHERE" +
                "                ho_khau.ID = thanh_vien_cua_ho.idHoKhau and nhan_khau.ID = thanh_vien_cua_ho.idNhanKhau AND lich_su_thay_doi.ID = lich_su_thay_doi_ho_khau.IdLSTD AND" +
                "               nhan_khau.ID = lich_su_thay_doi_ho_khau.IdNhanKhau";

        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        ngayThayDoiCol.setCellValueFactory(new PropertyValueFactory<>("ngayThayDoi"));
        tenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("hoTenChuHo"));
        themNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("themNhanKhau"));
        xoaNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("xoaNhanKhau"));
        ghiChuCol.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));

        optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm theo ID");
        optionChoiceBox.getSelectionModel().selectFirst();

        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<HoKhauTableModel> filteredData = new FilteredList<>(thayDoiTable.getItems(), b->true);
            findTxt.textProperty().addListener(((observable1, oldValue1, newValue1)->{
                filteredData.setPredicate(hoKhau->{
                    if(newValue1.isEmpty() || newValue1.isBlank() || newValue1 == null) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if(hoKhau.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo ID" ) {
                        return true;
                    } else if(hoKhau.getHoTenChuHo().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên") {
                        return true;
                    } else
                        return false;
                });
            } ));
            // Sắp xếp thứ tự
            SortedList<HoKhauTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(thayDoiTable.comparatorProperty());
            thayDoiTable.setItems(sortedData);
        });


        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(sqlQuery);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                thayDoiTable.getItems().add(new HoKhauTableModel(
                        queryResult.getString("ID"),
                        queryResult.getString("hoTen"),
                        queryResult.getDate("NgayThayDoi"),
                        queryResult.getString("ThemNhanKhau"),
                        queryResult.getString("XoaNhanKhau"),
                        queryResult.getString("GhiChu")));
            }
            thayDoiTable.setItems(thayDoiTable.getItems());

            FilteredList<HoKhauTableModel> filteredData = new FilteredList<>(thayDoiTable.getItems(), b->true);
            findTxt.textProperty().addListener(((observable, oldValue, newValue)->{
                filteredData.setPredicate(HoKhauTableModel->{
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if(HoKhauTableModel.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if(HoKhauTableModel.getHoTenChuHo().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;
                });
            } ));
            // Sắp xếp thứ tự
            SortedList<HoKhauTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(thayDoiTable.comparatorProperty());
            thayDoiTable.setItems(sortedData);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void cancle(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }

}
