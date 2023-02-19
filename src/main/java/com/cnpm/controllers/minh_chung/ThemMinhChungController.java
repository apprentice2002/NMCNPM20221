package com.cnpm.controllers.minh_chung;

import com.cnpm.entities.MinhChung;
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
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;


public class ThemMinhChungController implements Initializable {
    @FXML
    private TextField hoTen;
    @FXML
    private TextField namSinh;
    @FXML
    private Button btn_cancel_them;

    @FXML
    private Button btn_confirm_them;
    @FXML
    private Label errorLab;

    @FXML
    private TextField them_ma_nhan_khau;

    @FXML
    private TextField them_truong;

    @FXML
    private TextField them_lop;

    @FXML
    private TextField them_thanh_tich_hoc_tap;


    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    @FXML
    public void huy(ActionEvent event) throws IOException {
        Stage stage = (Stage) btn_cancel_them.getScene().getWindow();
        stage.close();
    }
    public void xuatHien() {
        them_ma_nhan_khau.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                int ID = Integer.parseInt(them_ma_nhan_khau.getText());
                    try {
                        connection = DBConnection.getConnection();
                        PreparedStatement stmt = connection.prepareStatement(
                                "SELECT hoTen, namSinh " +
                                        "FROM nhan_khau " +
                                        "WHERE ID = ?"
                        );
                        stmt.setInt(1, ID);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            errorLab.setText("");
                            hoTen.setText(rs.getString("hoTen"));
                            namSinh.setText(rs.getString("namSinh"));
                        } else {
                            errorLab.setText("Không tồn tại nhân khẩu này !");
                            hoTen.setText("");
                            namSinh.setText("");
                        }
                        rs.close();
                        stmt.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
        });
    }
    @FXML
    public void xacNhan(ActionEvent event) throws IOException {

        String truong = them_truong.getText();
        String lop = them_lop.getText();
        String thanhTichHocTap = them_thanh_tich_hoc_tap.getText();
        Date ngayKhaiBao = new Date(System.currentTimeMillis());
        if(them_ma_nhan_khau.getText().equals("")){
            errorLab.setText("Nhập mã nhân khẩu và nhấn Enter!");
        }
        else if ( lop.equals("") || truong.equals("") || thanhTichHocTap.equals("")||them_ma_nhan_khau.getText().equals("")) {
            errorLab.setText("Vui lòng điền đủ thông tin!");
        } else {
            errorLab.setText("");
            int ma_nhan_khau = Integer.parseInt(them_ma_nhan_khau.getText());
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, ma_nhan_khau);
                preparedStatement.setString(2, truong);
                preparedStatement.setString(3, lop);
                preparedStatement.setString(4, thanhTichHocTap);
                preparedStatement.setDate(5, ngayKhaiBao);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage)btn_confirm_them .getScene().getWindow();
            stage.close();

        }
    }
    private void getQuery() {
        query = "INSERT INTO minh_chung ( ma_nhan_khau, truong, lop, thanhTichHocTap,ngayKhaiBao)" +
                " VALUES (?,?,?,?,?)";
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLab.setText("");
        xuatHien();
    }

}
