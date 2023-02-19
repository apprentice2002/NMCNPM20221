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

    @FXML private TextField noi_tam_tru;
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
        String noi_tam_tru = this.noi_tam_tru.getText();
        String ma_nhan_khau = this.ma_nhan_khau.getText();
        String ly_do = this.ly_do.getText();

        if (ma_nhan_khau.equals("") || noi_tam_tru.equals("") || this.ngay_bat_dau.getValue() == null || this.ngay_ket_thuc.getValue() == null || ly_do.equals("")) {
            Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/thong-bao/alert.fxml");
        } else {
            Date ngay_bat_dau = Date.valueOf(this.ngay_bat_dau.getValue());
            Date ngay_ket_thuc = Date.valueOf(this.ngay_ket_thuc.getValue());
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, ma_nhan_khau);
                preparedStatement.setString(2, "");
                preparedStatement.setString(3, noi_tam_tru);
                preparedStatement.setDate(4, ngay_bat_dau);
                preparedStatement.setDate(5, ngay_ket_thuc);
                preparedStatement.setString(6, ly_do);
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
        query = "INSERT INTO tam_vang (idNhanKhau, maGiayTamVang, noiTamTru, tuNgay, denNgay, lyDo) VALUES (?, ?, ?, ?, ?, ?)";
    }
}
