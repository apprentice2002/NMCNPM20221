package com.cnpm.controllers;

import com.cnpm.entities.HoKhau;
import com.cnpm.utilities.*;
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

        optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm theo ID");
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
                    if(hoKhau.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo ID" ) {
                        return true;
                    } else if(hoKhau.getHoTenChuHo().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên") {
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

            String updateNhanKhauSql = "UPDATE nhan_khau SET diaChiMoi = ?, ngayChuyenDi = ?, lyDoChuyenDi = ? WHERE ID = " +
                    "(SELECT nhan_khau.ID from nhan_khau, ho_khau,thanh_vien_cua_ho where nhan_khau.ID = thanh_vien_cua_ho.idNhanKhau and thanh_vien_cua_ho.idHoKhau = ho_khau.ID and ho_khau.maHoKhau = ?)";
            PreparedStatement preparedStatementUpdateNhanKhau = connection.prepareStatement(updateNhanKhauSql);
            preparedStatementUpdateNhanKhau.setString(1, diaChiChuyenDen);
            preparedStatementUpdateNhanKhau.setDate(2, ngayChuyenDi);
            preparedStatementUpdateNhanKhau.setString(3, lyDoChuyen);
            preparedStatementUpdateNhanKhau.setString(4, maHoKhau);
            preparedStatementUpdateNhanKhau.execute();
            errorLab.setText("");
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
