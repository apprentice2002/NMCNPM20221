package com.cnpm.controllers.nhan_khau;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ThemNhanKhauController implements Initializable {

    @FXML private TextField bi_danh;
    @FXML private TextField dan_toc;
    @FXML private TextField dia_chi_hien_nay;
    @FXML private TextField gioi_tinh;
    @FXML private TextField ho_ten;
    @FXML private DatePicker ngay_sinh;
    @FXML private TextField nghe_nghiep;
    @FXML private TextField nguyen_quan;
    @FXML private TextField noi_lam_viec;
    @FXML private TextField noi_sinh;
    @FXML private TextField noi_thuong_tru;
    @FXML private TextField quoc_tich;
    @FXML private TextField tien_an;
    @FXML private TextField ton_giao;
    @FXML private TextField trinh_do_chuyen_mon;
    @FXML private TextField trinh_do_ngoai_ngu;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Stage stage = null;

    @FXML
    public void huy(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void xacNhan(ActionEvent event) {
        String noi_thuong_tru = this.noi_thuong_tru.getText();
        String dia_chi_hien_nay = this.dia_chi_hien_nay.getText();
        String trinh_do_chuyen_mon = this.trinh_do_chuyen_mon.getText();
        String trinh_do_ngoai_ngu = this.trinh_do_ngoai_ngu.getText();
        String tien_an = this.tien_an.getText();
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
            Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/thong-bao/alert.fxml");
        } else {
            Date ngay_sinh = Date.valueOf(this.ngay_sinh.getValue());
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);

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

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/thong-bao/thanh-cong.fxml");
        }
    }

    private void getQuery() {
        query = "INSERT INTO nhan_khau (hoTen, bietDanh, namSinh, gioiTinh, noiSinh, nguyenQuan, danToc, tonGiao, quocTich, " +
                "noiThuongTru, diaChiHienNay, trinhDoChuyenMon, trinhDoNgoaiNgu, ngheNghiep, noiLamViec, tienAn) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
