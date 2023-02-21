package com.cnpm.controllers.ho_khau;


import com.cnpm.entities.NhanKhauTableModel;
import com.cnpm.entities.SharedDataModel;
import com.cnpm.utilities.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
    @FXML
    private Button deleteNhanKhauBtn;

    private void removeNhanKhau(NhanKhauTableModel selectedRow) {
        sharedDataModel.getSelectedRows().removeIf(obj->obj.getHoTenNhanKhau().equals(selectedRow.getHoTenNhanKhau()));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        maNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        quanHeVoiChuHoCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));
        errorLab.setText("");

        insertTable();

        quanHeVoiChuHoCol.setCellFactory(column -> {
            return new TableCell<NhanKhauTableModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        choiceBox = new ChoiceBox<>();
                        choiceBox.getItems().addAll("Con", "Vợ", "Chồng", "Chủ Hộ", "Anh","Chị","Em");
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
                Utilities.popNewWindow(event,"/com/cnpm/chuc-nang-view/nhan-khau-chuc-nang-view/them-nhan-khau.fxml");
        });

        deleteNhanKhauBtn.setOnAction(event -> {
            NhanKhauTableModel selectedItem = nhanKhauTable.getSelectionModel().getSelectedItem();
            int ma_nhan_khau = Integer.parseInt(selectedItem.getMaNhanKhau());

            String delteNKSql = "DELETE FROM nhan_khau WHERE ID = ?";
            Connection connection = DBConnection.getConnection();

            try{
                PreparedStatement preparedDelete = connection.prepareStatement(delteNKSql);
                preparedDelete.setInt(1,ma_nhan_khau);
                preparedDelete.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            removeNhanKhau(selectedItem);
            nhanKhauTable.getItems().removeIf(obj->obj.getHoTenNhanKhau().equals(selectedItem.getHoTenNhanKhau()));
        });


        maChuHoTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            errorLab.setText("");
            if(newValue.length()>=2) {
                //Tìm kiếm họ tên của mã chủ hộ mới
                String findHoTenChuHoSql = "SELECT DISTINCT nhan_khau.ID, hoTen as HoTen, namSinh  as NgaySinh , diaChiHienNay FROM nhan_khau WHERE nhan_khau.ID = ?  ;";
                try {
                    PreparedStatement preparedStmtFindName = connection.prepareStatement(findHoTenChuHoSql);
                    preparedStmtFindName.setString(1,maChuHoTxt.getText());
                    ResultSet rs = preparedStmtFindName.executeQuery();
                    if(rs.next()) {
                        String hoTenChuHo = rs.getString("HoTen");
                        if(hoTenChuHo.equals("")) {
                            hoTenChuHoTxt.setText("");
                        } else {
                            hoTenChuHoTxt.setText(hoTenChuHo);
                        }
                        String ngaySnhChuHo = rs.getString("NgaySinh");
                        if(hoTenChuHo.equals("")) {
                            ngaySinhChuHoTxt.setText("");
                        } else {
                            ngaySinhChuHoTxt.setText(ngaySnhChuHo);
                        }
                        String cccd = rs.getString("diaChiHienNay");
                        if(cccdChuHoTxt.equals("")) {
                            cccdChuHoTxt.setText("");
                        } else {
                            cccdChuHoTxt.setText(cccd);
                        }
                        errorLab.setText("");
                    } else {
                        hoTenChuHoTxt.setText("");
                        ngaySinhChuHoTxt.setText("");
                        cccdChuHoTxt.setText("");
                        errorLab.setText("Không tìm thấy mã nhân khẩu tương ứng,\n vui lòng thử lại");
                    }
                } catch (NumberFormatException e) {
                    errorLab.setText("Vui lòng chỉ nhập đúng dữ liệu !");
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void refresh(ActionEvent event){
        nhanKhauTable.getItems().clear();
        insertTable();
    }

    public void insertTable(){
        try {
            String nhanKhauSql = "SELECT DISTINCT nhan_khau.ID as MaNhanKhau, nhan_khau.hoTen FROM\n" +
                    "                                    nhan_khau WHERE nhan_khau.daXoa is null\n" +
                    "                                    EXCEPT \n" +
                    "                                    (SELECT DISTINCT nhan_khau.ID as MaNhanKhau, nhan_khau.hoTen\n" +
                    "                                    FROM nhan_khau, thanh_vien_cua_ho WHERE nhan_khau.ID = thanh_vien_cua_ho.idNhanKhau\n" +
                    "                                    AND thanh_vien_cua_ho.quanHeVoiChuHo is not null)\n" +
                    "                                    UNION\n" +
                    "                                    (SELECT DISTINCT nhan_khau.ID as MaNhanKhau, nhan_khau.hoTen\n" +
                    "                                            FROM nhan_khau, tam_tru WHERE nhan_khau.ID = tam_tru.idNhanKhau);";
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(nhanKhauSql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                NhanKhauTableModel nhanKhau = new NhanKhauTableModel(queryResult.getString("MaNhanKhau"),
                        queryResult.getString("hoTen"),"");
                nhanKhauTable.getItems().add(nhanKhau);
            }
            maNhanKhauCol.setSortType(TableColumn.SortType.ASCENDING);
            nhanKhauTable.getSortOrder().clear();
            nhanKhauTable.getSortOrder().add(maNhanKhauCol);
            nhanKhauTable.sort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void xacNhan(ActionEvent event) throws IOException {
        String maHoKhau = maHoKhauTxt.getText();
        String idChuHo = maChuHoTxt.getText();
        String maKhuVuc = maKhuVucTxt.getText();
        String diaChi = diaChiTxt.getText();
        String hoTenChuHo = hoTenChuHoTxt.getText();
        Date ngayTao = new Date(System.currentTimeMillis());
        if (maHoKhau.equals("") || diaChi.equals("") || maKhuVuc.equals("") ||
                idChuHo.equals("")) {
            errorLab.setText("Vui lòng điền đủ thông tin cần thiết");
        } else {
            int idErrorCheck = Integer.parseInt(idChuHo);
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
                String updateLSTDSql = "INSERT INTO lich_su_thay_doi (NgayThayDoi, GhiChu) VALUES (?, ?)";
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
                    String updateLSTDHKSql = "INSERT INTO lich_su_thay_doi_ho_khau (ThayDoiChuHo, ThemNhanKhau, XoaNhanKhau, IdHoKhau, IdNhanKhau, IdLSTD) VALUES  (?, ?, ?,(SELECT ID from ho_khau WHERE maHoKhau = ?), ?, ?)";
                    PreparedStatement preparedStmtUpdateLSHK = connection.prepareStatement(updateLSTDHKSql);


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
                errorLab.setText(e.getMessage());
            }
        }
    }

    @FXML
    public void cancle(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }
}
