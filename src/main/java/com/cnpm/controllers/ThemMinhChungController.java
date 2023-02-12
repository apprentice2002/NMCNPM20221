package com.cnpm.controllers;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.cnpm.entities.MinhChung;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;


public class ThemMinhChungController implements Initializable {
    @FXML
    private Button btn_cancel_them;

    @FXML
    private Button btn_confirm_them;

    @FXML
    private TextField note;
    @FXML

    private TextField them_id_minh_chung;

    @FXML
    private TextField them_ma_nhan_khau;

    @FXML
    private TextField them_truong;

    @FXML
    private TextField them_lop;

    @FXML
    private TextField them_thanh_tich_hoc_tap;

    @FXML
    private DatePicker them_ngay_khai_bao;
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    MinhChung minhchung;
    PreparedStatement preparedStatement = null;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    @FXML
    public void huy(ActionEvent event) throws IOException {
        Stage stage = (Stage) btn_cancel_them.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void xacNhan(ActionEvent event) throws IOException {
        int idMinhChung = Integer.parseInt(them_id_minh_chung.getText());
        int ma_nhan_khau = Integer.parseInt(them_ma_nhan_khau.getText());
        String truong = them_truong.getText();
        String lop = them_lop.getText();
        String thanhTichHocTap = them_thanh_tich_hoc_tap.getText();
        Date ngayKhaiBao = Date.valueOf(them_ngay_khai_bao.getValue());
        if (formatter.format(ngayKhaiBao).equals("") || lop.equals("") ||
                truong.equals("") || thanhTichHocTap.equals("")) {
            note.setText("Vui lòng điền đủ thông tin cần thiết");
        } else {
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idMinhChung);
                preparedStatement.setInt(2, ma_nhan_khau);
                preparedStatement.setString(3, truong);
                preparedStatement.setString(4, lop);
                preparedStatement.setString(5, thanhTichHocTap);
                preparedStatement.setDate(6, ngayKhaiBao);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage)btn_confirm_them .getScene().getWindow();
            stage.close();

        }

    }
    private void getQuery() {
        query = "INSERT INTO minh_chung (idMinhChung, ma_nhan_khau, truong, lop, thanhTichHocTap,ngayKhaiBao)" +
                " VALUES (?,?,?,?,?,?)";
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
