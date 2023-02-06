package com.cnpm.controllers;

import com.cnpm.utilities.DBConnection;
import com.cnpm.entities.NhanKhau;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.cnpm.entities.NhanKhau;
import com.cnpm.utilities.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.cnpm.utilities.DBConnection.connection;

public class ThemHoKhauController implements Initializable {

    @FXML
    private TextField maHoKhauTxt;
    @FXML
    private TextField maChuHoTxt;
    @FXML
    private TextField maKhuVucTxt;
    @FXML
    private TextField diaChiTxt;
    @FXML
    private Label hoTenChuHoLab;
    @FXML
    private Label ngaySinhChuHoLab;
    @FXML
    private Label cmndChuHoLab;
    @FXML
    private Button pickChuHoBtn;
    @FXML
    private Button cancleBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private Button pickMemberBtn;
    @FXML
    private Label noticeLab;
    @FXML
    private TableView<themHoKhauNhanKhauTableModel> memberTable;
    @FXML
    private  TableColumn<themHoKhauNhanKhauTableModel, String> maNhanKhauCol;
    @FXML
    private  TableColumn<themHoKhauNhanKhauTableModel, Date> ngaySinhCol;
    @FXML
    private  TableColumn<themHoKhauNhanKhauTableModel, String> quanHeVoiChuHoCol;

    private SharedDataModel sharedDataModel = new SharedDataModel();

