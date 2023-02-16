package com.cnpm.controllers.ho_khau;

import com.cnpm.entities.HoKhauTableModel;
import com.cnpm.entities.NhanKhauTableModel;
import com.cnpm.utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.cnpm.utilities.DBConnection.connection;

public class ChuyenHoKhauController implements Initializable {


    @FXML
    private TextField maHoKhauTxt;
    @FXML
    private TextField tenChuHoTxt;
    @FXML
    private TextField maKhuVucTxt;
    @FXML
    private TextField diaChiHienTaiTxt;
    @FXML
    private TextField diaChiChuyenDenTxt;
    @FXML
    private TextArea lyDoChuyenDiTxt;
    @FXML
    private Button submitBtn;
    @FXML
    private Button cancleBtn;
    @FXML
    private TableView<HoKhauTableModel> hoKhauTable;

    @FXML
    private TableColumn maHoKhauCol;
    @FXML
    private TableColumn hoTenChuHoCol;
    @FXML
    private TableColumn diaChiCol;
    @FXML
    private TextField maHoKhauTim;
    @FXML
    private ChoiceBox<String> optionChoiceBox;
    @FXML
    private Label errorLab;
    private  ObservableList<NhanKhauTableModel> nhanKhauRemove = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLab.setText("");
        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        hoTenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("hoTenChuHo"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChiHoKhau"));

        String query = "SELECT ho_khau.maHoKhau, nhan_khau.hoTen, ho_khau.diaChi FROM ho_khau, nhan_khau, thanh_vien_cua_ho " +
                "WHERE nhan_khau.ID = thanh_vien_cua_ho.idNhanKhau AND thanh_vien_cua_ho.idHoKhau = ho_khau.ID and " +
                "ho_khau.idChuHo = nhan_khau.ID and ho_khau.daXoa is NULL and ho_khau.ngayChuyenDi is null and nhan_khau.daXoa is null;";
        try {
            Statement preparedStmtFindName = connection.createStatement();
            ResultSet rs = preparedStmtFindName.executeQuery(query);
            while(rs.next()) {
                HoKhauTableModel hk = new HoKhauTableModel(
                        rs.getString("maHoKhau"),
                        rs.getString("hoTen"),
                        rs.getString("diaChi")
                        );
                hoKhauTable.getItems().add(hk);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        optionChoiceBox.getItems().addAll("Tìm theo họ tên chủ hộ", "Tìm theo Mã hộ khẩu");
        optionChoiceBox.getSelectionModel().selectFirst();

        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<HoKhauTableModel> filteredData = new FilteredList<>(hoKhauTable.getItems(), b->true);
            maHoKhauTim.textProperty().addListener(((observable1, oldValue1, newValue1)->{
                filteredData.setPredicate(hoKhau->{
                    if(newValue1.isEmpty() || newValue1.isBlank() || newValue1 == null) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if(hoKhau.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo Mã hộ khẩu" ) {
                        return true;
                    } else if(hoKhau.getHoTenChuHo().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên chủ hộ") {
                        return true;
                    } else
                        return false;
                });
            } ));
            // Sắp xếp thứ tự
            SortedList<HoKhauTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(hoKhauTable.comparatorProperty());
            hoKhauTable.setItems(sortedData);
        });

        hoKhauTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    HoKhauTableModel selectedRow = hoKhauTable.getSelectionModel().getSelectedItem();
                    if (selectedRow != null) {
                        String maHoKhau = selectedRow.getMaHoKhau();
                        String hoTenChuHo = selectedRow.getHoTenChuHo();
                        String diaChi = selectedRow.getDiaChiHoKhau();
                        maHoKhauTxt.setText(maHoKhau);
                        tenChuHoTxt.setText(hoTenChuHo);
                        diaChiHienTaiTxt.setText(diaChi);
                    }
                }
            }
        });
    }

    public void refreshTable() {
        hoKhauTable.getItems().clear();
        String query = "SELECT ho_khau.maHoKhau, nhan_khau.hoTen, ho_khau.diaChi FROM ho_khau, nhan_khau, thanh_vien_cua_ho " +
                "WHERE nhan_khau.ID = thanh_vien_cua_ho.idNhanKhau AND thanh_vien_cua_ho.idHoKhau = ho_khau.ID and " +
                "ho_khau.idChuHo = nhan_khau.ID and ho_khau.daXoa is NULL and ho_khau.ngayChuyenDi is null and nhan_khau.daXoa is null;";
        try {
            Statement preparedStmtFindName = connection.createStatement();
            ResultSet rs = preparedStmtFindName.executeQuery(query);
            while(rs.next()) {
                HoKhauTableModel hk = new HoKhauTableModel(
                        rs.getString("maHoKhau"),
                        rs.getString("hoTen"),
                        rs.getString("diaChi")
                );
                hoKhauTable.getItems().add(hk);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void submit() {
        String diaChiChuyenDen = diaChiChuyenDenTxt.getText();
        String maHoKhau =  maHoKhauTxt.getText();
        Date ngayChuyenDi = new Date(System.currentTimeMillis());
        String lyDoChuyen = lyDoChuyenDiTxt.getText();
        String nguoiThucHien = UserSession.getUsername();
    // Cập nhật địa chỉ hộ khẩu
        try {
            if(diaChiChuyenDenTxt.getText().isEmpty()||
                    maHoKhauTxt.getText().isEmpty()||
                    lyDoChuyenDiTxt.getText().isEmpty()||
                    tenChuHoTxt.getText().isEmpty()||
                    diaChiHienTaiTxt.getText().isEmpty()){
                throw new Exception("Vui lòng nhập đầy đủ thông tin cần thiết !");
            }
            String updateHoKhauSql = "UPDATE ho_khau SET diaChi = ?, ngayChuyenDi = ?, lyDoChuyen = ?, nguoiThucHien = ?  WHERE maHoKhau = ?";
            PreparedStatement preparedStmtUpdateHoKhau = connection.prepareStatement(updateHoKhauSql);
            preparedStmtUpdateHoKhau.setString(1, diaChiChuyenDen);
            preparedStmtUpdateHoKhau.setDate(2, ngayChuyenDi);
            preparedStmtUpdateHoKhau.setString(3, lyDoChuyen);
            preparedStmtUpdateHoKhau.setString(4, nguoiThucHien);
            preparedStmtUpdateHoKhau.setString(5, maHoKhau);
            preparedStmtUpdateHoKhau.execute();



            String delteQHSql = "DELETE FROM thanh_vien_cua_ho WHERE IdHoKhau = ?";
            PreparedStatement preparedDeleteQHStmt = connection.prepareStatement(delteQHSql);
            String nhanKhauAddSql = "Select * from nhan_khau,thanh_vien_cua_ho, ho_khau where ho_khau.ID = thanh_vien_cua_ho.idHoKhau and  thanh_vien_cua_ho.idNhanKhau = nhan_khau.ID and ho_khau.maHoKhau = ? and nhan_khau.daXoa is NULL";
            PreparedStatement  preparedAddNhanKhauStmt= connection.prepareStatement(nhanKhauAddSql);
            preparedAddNhanKhauStmt.setString(1,maHoKhauTxt.getText());
            ResultSet queryResult = preparedAddNhanKhauStmt.executeQuery();
            // Tìm các nhân khẩu có mã hộ khẩu xóa
            System.out.println(queryResult.getFetchSize());
            while (queryResult.next()) {
                nhanKhauRemove.add(new NhanKhauTableModel(
                        queryResult.getString("ID"),
                        queryResult.getString("hoTen"),
                        queryResult.getString("quanHeVoiChuHo")
                ));
            }


            Date ngayTao = new Date(System.currentTimeMillis());
            String updateLSTDSql = "INSERT INTO lich_su_thay_doi (NgayThayDoi, GhiChu) VALUES (?, ?)";
            String updateLSTDHKSql = "INSERT INTO lich_su_thay_doi_ho_khau (ThayDoiChuHo, ThemNhanKhau, XoaNhanKhau, IdHoKhau, IdNhanKhau, IdLSTD) VALUES  (?, ?, ?, (select ID from ho_khau where maHoKhau = ?), ?, ?)";
            PreparedStatement preparedStmtUpdateLSHK = connection.prepareStatement(updateLSTDHKSql);
            PreparedStatement preparedStmtUpdateLS = connection.prepareStatement(updateLSTDSql);
            preparedStmtUpdateLS.setDate(1,ngayTao);
            preparedStmtUpdateLS.setString(2,"Chuyển hộ khẩu");
            preparedStmtUpdateLS.execute();
            String selectLastId = "SELECT LAST_INSERT_ID()";
            int lastId = 0;
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectLastId)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        lastId = resultSet.getInt(1);
                    }
                }
            } catch (Exception e)  {
                System.out.println(e.getMessage());
            }
            for (NhanKhauTableModel nhanKhau : nhanKhauRemove) {
                String updateNhanKhauSql = "UPDATE nhan_khau SET diaChiMoi = ?, ngayChuyenDi = ?, lyDoChuyenDi = ? WHERE ID = ?";
                PreparedStatement preparedStatementUpdateNhanKhau = connection.prepareStatement(updateNhanKhauSql);
                preparedStatementUpdateNhanKhau.setString(1, diaChiChuyenDen);
                preparedStatementUpdateNhanKhau.setDate(2, ngayChuyenDi);
                preparedStatementUpdateNhanKhau.setString(3, lyDoChuyen);
                preparedStatementUpdateNhanKhau.setString(4, nhanKhau.getMaNhanKhau());
                preparedStatementUpdateNhanKhau.execute();

                preparedDeleteQHStmt.setString(1,nhanKhau.getMaNhanKhau());

                preparedStmtUpdateLSHK.setString(1,"0");
                preparedStmtUpdateLSHK.setString(2, "0");
                preparedStmtUpdateLSHK.setString(3, "1");
                preparedStmtUpdateLSHK.setString(4, maHoKhauTxt.getText());
                preparedStmtUpdateLSHK.setString(5, nhanKhau.getMaNhanKhau());
                preparedStmtUpdateLSHK.setInt(6,lastId );
                preparedStmtUpdateLSHK.execute();
                preparedDeleteQHStmt.execute();
            }

            errorLab.setText("");
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            errorLab.setText(e.getMessage());
        }
    }
    @FXML
    public void cancle(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }
}
