package com.cnpm.controllers;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
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
    private TextField hoTenChuHoTxt;
    @FXML
    private TextField ngaySinhChuHoTxt;
    @FXML
    private TextField cccdChuHoTxt;
    @FXML
    private Button pickChuHoBtn;
    @FXML
    private Button cancleBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private Button pickMemberBtn;
    @FXML
    private Label errorLab;
    @FXML
    private TableView<NhanKhauTableModel> nhanKhauTable;
    @FXML
    private  TableColumn<NhanKhauTableModel, String> maNhanKhauCol;
    @FXML
    private  TableColumn<NhanKhauTableModel, Date> hoTenCol;
    @FXML
    private  TableColumn<NhanKhauTableModel, String> quanHeVoiChuHoCol;

    private SharedDataModel sharedDataModel = new SharedDataModel();

    private ChoiceBox<String> choiceBox;
    public void chonChuHo() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cnpm/scenes/chon-chu-ho.fxml"));
            ChonChuHoController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.showAndWait();
            NhanKhauTableModel selectedItem = controller.getSelectedItem();
            // use the selected item here
            System.out.println(selectedItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        maNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        quanHeVoiChuHoCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));

        errorLab.setText("");

        quanHeVoiChuHoCol.setCellFactory(column -> {
            return new TableCell<NhanKhauTableModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        choiceBox = new ChoiceBox<>();
                        choiceBox.getItems().addAll("Con trai", "Con gái", "Vợ", "Chồng", "Chủ Hộ", "Anh","Chị","Em");
                        choiceBox.getSelectionModel().select(item);
                        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                            NhanKhauTableModel nhanKhau = getTableView().getItems().get(getIndex());
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
                    for (NhanKhauTableModel nk : sharedDataModel.getSelectedRows()) {
                        nhanKhauTable.getItems().add(nk);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        cccdChuHoTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()|| newValue.isBlank()) {
                cccdChuHoTxt.setText("");
            } else {
                //Tìm kiếm họ tên của mã chủ hộ mới
                String findHoTenChuHoSql = "SELECT DISTINCT nhan_khau.ID, hoTen as HoTen, namSinh as NgaySinh, soCMT as SoCCCD FROM nhan_khau, chung_minh_thu WHERE chung_minh_thu.soCMT = ? AND nhan_khau.ID = chung_minh_thu.idNhanKhau;";
                try {
                    PreparedStatement preparedStmtFindName = connection.prepareStatement(findHoTenChuHoSql);
                    preparedStmtFindName.setString(1,cccdChuHoTxt.getText());
                    ResultSet rs = preparedStmtFindName.executeQuery();
                    if(rs.next()) {
                        String hoTenChuHo = rs.getString("HoTen");
                        if(hoTenChuHo.equals("")) {
                            maChuHoTxt.setText("");
                        } else {
                            hoTenChuHoTxt.setText(hoTenChuHo);
                        }
                        String ngaySnhChuHo = rs.getString("NgaySinh");
                        if(hoTenChuHo.equals("")) {
                            ngaySinhChuHoTxt.setText("");
                        } else {
                            ngaySinhChuHoTxt.setText(ngaySnhChuHo);
                        }
                        String maChuHo = rs.getString("ID");
                        if(maChuHo.equals("")) {
                            maChuHoTxt.setText("");
                        } else {
                            maChuHoTxt.setText(maChuHo);
                        }
                        errorLab.setText("");
                    } else {
                        hoTenChuHoTxt.setText("");
                        ngaySinhChuHoTxt.setText("");
                        maChuHoTxt.setText("");
                        errorLab.setText("Không tìm thấy mã nhân khẩu tương ứng, vui lòng thử lại");
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
        String idChuHo = maChuHoTxt.getText();
        String maKhuVuc = maKhuVucTxt.getText();
        String diaChi = diaChiTxt.getText();
        String hoTenChuHo = hoTenChuHoTxt.getText();
        int soThanhVien = nhanKhauTable.getItems().size();
        Date ngayTao = new Date(System.currentTimeMillis());
        if (maHoKhau.equals("") || diaChi.equals("") || maKhuVuc.equals("") ||
                idChuHo.equals("")) {
            errorLab.setText("Vui lòng điền đủ thông tin cần thiết");
        } else {
            String insertQuery = "INSERT INTO ho_khau (ngayLap, maKhuVuc, idChuHo, diaChi, maHoKhau)"  +
                    "VALUES ( ?, ?, ?, ?,?)";
            try {
                // Kiểm tra các mối quan hệ trong hộ khẩu mới.
                int chuHoCount = 0;
                boolean chuHoTrue = false;
                for(NhanKhauTableModel nhanKhauMoi : nhanKhauTable.getItems()) {
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
                prepareStmt.setDate(1,ngayTao);
                prepareStmt.setString(2, maKhuVuc);
                prepareStmt.setString(3, idChuHo);
                prepareStmt.setString(4, diaChi);
                prepareStmt.setString(5, maHoKhau);
                prepareStmt.execute();
                // Cập nhật quan hệ các thành viên chủ hộ mới
                String updateQuanHeSql = "INSERT INTO thanh_vien_cua_ho (quanHeVoiChuHo, IdNhanKhau, IdHoKhau) VALUES (?,?,(SELECT ID from ho_khau WHERE maHoKhau = ?))";
                PreparedStatement preparedStmtUpdateQuanHeThanhVien = connection.prepareStatement(updateQuanHeSql);
                for(NhanKhauTableModel nhanKhau : nhanKhauTable.getItems()) {
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
                    String updateLSTDHKSql = "INSERT INTO lich_su_thay_doi_ho_khau (ThayDoiChuHo, ThemNhanKhau, XoaNhanKhau, IdHoKhau, IdNhanKhau, IdLSTD) VALUES  (?, ?, ?,(SELECT ID from ho_khau WHERE maHoKhau = ?), ?, ?)";
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
                sharedDataModel=null;
                nhanKhauTable.getItems().clear();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void cancle(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }
}
