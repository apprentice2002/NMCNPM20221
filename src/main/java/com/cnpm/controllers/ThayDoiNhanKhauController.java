package com.cnpm.controllers;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ThayDoiNhanKhauController implements Initializable {

    @FXML private TextField bi_danh;
    @FXML private TextField dan_toc;
    @FXML private TextField dia_chi_hien_nay;
    @FXML private TextField gioi_tinh;
    @FXML private TextField ho_ten;
    @FXML private TextField input_ma_nhan_khau;
    @FXML private DatePicker input_ngay_sinh;
    @FXML private DatePicker ngay_sinh;
    @FXML private TextField nghe_nghiep;
    @FXML private TextField nguyen_quan;
    @FXML private TextField noi_lam_viec;
    @FXML private TextField noi_sinh;
    @FXML private TextField noi_thuong_tru;
    @FXML private TextField quoc_tich;
    @FXML private TextField so_dien_thoai;
    @FXML private TextField tien_an;
    @FXML private TextField ton_giao;
    @FXML private TextField trinh_do_chuyen_mon;
    @FXML private TextField trinh_do_ngoai_ngu;
    @FXML private ChoiceBox<String> da_xoa;

    String query_find = "", query_update = "";
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Stage stage = null;
    int id = 0;

    @FXML
    public void capNhat(ActionEvent event) {
        if (id != 0) {
            String noi_thuong_tru = this.noi_thuong_tru.getText();
            String dia_chi_hien_nay = this.dia_chi_hien_nay.getText();
            String trinh_do_chuyen_mon = this.trinh_do_chuyen_mon.getText();
            String trinh_do_ngoai_ngu = this.trinh_do_ngoai_ngu.getText();
            String tien_an = this.tien_an.getText();
            String so_dien_thoai = this.so_dien_thoai.getText();
            String ho_ten = this.ho_ten.getText();
            String bi_danh = this.bi_danh.getText();
            String gioi_tinh = this.gioi_tinh.getText();
            String nghe_nghiep = this.nghe_nghiep.getText();
            String noi_lam_viec = this.noi_lam_viec.getText();
            String nguyen_quan = this.nguyen_quan.getText();
            String dan_toc = this.dan_toc.getText();
            String ton_giao = this.ton_giao.getText();
            String noi_sinh = this.noi_sinh.getText();
            String quoc_tich = this.quoc_tich.getText();
            if (ho_ten.equals("") || this.ngay_sinh.getValue() == null || nguyen_quan.equals("") || quoc_tich.equals("") ||
                    gioi_tinh.equals("") || dia_chi_hien_nay.equals("")) {
                Utilities.popNewWindow(event, "/com/cnpm/scenes/alert.fxml");
            } else {
                Date ngay_sinh = Date.valueOf(this.ngay_sinh.getValue());
                query_update = "UPDATE nhan_khau SET hoTen=?, bietDanh=?, namSinh=?, gioiTinh=?, noiSinh=?, nguyenQuan=?, " +
                        "danToc=?, tonGiao=?, quocTich=?, noiThuongTru=?, diaChiHienNay=?, trinhDoChuyenMon=?, " +
                        "trinhDoNgoaiNgu=?, ngheNghiep=?, noiLamViec=?, tienAn=?, soDienThoai=? WHERE ID=?";

                try {
                    connection = DBConnection.getConnection();
                    preparedStatement = connection.prepareStatement(query_update);

                    preparedStatement.setString(1, ho_ten);
                    preparedStatement.setString(2, bi_danh);
                    preparedStatement.setDate(3, ngay_sinh);
                    preparedStatement.setString(4, gioi_tinh);
                    preparedStatement.setString(5, noi_sinh);
                    preparedStatement.setString(6, nguyen_quan);
                    preparedStatement.setString(7, dan_toc);
                    preparedStatement.setString(8, ton_giao);
                    preparedStatement.setString(9, quoc_tich);
                    preparedStatement.setString(10, noi_thuong_tru);
                    preparedStatement.setString(11, dia_chi_hien_nay);
                    preparedStatement.setString(12, trinh_do_chuyen_mon);
                    preparedStatement.setString(13, trinh_do_ngoai_ngu);
                    preparedStatement.setString(14, nghe_nghiep);
                    preparedStatement.setString(15, noi_lam_viec);
                    preparedStatement.setString(16, tien_an);
                    preparedStatement.setString(17, so_dien_thoai);
                    preparedStatement.setInt(18, id);

                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Utilities.popNewWindow(event, "/com/cnpm/scenes/alert.fxml");
        }
    }

    @FXML
    public void huy(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void timKiem(ActionEvent event) {
        int id_nhan_khau = Integer.parseInt(this.input_ma_nhan_khau.getText());
        if (id_nhan_khau == 0 ) {
            Utilities.popNewWindow(event, "/com/cnpm/scenes/alert.fxml");
        } else {
            query_find = "SELECT * FROM nhan_khau WHERE ID='" + id_nhan_khau + "'";
            try {
                connection = DBConnection.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query_find);
                System.out.println(resultSet);
                if (resultSet == null || resultSet.equals("")) {
                    Utilities.popNewWindow(event, "/com/cnpm/scenes/alert.fxml");
                }
                while (resultSet.next()) {
                    id = resultSet.getInt("ID");

                    ho_ten.setText(resultSet.getString("hoTen"));
                    bi_danh.setText(resultSet.getString("bietDanh"));
                    ngay_sinh.setValue(resultSet.getDate("namSinh").toLocalDate());
                    gioi_tinh.setText(resultSet.getString("gioiTinh"));
                    noi_sinh.setText(resultSet.getString("noiSinh"));
                    nguyen_quan.setText(resultSet.getString("nguyenQuan"));
                    dan_toc.setText(resultSet.getString("danToc"));
                    quoc_tich.setText(resultSet.getString("quocTich"));
                    ton_giao.setText(resultSet.getString("tonGiao"));
                    noi_thuong_tru.setText(resultSet.getString("noiThuongTru"));
                    dia_chi_hien_nay.setText(resultSet.getString("diaChiHienNay"));
                    trinh_do_chuyen_mon.setText(resultSet.getString("trinhDoChuyenMon"));
                    trinh_do_ngoai_ngu.setText(resultSet.getString("trinhDoNgoaiNgu"));
                    nghe_nghiep.setText(resultSet.getString("ngheNghiep"));
                    noi_lam_viec.setText(resultSet.getString("noiLamViec"));
                    so_dien_thoai.setText(resultSet.getString("soDienThoai"));
                    tien_an.setText(resultSet.getString("tienAn"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
