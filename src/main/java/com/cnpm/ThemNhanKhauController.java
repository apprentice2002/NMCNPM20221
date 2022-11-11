package com.cnpm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;

public class ThemNhanKhauController implements Initializable {
    @FXML
    private Button btn_cancel_them;

    @FXML
    private Button btn_confirm_them;

    @FXML
    private Label note;

    @FXML
    private TextField them_bi_danh;

    @FXML
    private TextField them_cmnd;

    @FXML
    private TextField them_dan_toc;

    @FXML
    private TextField them_gioi_tinh;

    @FXML
    private TextField them_ho_ten;

    @FXML
    private DatePicker them_ngay_cap;

    @FXML
    private DatePicker them_ngay_chuyen_den;

    @FXML
    private DatePicker them_ngay_sinh;

    @FXML
    private TextField them_nghe_nghiep;

    @FXML
    private TextField them_nguyen_quan;

    @FXML
    private TextField them_noi_lam_viec;

    @FXML
    private TextField them_noi_sinh;

    @FXML
    private TextField them_noi_thuong_tru_truoc;

    @FXML
    private TextField them_quan_he_chu_ho;

    @FXML
    private TextField them_quoc_tich;

    @FXML
    private TextField them_so_dien_thoai;

    @FXML
    private TextField them_ton_giao;

    @FXML
    private TextField them_trang_thai;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    NhanKhau nhanKhau = null;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    @FXML
    public void huy(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "home.fxml", "Trang chu");
    }

    @FXML
    public void xacNhan(ActionEvent event) throws IOException {
        String ho_ten = them_ho_ten.getText();
        String bi_danh = them_bi_danh.getText();
        String gioi_tinh = them_gioi_tinh.getText();
        String so_dien_thoai = them_so_dien_thoai.getText();
        String trang_thai = them_trang_thai.getText();
        String quan_he_voi_chu_ho = them_quan_he_chu_ho.getText();
        String nghe_nghiep = them_nghe_nghiep.getText();
        String noi_lam_viec = them_noi_lam_viec.getText();
        String nguyen_quan = them_nguyen_quan.getText();
        String dan_toc = them_dan_toc.getText();
        String ton_giao = them_ton_giao.getText();
        String noi_sinh = them_noi_sinh.getText();
        String noi_thuong_tru_truoc = them_noi_thuong_tru_truoc.getText();
        String quoc_tich = them_quoc_tich.getText();
        String cmnd = them_cmnd.getText();
        Date ngay_cap = Date.valueOf(them_ngay_cap.getValue());
        Date ngay_sinh = Date.valueOf(them_ngay_sinh.getValue());
        Date ngay_chuyen_den = Date.valueOf(them_ngay_chuyen_den.getValue());
        if (ho_ten.equals("") || formatter.format(ngay_sinh).equals("") || noi_sinh.equals("") || quoc_tich.equals("") ||
                gioi_tinh.equals("") || trang_thai.equals("") || quan_he_voi_chu_ho.equals("")) {
            note.setText("Vui lòng điền đủ thông tin cần thiết");
        } else {
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, ho_ten);
                preparedStatement.setString(2, bi_danh);
                preparedStatement.setDate(3, ngay_sinh);
                preparedStatement.setString(4, noi_sinh);
                preparedStatement.setString(5, gioi_tinh);
                preparedStatement.setString(6, dan_toc);
                preparedStatement.setString(7, quoc_tich);
                preparedStatement.setString(8, ton_giao);
                preparedStatement.setString(9, nguyen_quan);
                preparedStatement.setString(10, nghe_nghiep);
                preparedStatement.setString(11, noi_lam_viec);
                preparedStatement.setString(12, so_dien_thoai);
                preparedStatement.setString(13, cmnd);
                preparedStatement.setDate(14, ngay_cap);
                preparedStatement.setDate(15, ngay_chuyen_den);
                preparedStatement.setString(16, noi_thuong_tru_truoc);
                preparedStatement.setString(17, trang_thai);
                preparedStatement.setString(18, quan_he_voi_chu_ho);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Utilities.changeScene(event, "home.fxml", "Trang chu");
        }
    }

    private void getQuery() {
        query = "INSERT INTO nhan_khau (ho_ten,bi_danh, ngay_sinh, noi_sinh, gioi_tinh, dan_toc, quoc_tich, " +
                "ton_giao, nguyen_quan, nghe_nghiep, noi_lam_viec, so_dien_thoai, cmnd, ngay_cap, ngay_chuyen_den, " +
                "noi_thuong_tru_truoc, trang_thai, quan_he_voi_chu_ho) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
