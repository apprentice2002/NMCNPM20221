package com.cnpm.controllers;

import com.cnpm.entities.NhanKhau;
import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.NhanKhauTableModel;
import com.cnpm.utilities.TableModel;
import com.cnpm.utilities.Utilities;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class TachHoKhauController implements Initializable {

    @FXML
    public Button moveToOld;
    @FXML
    public Button moveToNew;
    @FXML
    public TextField maHoKhauTim;
    @FXML
    private Label hoTenChuHoCu;
    @FXML
    private Label hoTenChuHoMoi;
    @FXML
    private TableColumn<TableModel, String> maHoKhauCol;
    @FXML
    private TableColumn<TableModel, String> hoTenChuHoCol;
    @FXML
    private TableColumn<TableModel, String> diaChiCol;
    @FXML
    private TableView<TableModel> hoKhauTable;
    ObservableList<TableModel> hoKhauView = FXCollections.observableArrayList();

    @FXML
    private TableColumn<NhanKhauTableModel, String> maNhanKhauOldCol;
    @FXML
    private TableColumn<NhanKhauTableModel, String> hoTenNhanKhauOldCol;
    @FXML
    private TableColumn<NhanKhauTableModel, String> quanHeVoiChuHoOldCol;

    @FXML
    private TableView<NhanKhauTableModel> nhanKhauOldTable;
    ObservableList<NhanKhauTableModel> nhanKhauOldView = FXCollections.observableArrayList();
    @FXML
    private TableColumn<NhanKhauTableModel, String> maNhanKhauNewCol;
    @FXML
    private TableColumn<NhanKhauTableModel, String> hoTenNhanKhauNewCol;
    @FXML
    private TableColumn<NhanKhauTableModel, String> quanHeVoiChuHoNewCol;

    @FXML
    private TableView<NhanKhauTableModel> nhanKhauNewTable;
    ObservableList<NhanKhauTableModel> nhanKhauNewView = FXCollections.observableArrayList();

    @FXML
    private TextField maChuHoMoiTxt;
    @FXML
    private TextField diaChiTxt;
    @FXML
    private TextField maKhuVucTxt;
    Connection connection = DBConnection.getConnection();


    String hoKhauSql = "SELECT MaHoKhau, nhan_khau.HoTen AS TenChuHo, DiaChiHoKhau FROM nhan_khau, ho_khau WHERE MaNhanKhau = MaChuHo";
    String nhanKhauSql;
    String submitSql;

    private ChoiceBox<String> choiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection connection = DBConnection.getConnection();

        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        hoTenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("hoTenChuHo"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChiHoKhau"));


        maNhanKhauOldCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        hoTenNhanKhauOldCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        quanHeVoiChuHoOldCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));


        maNhanKhauNewCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        hoTenNhanKhauNewCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        quanHeVoiChuHoNewCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));


        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(hoKhauSql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                hoKhauView.add(new TableModel(
                        queryResult.getString("MaHoKhau"),
                        queryResult.getString("TenChuHo"),
                        queryResult.getString("DiaChiHoKhau")));
            }
            hoKhauTable.setItems(hoKhauView);
            //Lọc theo TextField
            FilteredList<TableModel> filteredData = new FilteredList<>(hoKhauView, b->true);
            maHoKhauTim.textProperty().addListener(((observable, oldValue, newValue)->{
                filteredData.setPredicate(tableModel->{
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if(tableModel.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }
                    return false;
                });
            } ));
            // Sắp xếp thứ tự hộ khẩu
            SortedList<TableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(hoKhauTable.comparatorProperty());
            hoKhauTable.setItems(sortedData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        hoKhauTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    nhanKhauOldTable.getItems().clear();
                    nhanKhauNewTable.getItems().clear();
                    TableModel selectedRow = hoKhauTable.getSelectionModel().getSelectedItem();
                    if (selectedRow != null) {
                        String maHoKhau = selectedRow.getMaHoKhau();
                        nhanKhauSql = "SELECT nhan_khau.MaNhanKhau, nhan_khau.HoTen, QuanHeVoiChuHo FROM nhan_khau, thanh_vien_cua_ho " +
                                " WHERE nhan_khau.MaHo = " + maHoKhau +
                                " AND thanh_vien_cua_ho.MaNhanKhau = nhan_khau.MaNhanKhau";
                        try {
                            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
                            Statement statement = connection.createStatement();
                            ResultSet queryResult = statement.executeQuery(nhanKhauSql);
                            // Cập nhật họ tên Chủ hộ cũ
                            hoTenChuHoCu.setText(selectedRow.getHoTenChuHo());
                            //Xóa họ tên chủ hộ mới
                            hoTenChuHoMoi.setText("");
                            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
                            if(nhanKhauOldView.isEmpty()){
                                while (queryResult.next()) {
                                    if(!queryResult.getString("QuanHeVoiChuHo").equals("")) {
                                        nhanKhauOldView.add(new NhanKhauTableModel(
                                                queryResult.getString("MaNhanKhau"),
                                                queryResult.getString("HoTen"),
                                                queryResult.getString("QuanHeVoiChuHo")));
                                    }
                                }
                            } else {
                                nhanKhauOldView.clear();
                                while (queryResult.next()) {
                                    nhanKhauOldView.add(new NhanKhauTableModel(
                                            queryResult.getString("MaNhanKhau"),
                                            queryResult.getString("HoTen"),
                                            queryResult.getString("QuanHeVoiChuHo")));
                                }
                            }
                            nhanKhauOldTable.setItems(nhanKhauOldView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        moveToNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selectedIndex = nhanKhauOldTable.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    NhanKhauTableModel data = nhanKhauOldTable.getItems().get(selectedIndex);
                    nhanKhauNewTable.getItems().add(data);
                    nhanKhauOldTable.getItems().remove(selectedIndex);
                }
            }
        });
        moveToOld.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selectedIndex = nhanKhauNewTable.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    NhanKhauTableModel data = nhanKhauNewTable.getItems().get(selectedIndex);
                    nhanKhauOldTable.getItems().add(data);
                    nhanKhauNewTable.getItems().remove(selectedIndex);
                }
            }
        });

        quanHeVoiChuHoNewCol.setCellFactory(column -> {
            return new TableCell<NhanKhauTableModel, String>() {
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
                            NhanKhauTableModel nhanKhau = getTableView().getItems().get(getIndex());
                            nhanKhau.setQuanHeVoiChuHo(newValue);
                        });
                        setGraphic(choiceBox);
                    }
                }
            };
        });
        maChuHoMoiTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()|| newValue.isBlank()) {
                hoTenChuHoMoi.setText("");
            } else {
                //Tìm kiếm họ tên của mã chủ hộ mới
                String findHoTenChuHoSql = "SELECT HoTen FROM nhan_khau WHERE MaNhanKhau = ?";
                try {
                    PreparedStatement preparedStmtFindName = connection.prepareStatement(findHoTenChuHoSql);
                    preparedStmtFindName.setString(1,maChuHoMoiTxt.getText());
                    ResultSet rs = preparedStmtFindName.executeQuery();
                    if(rs.next()) {
                        String hoTenChuHo = rs.getString("HoTen");
                        hoTenChuHoMoi.setText(hoTenChuHo);
                    } else {
                        hoTenChuHoMoi.setText("");
                        System.out.println("Không tìm thấy mã nhân khẩu tương ứng, vui lòng thử lại");
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



    public void cancle(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/views/ho-khau.fxml", "Hộ khẩu",720, 600);
    }

    public void submit(ActionEvent event) {
        List<NhanKhauTableModel> dataNhanKhauCu = nhanKhauNewTable.getItems();
        List<NhanKhauTableModel> dataNhanKhauMoi = nhanKhauOldTable.getItems();
        try {
            if(dataNhanKhauMoi == null) {
                System.out.println("Vui lòng chọn những nhân khẩu muốn tách hộ!");
            } else {
                // Kiểm tra các mối quan hệ trong hộ khẩu mới.
                int chuHoCount = 0;
                boolean chuHoTrue = false;
                for(NhanKhauTableModel nhanKhauMoi : nhanKhauNewTable.getItems()) {
                    if (nhanKhauMoi.getQuanHeVoiChuHo().equals("Chủ Hộ")) {
                        if(nhanKhauMoi.getMaNhanKhau().equals(maChuHoMoiTxt.getText())) {
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
                // Tạo hộ khẩu mới
                String maChuHoMoi = maChuHoMoiTxt.getText();
                String diaChi = diaChiTxt.getText();
                String maKhuVuc = maKhuVucTxt.getText();
                Date ngayTao = new Date(System.currentTimeMillis());
                int soThanhVien = nhanKhauNewTable.getItems().size();
                String newHoKhauSql = "INSERT INTO ho_khau (NgayTao, MaChuHo, DiaChiHoKhau, SoThanhVien) " +
                        "VALUES ( ?, ?, ?, ?)";
                PreparedStatement preparedStmtInsert = connection.prepareStatement(newHoKhauSql,Statement.RETURN_GENERATED_KEYS);
                if(diaChi.isEmpty() || maKhuVuc.isEmpty() || maChuHoMoi.isEmpty()) {
                    System.out.println("Vui lòng nhập đầy đủ thông tin !");
                } else {
                    preparedStmtInsert.setDate(1, ngayTao);
                    preparedStmtInsert.setString(2, maChuHoMoi);
                    preparedStmtInsert.setString(3, diaChi);
                    preparedStmtInsert.setInt(4, soThanhVien);
                }
                int affectedRows = preparedStmtInsert.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating nhanKhau failed, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStmtInsert.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        String maHoKhau = generatedKeys.getString(1);
                        // Cập nhật hộ khẩu cũ
                        submitSql = "UPDATE nhan_khau SET MaHo = ? WHERE MaNhanKhau = ?";
                        PreparedStatement preparedStmtUpdate = connection.prepareStatement(submitSql);
                        // Cập nhật thàn viên hộ khẩu mới.
                        for (NhanKhauTableModel nhanKhau : nhanKhauNewTable.getItems()) {
                            preparedStmtUpdate.setString(1, maHoKhau);
                            preparedStmtUpdate.setString(2, nhanKhau.getMaNhanKhau());
                            preparedStmtUpdate.execute();
                        }
                        // Cập nhật số thành viên hộ cũ
                        String updateMembersSql = "UPDATE ho_khau SET SoThanhVien = ? WHERE MaHoKhau = (SELECT MaHo FROM nhan_khau WHERE MaNhanKhau = ?)";
                        for(NhanKhauTableModel nhanKhauCu : nhanKhauOldTable.getItems()){
                            PreparedStatement preparedStmtUpdateMembers = connection.prepareStatement(updateMembersSql);
                            preparedStmtUpdateMembers.setInt(1,nhanKhauOldTable.getItems().size());
                            preparedStmtUpdateMembers.setString(2,nhanKhauOldTable.getItems().get(0).getMaNhanKhau());
                            preparedStmtUpdateMembers.execute();
                        }
                        // Cập nhật quan hệ các thành viên chủ hộ mới
                        String updateQuanHeSql = "UPDATE thanh_vien_cua_ho SET QuanHeVoiChuHo = ?, MaHoKhau = ? WHERE MaNhanKhau = ?";
                        PreparedStatement preparedStmtUpdateQuanHeThanhVien = connection.prepareStatement(updateQuanHeSql);
                        for(NhanKhauTableModel nhanKhau : nhanKhauNewTable.getItems()) {
                            if(nhanKhau.getQuanHeVoiChuHo().equals("Chủ Hộ")) {
                                preparedStmtUpdateQuanHeThanhVien.setString(1,new String(""));
                            } else {
                                preparedStmtUpdateQuanHeThanhVien.setString(1,nhanKhau.getQuanHeVoiChuHo());
                            }
                            preparedStmtUpdateQuanHeThanhVien.setString(2,maHoKhau);
                            preparedStmtUpdateQuanHeThanhVien.setString(3,nhanKhau.getMaNhanKhau());
                            preparedStmtUpdateQuanHeThanhVien.execute();

                            // Cập nhật lịch sử thay đổi hộ khẩu
                            String updateLSTDSql = "INSERT INTO lich_su_thay_doi (NgayThayDoi, GhiChu) VALUES (?, ?)";
                            String updateLSTDHKSql = "INSERT INTO lich_su_thay_doi_ho_khau (ThayDoiChuHo, ThemNhanKhau, XoaNhanKhau, MaHoKhau, MaNhanKhau, MaLSTD) VALUES  (?, ?, ?, ?, ?, ?)";
                            PreparedStatement preparedStmtUpdateLSHK = connection.prepareStatement(updateLSTDHKSql);
                            PreparedStatement preparedStmtUpdateLS = connection.prepareStatement(updateLSTDSql);


                            preparedStmtUpdateLS.setDate(1,ngayTao);
                            preparedStmtUpdateLS.setString(2,"Tách hộ khẩu");
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
                            preparedStmtUpdateLSHK.setString(5,nhanKhau.getMaNhanKhau() );
                            preparedStmtUpdateLSHK.setInt(6,lastId );
                            preparedStmtUpdateLSHK.execute();

                        }


                        nhanKhauOldTable.getItems().clear();
                        nhanKhauNewTable.getItems().clear();
                    } else {
                        throw new SQLException("Creating nhanKhau failed, no ID obtained.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

