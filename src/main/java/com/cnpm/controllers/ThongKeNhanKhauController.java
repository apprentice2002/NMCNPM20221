package com.cnpm.controllers;

import java.sql.PreparedStatement;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.HoKhauTableModel;
import com.cnpm.utilities.NhanKhauTableModel;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.cnpm.utilities.DBConnection.connection;

public class ThongKeNhanKhauController implements Initializable {
    @FXML
    private TextField fromAgeTxt;
    @FXML
    private TextField toAgeTxt;
    @FXML
    private TextField nhanKhauThongKeTxt;
    @FXML
    private DatePicker fromYearTxt;
    @FXML
    private DatePicker toYearTxt;
    @FXML
    private Button cancleBtn;
    @FXML
    private Label errorLab;
    @FXML
    private TableView<NhanKhauTableModel> nhanKhauTable;
    @FXML
    private TableColumn maNhanKhauCol;
    @FXML
    private TableColumn tuoiCol;
    @FXML
    private TableColumn gioiTinhCol;
    @FXML
    private TableColumn maHoKhauCol;
    @FXML
    private TableColumn hoTenCol;
    @FXML
    private TableColumn ngaySinhCol;
    @FXML
    private CheckBox maleCheckBox;
    @FXML
    private CheckBox femaleCheckBox;

    public void refreshTable() {
        nhanKhauTable.getItems().clear();
        Connection connection = DBConnection.getConnection();
        String nhanKhauSql = "SELECT nhan_khau.ID,nhan_khau.hoTen,nhan_khau.gioiTinh, " +
                "(YEAR(CURDATE()) - YEAR(nhan_khau.namSinh)) as tuoi, ho_khau.maHoKhau" +
                " from nhan_khau, ho_khau, thanh_vien_cua_ho where ho_khau.ID = thanh_vien_cua_ho.idHoKhau " +
                "and nhan_khau.ID = thanh_vien_cua_ho.idNhanKhau and ho_khau.daXoa is NULL and " +
                "ho_khau.ngayChuyenDi is null and nhan_khau.daXoa is null";
        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(nhanKhauSql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                nhanKhauTable.getItems().add(new NhanKhauTableModel(
                        queryResult.getString("ID"),
                        queryResult.getString("hoTen"),
                        queryResult.getString("gioiTinh"),
                        queryResult.getInt("tuoi"),
                        queryResult.getString("maHoKhau")
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        maNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        tuoiCol.setCellValueFactory(new PropertyValueFactory<>("tuoi"));
        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        gioiTinhCol.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        refreshTable();

        // Set filter cho tuổi và giới tính
        FilteredList<NhanKhauTableModel> filteredData = new FilteredList<>(nhanKhauTable.getItems(), p -> true);
        maleCheckBox.setOnAction(event -> {
            if (maleCheckBox.isSelected()) {
                femaleCheckBox.setSelected(false);
            }
        });

        femaleCheckBox.setOnAction(event -> {
            if (femaleCheckBox.isSelected()) {
                maleCheckBox.setSelected(false);
            }
        });

        ChangeListener<Boolean> checkBoxListener = (observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                try {
                    errorLab.setText("");
                    int age = person.getTuoi();
                    if (fromAgeTxt.getText().isEmpty() || toAgeTxt.getText().isEmpty()) {
                        if(!maleCheckBox.isSelected() && !femaleCheckBox.isSelected())
                            return true;
                        if (maleCheckBox.isSelected() && person.getGioiTinh().equals("Nam")) {
                            return true;
                        }
                        if (femaleCheckBox.isSelected() && person.getGioiTinh().equals("Nữ")) {
                            return true;
                        }
                    } else {
                        if(!maleCheckBox.isSelected() && !femaleCheckBox.isSelected())
                            return age >= Integer.parseInt(fromAgeTxt.getText()) && age <= Integer.parseInt(toAgeTxt.getText());
                        else if (maleCheckBox.isSelected())
                            return age >= Integer.parseInt(fromAgeTxt.getText()) && age <= Integer.parseInt(toAgeTxt.getText()) && person.getGioiTinh().equals("Nam");
                        else return age >= Integer.parseInt(fromAgeTxt.getText()) && age <= Integer.parseInt(toAgeTxt.getText())&& person.getGioiTinh().equals("Nữ");
                    }
                } catch(NumberFormatException e) {
                    errorLab.setText("Vui lòng chỉ nhập đúng dữ liệu !");
                }
                return false;
            });
        };
        maleCheckBox.selectedProperty().addListener(checkBoxListener);
        femaleCheckBox.selectedProperty().addListener(checkBoxListener);

        ChangeListener<String> textFieldListener = (observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                try {
                    errorLab.setText("");
                    int age = person.getTuoi();
                    if (fromAgeTxt.getText().isEmpty() || toAgeTxt.getText().isEmpty()) {
                        if(!maleCheckBox.isSelected() && !femaleCheckBox.isSelected())
                            return true;
                        if (maleCheckBox.isSelected() && person.getGioiTinh().equals("Nam")) {
                            return true;
                        }
                        if (femaleCheckBox.isSelected() && person.getGioiTinh().equals("Nữ")) {
                            return true;
                        }
                    } else {
                        if(Integer.parseInt(fromAgeTxt.getText())<0 || Integer.parseInt(toAgeTxt.getText())<0) throw new NumberFormatException("Vui lòng nhập số tuổi lớn hơn 0");
                        if(!maleCheckBox.isSelected() && !femaleCheckBox.isSelected())
                            return age >= Integer.parseInt(fromAgeTxt.getText()) && age <= Integer.parseInt(toAgeTxt.getText());
                        else if (maleCheckBox.isSelected())
                            return age >= Integer.parseInt(fromAgeTxt.getText()) && age <= Integer.parseInt(toAgeTxt.getText()) && person.getGioiTinh().equals("Nam");
                        else return age >= Integer.parseInt(fromAgeTxt.getText()) && age <= Integer.parseInt(toAgeTxt.getText())&& person.getGioiTinh().equals("Nữ");
                    }
                } catch(NumberFormatException e) {
                    errorLab.setText("Nhập dữ liệu không hợp lệ !");
                }
                return false;
            });
        };
        fromAgeTxt.textProperty().addListener(textFieldListener);
        toAgeTxt.textProperty().addListener(textFieldListener);
        nhanKhauTable.setItems(filteredData);

        // set số nhân khẩu thống kê TextField
        nhanKhauThongKeTxt.setText(String.valueOf(nhanKhauTable.getItems().size()));
        filteredData.addListener((ListChangeListener.Change<? extends NhanKhauTableModel> c) -> {
            nhanKhauThongKeTxt.setText(String.valueOf(nhanKhauTable.getItems().size()));
        });
    }

    public void cancle(ActionEvent event) {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }
}
