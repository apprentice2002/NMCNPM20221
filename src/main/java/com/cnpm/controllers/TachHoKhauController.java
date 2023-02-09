package com.cnpm.controllers;

import com.cnpm.entities.NhanKhau;
import com.cnpm.utilities.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
    private TableColumn<HoKhauTableModel, String> maHoKhauCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> hoTenChuHoCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> diaChiCol;
    @FXML
    private TableView<HoKhauTableModel> hoKhauTable;
    ObservableList<HoKhauTableModel> hoKhauView = FXCollections.observableArrayList();

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
    private TextField maHoKhauMoiTxt;
    @FXML
    private TextField diaChiTxt;
    @FXML
    private TextField maKhuVucTxt;
    @FXML
    private TextField chuHoCuTxt;
    @FXML
    private TextField chuHoMoiTxt;
    @FXML
    private TextField maHoKhauTxt;
    @FXML
    private Button cancleBtn;

    @FXML
    private ChoiceBox<String> optionChoiceBox;

    Connection connection = DBConnection.getConnection();

    private SharedDataModel sharedDataModel = new SharedDataModel();


    String hoKhauSql = "SELECT ho_khau.ID as MaHoKhau, nhan_khau.hoTen AS TenChuHo, diaChi as DiaChiHoKhau FROM nhan_khau, ho_khau WHERE nhan_khau.ID = IdChuHo";
    String nhanKhauSql;
    String submitSql;

    private ChoiceBox<String> choiceBox;

    private void restartScene(Scene scene) {
        Stage stage = (Stage) scene.getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

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
                hoKhauView.add(new HoKhauTableModel(
                        queryResult.getString("MaHoKhau"),
                        queryResult.getString("TenChuHo"),
                        queryResult.getString("DiaChiHoKhau")));
            }
            hoKhauTable.setItems(hoKhauView);
            optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm theo ID");
            optionChoiceBox.setValue("Lựa chọn tìm kiếm");

            // Thêm action cho choicebox
            optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                //Lọc theo TextField
                FilteredList<HoKhauTableModel> filteredData = new FilteredList<>(hoKhauView, b->true);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        hoKhauTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    nhanKhauOldTable.getItems().clear();
                    nhanKhauNewTable.getItems().clear();
                    HoKhauTableModel selectedRow = hoKhauTable.getSelectionModel().getSelectedItem();
                    if (selectedRow != null) {
                        String maHoKhau = selectedRow.getMaHoKhau();
                        nhanKhauSql = "SELECT nhan_khau.ID AS MaNhanKhau, nhan_khau.hoTen as HoTen, quanHeVoiChuHo as QuanHeVoiChuHo FROM nhan_khau, thanh_vien_cua_ho " +
                                " WHERE thanh_vien_cua_ho.IdHoKhau = " + maHoKhau +
                                " AND thanh_vien_cua_ho.IdNhanKhau = nhan_khau.ID AND thanh_vien_cua_ho.quanHeVoiChuHo !='' ";
                        try {
                            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
                            Statement statement = connection.createStatement();
                            ResultSet queryResult = statement.executeQuery(nhanKhauSql);
                            // Cập nhật họ tên Chủ hộ cũ
                            chuHoCuTxt.setText(selectedRow.getHoTenChuHo());
                            maHoKhauTxt.setText(maHoKhau);
                            //Xóa họ tên chủ hộ mới
                            chuHoMoiTxt.setText("");
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
        maChuHoMoiTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()|| newValue.isBlank()) {
                chuHoMoiTxt.setText("");
            } else {
                //Tìm kiếm họ tên của mã chủ hộ mới
                String findHoTenChuHoSql = "SELECT hoTen as HoTen FROM nhan_khau WHERE ID = ?";
                try {
                    PreparedStatement preparedStmtFindName = connection.prepareStatement(findHoTenChuHoSql);
                    preparedStmtFindName.setString(1,maChuHoMoiTxt.getText());
                    ResultSet rs = preparedStmtFindName.executeQuery();
                    if(rs.next()) {
                        String hoTenChuHo = rs.getString("HoTen");
                        chuHoMoiTxt.setText(hoTenChuHo);
                    } else {
                        chuHoMoiTxt.setText("");
                        System.out.println("Không tìm thấy mã nhân khẩu tương ứng, vui lòng thử lại");
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void chonChuHo(ActionEvent event) throws IOException  {
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

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void cancle(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }

    public void submit(ActionEvent event) {
        List<NhanKhauTableModel> dataNhanKhauCu = nhanKhauNewTable.getItems();
        List<NhanKhauTableModel> dataNhanKhauMoi = nhanKhauOldTable.getItems();

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
        confirmationLayout.add(new Label("Bạn có muốn tiếp tục tách hộ khẩu này không ?"), 0, 0, 2, 1);
        confirmationLayout.add(yesButton, 0, 1);
        confirmationLayout.add(noButton, 1, 1);

        Scene confirmationScene = new Scene(confirmationLayout, 300, 100);
        Stage confirmationStage = new Stage();
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();

        noButton.setOnAction(e1 -> {
            confirmationStage.close();
        });

        yesButton.setOnAction(e2 -> {
            //Cập nhật CSDL khi xóa hộ khẩu
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
                    // Kiểm tra các text liệu có để rỗng
                    String maChuHoMoi = maChuHoMoiTxt.getText();
                    String diaChi = diaChiTxt.getText();
                    String maKhuVuc = maKhuVucTxt.getText();
                    Date ngayTao = new Date(System.currentTimeMillis());
                    String maHoKhauMoi = maHoKhauMoiTxt.getText();
                    if(maChuHoMoi.isEmpty()|| diaChi.isEmpty() || maKhuVuc.isEmpty()||maHoKhauMoi.isEmpty()) {
                        Utilities.popNewWindow(event,"/resources/com/cnpm/scenes/alert.fxml");
                    }
                    // Tạo hộ khẩu mới
                    String newHoKhauSql = "INSERT INTO ho_khau (ngayLap, maKhuVuc, idChuHo, diaChi, maHoKhau) " +
                            "VALUES ( ?, ?, ?, ?,?)";
                    PreparedStatement preparedStmtInsert = connection.prepareStatement(newHoKhauSql,Statement.RETURN_GENERATED_KEYS);
                    if(diaChi.isEmpty() || maKhuVuc.isEmpty() || maChuHoMoi.isEmpty()|| maHoKhauMoi.isEmpty()) {
                        System.out.println("Vui lòng nhập đầy đủ thông tin !");
                    } else {
                        preparedStmtInsert.setDate(1, ngayTao);
                        preparedStmtInsert.setString(2, maKhuVuc);
                        preparedStmtInsert.setString(3, maChuHoMoi);
                        preparedStmtInsert.setString(4, diaChi);
                        preparedStmtInsert.setString(5, maHoKhauMoi);
                    }
                    int affectedRows = preparedStmtInsert.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("Creating nhanKhau failed, no rows affected.");
                    }

                    try (ResultSet generatedKeys = preparedStmtInsert.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            String maHoKhau = generatedKeys.getString(1);
                            // Cập nhật hộ khẩu cũ
                            submitSql = "UPDATE thanh_vien_cua_ho SET IdHoKhau = ? WHERE IdNhanKhau = ?";
                            PreparedStatement preparedStmtUpdate = connection.prepareStatement(submitSql);
                            // Cập nhật thàn viên hộ khẩu mới.
                            for (NhanKhauTableModel nhanKhau : nhanKhauNewTable.getItems()) {
                                preparedStmtUpdate.setString(1, maHoKhau);
                                preparedStmtUpdate.setString(2, nhanKhau.getMaNhanKhau());
                                preparedStmtUpdate.execute();
                            }

                            // Cập nhật quan hệ các thành viên chủ hộ mới
                            String updateQuanHeSql = "UPDATE thanh_vien_cua_ho SET quanHeVoiChuHo = ?, IdHoKhau = ? WHERE IdNhanKhau = ?";
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
                                String updateLSTDHKSql = "INSERT INTO lich_su_thay_doi_ho_khau (ThayDoiChuHo, ThemNhanKhau, XoaNhanKhau, IdHoKhau, IdNhanKhau, IdLSTD) VALUES  (?, ?, ?, ?, ?, ?)";
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
            confirmationStage.close();
            Utilities.popNewWindow(event, "/com/cnpm/scenes/xoa-thanh-cong.fxml");
            restartScene(hoKhauTable.getScene());
        });
    }

}

