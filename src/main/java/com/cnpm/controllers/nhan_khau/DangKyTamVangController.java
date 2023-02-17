package com.cnpm.controllers.nhan_khau;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DangKyTamVangController implements Initializable {

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Stage stage = null;

    @FXML private TextField co_quan_khai_bao;
    @FXML private TextArea ly_do;
    @FXML private TextField ma_nhan_khau;
    @FXML private DatePicker ngay_bat_dau;
    @FXML private DatePicker ngay_ket_thuc;

    @FXML
    void huy(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void xacNhan(ActionEvent event) {
        String co_quan_khai_bao = this.co_quan_khai_bao.getText();
        String ma_nhan_khau = this.ma_nhan_khau.getText();
        String ly_do = this.ly_do.getText();

        if (ma_nhan_khau.equals("") || co_quan_khai_bao.equals("") || this.ngay_bat_dau == null || this.ngay_ket_thuc == null || ly_do.equals("")) {
            Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/thong-bao/alert.fxml");
        } else {
            Date ngay_bat_dau = Date.valueOf(this.ngay_bat_dau.getValue());
            Date ngay_ket_thuc = Date.valueOf(this.ngay_ket_thuc.getValue());
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, ma_nhan_khau);
                preparedStatement.setString(2, co_quan_khai_bao);
                preparedStatement.setDate(3, ngay_bat_dau);
                preparedStatement.setDate(4, ngay_ket_thuc);
                preparedStatement.setString(5, ly_do);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void getQuery() {
        query = "INSERT INTO tam_tru (maNhanKhau, coQuanKhaiBao, ngayBatDau, ngayKetThuc, lyDo) VALUES (?, ?, ?, ?, ?)";
    }
}