    private ChoiceBox<String> choiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        maNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        quanHeVoiChuHoCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));

        quanHeVoiChuHoCol.setCellFactory(column -> {
            return new TableCell<themHoKhauNhanKhauTableModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        choiceBox = new ChoiceBox<>();
                        choiceBox.getItems().addAll("Con", "Vợ", "Chồng", "Chủ Hộ");
                        choiceBox.getSelectionModel().select(item);
                        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                            themHoKhauNhanKhauTableModel nhanKhau = getTableView().getItems().get(getIndex());
                            nhanKhau.setQuanHeVoiChuHo(newValue);
                        });
                        setGraphic(choiceBox);
                    }
                }
            };
        });


        pickMemberBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cnpm/scenes/chon-nhan-khau.fxml"));
                Parent root = loader.load();
                ChonNhanKhauController controller = loader.getController();
                controller.setSharedDataModel(sharedDataModel);
                Scene nhanKhauScene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Chọn Nhân khẩu");
                stage.setScene(nhanKhauScene);
                stage.show();
                stage.setOnHidden(e -> {
                    System.out.println(sharedDataModel.getSelectedRows().size());
                    memberTable.getItems().clear();
                    memberTable.refresh();
                    for (themHoKhauNhanKhauTableModel nk : sharedDataModel.getSelectedRows()) {
                        memberTable.getItems().add(nk);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        maChuHoTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()|| newValue.isBlank()) {
                hoTenChuHoLab.setText("");
                ngaySinhChuHoLab.setText("");
                cmndChuHoLab.setText("");
            } else {
                //Tìm kiếm họ tên của mã chủ hộ mới
                String findHoTenChuHoSql = "SELECT HoTen, NgaySinh, SoCCCD FROM nhan_khau, can_cuoc_cong_dan WHERE nhan_khau.MaNhanKhau = ? AND nhan_khau.MaNhanKhau = can_cuoc_cong_dan.MaNhanKhau;";
                try {
                    PreparedStatement preparedStmtFindName = connection.prepareStatement(findHoTenChuHoSql);
                    preparedStmtFindName.setString(1,maChuHoTxt.getText());
                    ResultSet rs = preparedStmtFindName.executeQuery();
                    if(rs.next()) {
                        String hoTenChuHo = rs.getString("HoTen");
                        if(hoTenChuHo.equals("")) {
                            hoTenChuHoLab.setText("");
                        } else {
                            hoTenChuHoLab.setText(hoTenChuHo);
                        }
                        String ngaySnhChuHo = rs.getString("NgaySinh");
                        if(hoTenChuHo.equals("")) {
                            ngaySinhChuHoLab.setText("");
                        } else {
                            ngaySinhChuHoLab.setText(ngaySnhChuHo);
                        }
                        String soCCCD = rs.getString("SoCCCD");
                        if(soCCCD.equals("")) {
                            cmndChuHoLab.setText("");
                        } else {
                            cmndChuHoLab.setText(soCCCD);
                        }
                        noticeLab.setText("");
                    } else {
                        hoTenChuHoLab.setText("");
                        ngaySinhChuHoLab.setText("");
                        cmndChuHoLab.setText("");
                        noticeLab.setText("Không tìm thấy mã nhân khẩu tương ứng, vui lòng thử lại");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    @FXML
    public void xacNhan(ActionEvent event) throws IOException {
        String maHoKhau = maHoKhauTxt.getText();
        String maChuHo = maChuHoTxt.getText();
        String maKhuVuc = maKhuVucTxt.getText();
        String diaChi = diaChiTxt.getText();
        String hoTenChuHo = hoTenChuHoLab.getText();
        int soThanhVien = memberTable.getItems().size();
        Date ngayTao = new Date(System.currentTimeMillis());
        if (maHoKhau.equals("") || diaChi.equals("") || maKhuVuc.equals("") ||
                maChuHo.equals("")) {
            noticeLab.setText("Vui lòng điền đủ thông tin cần thiết");
        } else {
            String insertQuery = "INSERT INTO ho_khau (MaHoKhau, NgayTao, MaChuHo, DiaChiHoKhau, SoThanhVien) VALUES (?, ?, ?, ?, ?)";
            try {
                // Kiểm tra các mối quan hệ trong hộ khẩu mới.
                int chuHoCount = 0;
                boolean chuHoTrue = false;
                for(themHoKhauNhanKhauTableModel nhanKhauMoi : memberTable.getItems()) {
                    if (nhanKhauMoi.getQuanHeVoiChuHo().equals("Chủ Hộ")) {
                        if(nhanKhauMoi.getMaNhanKhau().equals(maChuHoTxt.getText())) {
                            chuHoTrue = true;
                        }
                        chuHoCount++;
                    }
                }
                if (chuHoCount<1 || chuHoCount>1) {
                    throw new Exception("Vui lòng chỉ chọn 1 chủ hộ !");
                }
                if(chuHoTrue == false) {
                    throw new Exception("Vui lòng điền đúng mã chủ hộ mới!");
                }
                connection = DBConnection.getConnection();
                PreparedStatement prepareStmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                prepareStmt.setString(1,maHoKhau);
                prepareStmt.setDate(2, ngayTao);
                prepareStmt.setString(3, maChuHo);
                prepareStmt.setString(4, diaChi);
                prepareStmt.setInt(5, soThanhVien);
                prepareStmt.execute();
                // Cập nhật thông tin nhân khẩu
                String updateNhaKhauSql = "UPDATE nhan_khau SET MaHo = ? WHERE MaNhanKhau = ?";
                PreparedStatement preparedStmtUpdate = connection.prepareStatement(updateNhaKhauSql);
                for (themHoKhauNhanKhauTableModel nhanKhau :memberTable.getItems()) {
                    preparedStmtUpdate.setString(1, maHoKhau);
                    preparedStmtUpdate.setString(2, nhanKhau.getMaNhanKhau());
                    preparedStmtUpdate.execute();
                }
                // Cập nhật quan hệ các thành viên chủ hộ mới
                String updateQuanHeSql = "INSERT INTO thanh_vien_cua_ho (QuanHeVoiChuHo, MaNhanKhau, MaHoKhau) VALUES (?,?,?)";
                PreparedStatement preparedStmtUpdateQuanHeThanhVien = connection.prepareStatement(updateQuanHeSql);
                for(themHoKhauNhanKhauTableModel nhanKhau : memberTable.getItems()) {
                    if(nhanKhau.getQuanHeVoiChuHo().equals("Chủ Hộ")) {
                        preparedStmtUpdateQuanHeThanhVien.setString(1,new String(""));
                    } else {
                        preparedStmtUpdateQuanHeThanhVien.setString(1,nhanKhau.getQuanHeVoiChuHo());
                    }
                    preparedStmtUpdateQuanHeThanhVien.setString(2,nhanKhau.getMaNhanKhau());
                    preparedStmtUpdateQuanHeThanhVien.setString(3,maHoKhau);
                    preparedStmtUpdateQuanHeThanhVien.execute();

                    // Cập nhật lịch sử thay đổi hộ khẩu
                    String updateLSTDSql = "INSERT INTO lich_su_thay_doi (NgayThayDoi, GhiChu) VALUES (?, ?)";
                    String updateLSTDHKSql = "INSERT INTO lich_su_thay_doi_ho_khau (ThayDoiChuHo, ThemNhanKhau, XoaNhanKhau, MaHoKhau, MaNhanKhau, MaLSTD) VALUES  (?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStmtUpdateLSHK = connection.prepareStatement(updateLSTDHKSql);
                    PreparedStatement preparedStmtUpdateLS = connection.prepareStatement(updateLSTDSql);


                    preparedStmtUpdateLS.setDate(1,ngayTao);
                    preparedStmtUpdateLS.setString(2,"Thêm hộ khẩu");
                    preparedStmtUpdateLS.execute();

                    String selectLastId = "SELECT LAST_INSERT_ID()";
                    int lastId = 0;
                    try (PreparedStatement preparedStatement = connection.prepareStatement(selectLastId)) {
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            while (resultSet.next()) {
                                lastId = resultSet.getInt(1);
                            }
                        }
                    }

                    preparedStmtUpdateLSHK.setString(1,"0");
                    preparedStmtUpdateLSHK.setString(2, "1");
                    preparedStmtUpdateLSHK.setString(3, "0");
                    preparedStmtUpdateLSHK.setString(4, maHoKhau);
                    preparedStmtUpdateLSHK.setString(5, nhanKhau.getMaNhanKhau());
                    preparedStmtUpdateLSHK.setInt(6,lastId );
                    preparedStmtUpdateLSHK.execute();
                }

                memberTable.getItems().clear();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Utilities.changeScene(event, "/com/cnpm/views/ho-khau.fxml", 720, 600);
    }

    @FXML
    public void cancle(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/views/ho-khau.fxml", 720, 600);
    }
}
