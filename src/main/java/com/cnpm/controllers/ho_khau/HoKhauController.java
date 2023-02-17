package com.cnpm.controllers.ho_khau;

import com.cnpm.entities.NhanKhauTableModel;
import com.cnpm.utilities.DBConnection;
import com.cnpm.entities.HoKhauTableModel;
import com.cnpm.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HoKhauController implements Initializable {
    @FXML
    private Button them_ho_khau;
    @FXML
    private Button tach_ho_khau;
    @FXML
    private Button chuyen_ho_khau;

    @FXML
    private Button tim_kiem_ho_khau;

    @FXML
    private ChoiceBox<String> optionChoiceBox;

    @FXML
    private Button findBtn;
    @FXML
    private Label errorLab;
    @FXML
    private TextField keywordTextField;

    @FXML
    private TableView<HoKhauTableModel> table;
    @FXML
    private TableColumn<HoKhauTableModel, String> maHoKhauCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> hoTenChuHoCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> diaChiCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> soThanhVienCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> xoaCol;

    ObservableList<HoKhauTableModel> listView = FXCollections.observableArrayList();

    public void refreshTable() {
        table.getItems().clear();
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT ho_khau.ID,nhan_khau.hoTen AS hoTenChuHo, diaChi, COUNT(thanh_vien_cua_ho.idHoKhau) " +
                "AS soThanhVien FROM ho_khau, thanh_vien_cua_ho, nhan_khau " +
                "WHERE ho_khau.idChuHo = nhan_khau.ID and thanh_vien_cua_ho.idHoKhau = ho_khau.ID " +
                "AND ho_khau.daXoa is NULL and ho_khau.ngayChuyenDi is null " +
                "GROUP BY thanh_vien_cua_ho.idHoKhau";
        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                listView.add(new HoKhauTableModel(queryResult.getString("ID"),
                        queryResult.getString("hoTenChuHo"),
                        queryResult.getString("diaChi"),
                        queryResult.getString("SoThanhVien")));
            }
            table.setItems(listView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLab.setText("");

        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        hoTenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("hoTenChuHo"));
        xoaCol.setCellValueFactory(new PropertyValueFactory<>("deleteBox"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChiHoKhau"));
        soThanhVienCol.setCellValueFactory(new PropertyValueFactory<>("soThanhVien"));

        optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm theo ID", "Tìm theo số thành viên");
        optionChoiceBox.setValue("Chọn tìm kiếm theo ...");


        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<HoKhauTableModel> filteredData = new FilteredList<>(listView, b->true);
            keywordTextField.textProperty().addListener(((observable1, oldValue1, newValue1)->{
                filteredData.setPredicate(hoKhau->{
                    if(newValue1.isEmpty() || newValue1.isBlank() || newValue1 == null) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if(hoKhau.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo ID" ) {
                        return true;
                    } else if(hoKhau.getHoTenChuHo().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên") {
                        return true;
                    } else if(hoKhau.getSoThanhVien().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo số thành viên") {
                        return true;
                    } else
                        return false;
                });
            } ));
            // Sắp xếp thứ tự
            SortedList<HoKhauTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });

        refreshTable();

    }
    @FXML
    public void themHoKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/them-ho-khau.fxml");
    }
    public void doiChuHo(ActionEvent event) throws  IOException {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/doi-chu-ho.fxml");
    }

    public void tachHoKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/tach-ho-khau.fxml");
    }

    public void chuyenHoKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/chuyen-ho-khau.fxml");
    }
    public void lichSuThayDoi(ActionEvent event) {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/lich-su-thay-doi.fxml");
    }
    public void xoaHoKhau(ActionEvent event) {
        boolean canDelete = false;
        for(HoKhauTableModel hk : table.getItems()) {
            if(hk.getDeleteBox().isSelected()) canDelete = true;
        }
        if(canDelete) {
            // Nảy ra màn hình liệu có tiếp tục muốn xóa
            // Tạo ra scene
            Button yesButton = new Button("Có");
            yesButton.setPrefSize(100, 40);
            yesButton.getStyleClass().add("yes-button");

            Button noButton = new Button("Không");
            noButton.setPrefSize(100, 40);
            noButton.getStyleClass().add("no-button");

            GridPane confirmationLayout = new GridPane();
            confirmationLayout.setVgap(10);
            confirmationLayout.setHgap(10);
            confirmationLayout.setPadding(new Insets(20, 20, 20, 20));
            confirmationLayout.add(new Label("Bạn có muốn tiếp tục xóa hộ khẩu này không ?"), 0, 0, 2, 1);
            confirmationLayout.add(yesButton, 0, 1);
            confirmationLayout.add(noButton, 1, 1);

            Scene confirmationScene = new Scene(confirmationLayout, 300, 100);
            Stage confirmationStage = new Stage();
            confirmationStage.setScene(confirmationScene);
            confirmationStage.show();
            //Tìm kiếm những hộ khẩu được tích checkbox
            ObservableList<HoKhauTableModel> dataListRemove = FXCollections.observableArrayList();
            for (HoKhauTableModel hoKhau: table.getItems()) {
                if(hoKhau.getDeleteBox().isSelected()) {
                    dataListRemove.add(hoKhau);
                }
            }

            noButton.setOnAction(e1 -> {
                confirmationStage.close();
            });

            yesButton.setOnAction(e2 -> {
                //Cập nhật CSDL khi xóa hộ khẩu
                String delteHKSql = "UPDATE ho_khau SET daXoa = 1 WHERE ID = ?";
                String delteQHSql = "DELETE FROM thanh_vien_cua_ho WHERE IdHoKhau = ?";
                Connection connection = DBConnection.getConnection();
                Date ngayTao = new Date(System.currentTimeMillis());
                ObservableList<NhanKhauTableModel> nhanKhauRemove = FXCollections.observableArrayList();
                try {
                    PreparedStatement preparedDeleteHKStmt = connection.prepareStatement(delteHKSql);
                    PreparedStatement preparedDeleteQHStmt = connection.prepareStatement(delteQHSql);
                    for(HoKhauTableModel hoKhau: dataListRemove) {
                        String idHoKhau  = hoKhau.getMaHoKhau();
                        preparedDeleteQHStmt.setString(1,idHoKhau);
                        preparedDeleteHKStmt.setString(1,idHoKhau);
                        // Cập nhật lịch sử thay đổi hộ khẩu
                        String updateLSTDSql = "INSERT INTO lich_su_thay_doi (NgayThayDoi, GhiChu) VALUES (?, ?)";
                        String updateLSTDHKSql = "INSERT INTO lich_su_thay_doi_ho_khau (ThayDoiChuHo, ThemNhanKhau, XoaNhanKhau, IdHoKhau, IdNhanKhau, IdLSTD) VALUES  (?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStmtUpdateLSHK = connection.prepareStatement(updateLSTDHKSql);
                        PreparedStatement preparedStmtUpdateLS = connection.prepareStatement(updateLSTDSql);
                        preparedStmtUpdateLS.setDate(1,ngayTao);
                        preparedStmtUpdateLS.setString(2,"Xóa hộ khẩu");
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
                        String nhanKhauAddSql = "Select * from nhan_khau,thanh_vien_cua_ho where  thanh_vien_cua_ho.idNhanKhau = nhan_khau.ID and thanh_vien_cua_ho.idHoKhau = ? and nhan_khau.daXoa is NULL";
                        PreparedStatement  preparedAddNhanKhauStmt= connection.prepareStatement(nhanKhauAddSql);
                        preparedAddNhanKhauStmt.setString(1,hoKhau.getMaHoKhau());
                        ResultSet queryResult = preparedAddNhanKhauStmt.executeQuery();
                        // Tìm các nhân khẩu có mã hộ khẩu xóa
                        while (queryResult.next()) {
                            nhanKhauRemove.add(new NhanKhauTableModel(
                                    queryResult.getString("ID"),
                                    queryResult.getString("hoTen"),
                                    queryResult.getString("quanHeVoiChuHo")
                            ));
                        }
                        for (NhanKhauTableModel nhanKhau : nhanKhauRemove) {
                            preparedStmtUpdateLSHK.setString(1,"0");
                            preparedStmtUpdateLSHK.setString(2, "0");
                            preparedStmtUpdateLSHK.setString(3, "1");
                            preparedStmtUpdateLSHK.setString(4, hoKhau.getMaHoKhau());
                            preparedStmtUpdateLSHK.setString(5, nhanKhau.getMaNhanKhau());
                            preparedStmtUpdateLSHK.setInt(6,lastId );
                            preparedStmtUpdateLSHK.execute();
                            preparedDeleteQHStmt.execute();
                            preparedDeleteHKStmt.execute();
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                refreshTable();
                confirmationStage.close();
            });
        } else {
            errorLab.setText("Vui lòng chọn ít nhất 1 hộ khẩu cần xóa !");
        }
    }

    public void themThanhVien(ActionEvent event) {
        Utilities.popNewWindow(event, "/com/cnpm/chuc-nang-view/ho-khau-chuc-nang-view/them-thanh-vien.fxml");
    }
}
