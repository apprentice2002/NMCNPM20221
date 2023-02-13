package com.cnpm.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class XinCapGiayController implements Initializable {
    @FXML
    private TextField cmndTxt;
    @FXML
    private TextField maCapGiayTxt;
    @FXML
    private TextField diaChiTxt;
    @FXML
    private TextArea lyDoTxt;
    @FXML
    private Button cancleBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        cmndTxt.textProperty().addListener((observable, oldValue, newValue) -> {
//            if(newValue.isEmpty()|| newValue.isBlank()) {
//                cmndTxt.setText("");
//            } else {
//                //Tìm kiếm
//                String query = "SELECT SoCCCD, ho_khau.DiaChiHoKhau FROM ho_khau, can_cuoc_cong_dan, nhan_khau WHERE can_cuoc_cong_dan.MaNhanKhau = nhan_khau.MaNhanKhau AND ho_khau.MaHoKhau = nhan_khau.MaHo AND SoCCCD = ?";
//                try {
//                    PreparedStatement preparedStmtFindName = connection.prepareStatement(query);
//                    preparedStmtFindName.setString(1,cmndTxt.getText());
//                    ResultSet rs = preparedStmtFindName.executeQuery();
//                    if(rs.next()) {
//                        String noiTamTru = rs.getString("DiaChiHoKhau");
//                        diaChiTxt.setText(noiTamTru);
//                    } else {
//                        System.out.println("Không tìm thấy số CCCD tương ứng, vui lòng thử lại");
//                    }
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            // Tạo mã giấy cấp
//            try (ResultSet generatedKeys = preparedStmtInsert.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    String maHoKhau = generatedKeys.getString(1);
//                    // Cập nhật hộ khẩu cũ
//                    submitSql = "UPDATE nhan_khau SET MaHo = ? WHERE MaNhanKhau = ?";
//                    PreparedStatement preparedStmtUpdate = connection.prepareStatement(submitSql);
//                    // Cập nhật thàn viên hộ khẩu mới.
//                    for (NhanKhauTableModel nhanKhau : nhanKhauNewTable.getItems()) {
//                        preparedStmtUpdate.setString(1, maHoKhau);
//                        preparedStmtUpdate.setString(2, nhanKhau.getMaNhanKhau());
//                        preparedStmtUpdate.execute();
//                    }
//                    // Cập nhật số thành viên hộ cũ
//                    String updateMembersSql = "UPDATE ho_khau SET SoThanhVien = ? WHERE MaHoKhau = (SELECT MaHo FROM nhan_khau WHERE MaNhanKhau = ?)";
//                    for(NhanKhauTableModel nhanKhauCu : nhanKhauOldTable.getItems()){
//                        PreparedStatement preparedStmtUpdateMembers = connection.prepareStatement(updateMembersSql);
//                        preparedStmtUpdateMembers.setInt(1,nhanKhauOldTable.getItems().size());
//                        preparedStmtUpdateMembers.setString(2,nhanKhauOldTable.getItems().get(0).getMaNhanKhau());
//                        preparedStmtUpdateMembers.execute();
//                    }
//                    // Cập nhật quan hệ các thành viên chủ hộ mới
//                    String updateQuanHeSql = "UPDATE thanh_vien_cua_ho SET QuanHeVoiChuHo = ?, MaHoKhau = ? WHERE MaNhanKhau = ?";
//                    PreparedStatement preparedStmtUpdateQuanHeThanhVien = connection.prepareStatement(updateQuanHeSql);
//                    for(NhanKhauTableModel nhanKhau : nhanKhauNewTable.getItems()) {
//                        if(nhanKhau.getQuanHeVoiChuHo().equals("Chủ Hộ")) {
//                            preparedStmtUpdateQuanHeThanhVien.setString(1,new String(""));
//                        } else {
//                            preparedStmtUpdateQuanHeThanhVien.setString(1,nhanKhau.getQuanHeVoiChuHo());
//                        }
//                        preparedStmtUpdateQuanHeThanhVien.setString(2,maHoKhau);
//                        preparedStmtUpdateQuanHeThanhVien.setString(3,nhanKhau.getMaNhanKhau());
//                        preparedStmtUpdateQuanHeThanhVien.execute();
//                    }
//
//                    nhanKhauOldTable.getItems().clear();
//                    nhanKhauNewTable.getItems().clear();
//                } else {
//                    throw new SQLException("Creating nhanKhau failed, no ID obtained.");
//                }
//        });
//    }
//
//    public void cancle(ActionEvent event) {
//
//    }
//
//    public void submit(ActionEvent event) {
//
//    }
    }
}
