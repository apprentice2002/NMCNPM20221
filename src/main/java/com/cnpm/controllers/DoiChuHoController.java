package com.cnpm.controllers;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.HoKhauTableModel;
import com.cnpm.utilities.NhanKhauTableModel;
import com.cnpm.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DoiChuHoController implements Initializable {

    @FXML
    private Button cancleBtn;
    @FXML
    private TextField maHoKhauTxt;
    @FXML
    private ChoiceBox<String> quanHeVoiChuHoChoiceBox;
    @FXML
    private TextField maChuHoOldTxt;
    @FXML
    private TextField hoTenOldTxt;
    @FXML
    private TextField maChuHoNewTxt;
    @FXML
    private TextField hoTenMoiTxt;
    @FXML
    private TableView<HoKhauTableModel> hoKhauTable;
    @FXML
    private TableView<NhanKhauTableModel> nhanKhauTable;
    @FXML
    private TableColumn<HoKhauTableModel, String> maHoKhauCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> hoTenChuHoCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> diaChiCol;
    @FXML
    private Label errorLab;

    @FXML
    private TableColumn<NhanKhauTableModel, String> maNhanKhauCol;
    @FXML
    private TableColumn<NhanKhauTableModel, String> hoTenNhanKhauCol;
    @FXML
    private TableColumn<NhanKhauTableModel, String> quanHeVoiChuHoCol;

    public void submit(ActionEvent event) {
        if(maChuHoNewTxt.getText().equals("")||maChuHoOldTxt.getText().equals("")||
                hoTenOldTxt.getText().equals("")||hoTenMoiTxt.getText().equals("")||
                quanHeVoiChuHoChoiceBox.getValue().equals("")) {
               errorLab.setText("Vui lòng chọn đầy đủ các thông tin cần thiết");
        } else {
            String submitSql = "UPDATE ho_khau SET idChuHo =  ?" +
                    "WHERE ID = ?";
            String updateQuanHeVoiChuHo = "update thanh_vien_cua_ho set quanHeVoiChuHo = ? where idNhanKhau = ?";

            Connection connection = DBConnection.getConnection();
            try {
                //Thay đổi id chủ hộ của hộ khẩu
                PreparedStatement preparedStatement = connection.prepareStatement(submitSql);
                preparedStatement.setString(1,maChuHoNewTxt.getText());
                preparedStatement.setString(2, maHoKhauTxt.getText());
                preparedStatement.execute();
                // Thay đổi QHVCH của chủ hộ cũ
                preparedStatement = connection.prepareStatement(updateQuanHeVoiChuHo);
                preparedStatement.setString(1,quanHeVoiChuHoChoiceBox.getValue());
                preparedStatement.setString(2,maChuHoOldTxt.getText());
                preparedStatement.execute();
                // Thay đổi QHVCH của chủ hộ mới
                preparedStatement.setString(1,"");
                preparedStatement.setString(2,maChuHoNewTxt.getText());
                preparedStatement.execute();

                restartScene(nhanKhauTable.getScene());
                restartScene(hoKhauTable.getScene());
                // Refresh the table
                String hoKhauSql = "SELECT ho_khau.ID as MaHoKhau, nhan_khau.hoTen AS TenChuHo, diaChi as DiaChiHoKhau FROM nhan_khau, ho_khau WHERE nhan_khau.ID = IdChuHo";
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ObservableList<HoKhauTableModel> data = FXCollections.observableArrayList();
                ResultSet queryResult = statement.executeQuery(hoKhauSql);
                while (queryResult.next()) {
                    data.add(new HoKhauTableModel(
                            queryResult.getString("MaHoKhau"),
                            queryResult.getString("TenChuHo"),
                            queryResult.getString("DiaChiHoKhau")));
                }
                hoKhauTable.setItems(data);
                hoTenMoiTxt.setText("");
                hoTenOldTxt.setText("");
                maChuHoOldTxt.setText("");
                maChuHoNewTxt.setText("");
                maHoKhauTxt.setText("");
                errorLab.setText("");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void restartScene(Scene scene) {
        Stage stage = (Stage) scene.getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
    public void cancle(ActionEvent event) {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        quanHeVoiChuHoChoiceBox.getItems().addAll("Con","Bố","Mẹ", "Vợ", "Chồng", "Anh","Chị","Em", "Chú", "Bác");
        quanHeVoiChuHoChoiceBox.setValue("");
        errorLab.setText("");

        Connection connection = DBConnection.getConnection();

        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        hoTenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("hoTenChuHo"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChiHoKhau"));


        maNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        hoTenNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        quanHeVoiChuHoCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));

        String hoKhauSql = "SELECT ho_khau.ID as MaHoKhau, nhan_khau.hoTen AS TenChuHo, diaChi as DiaChiHoKhau FROM nhan_khau, ho_khau WHERE nhan_khau.ID = IdChuHo";

        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(hoKhauSql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                hoKhauTable.getItems().add(new HoKhauTableModel(
                        queryResult.getString("MaHoKhau"),
                        queryResult.getString("TenChuHo"),
                        queryResult.getString("DiaChiHoKhau")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        hoKhauTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    HoKhauTableModel selectedRow = hoKhauTable.getSelectionModel().getSelectedItem();
                    if (selectedRow != null) {
                        String maHoKhau = selectedRow.getMaHoKhau();
                        String nhanKhauSql = "SELECT nhan_khau.ID AS MaNhanKhau, nhan_khau.hoTen as HoTen, quanHeVoiChuHo as QuanHeVoiChuHo FROM nhan_khau, thanh_vien_cua_ho " +
                                " WHERE thanh_vien_cua_ho.IdHoKhau = " + maHoKhau +
                                " AND thanh_vien_cua_ho.IdNhanKhau = nhan_khau.ID AND thanh_vien_cua_ho.quanHeVoiChuHo !='' ";
                        try {
                            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
                            Statement statement = connection.createStatement();
                            ResultSet queryResult = statement.executeQuery(nhanKhauSql);
                            // Cập nhật họ tên Chủ hộ cũ
                            hoTenOldTxt.setText(selectedRow.getHoTenChuHo());
                            maChuHoOldTxt.setText(selectedRow.getMaChuHo());
                            maHoKhauTxt.setText(maHoKhau);
                            //Xóa họ tên chủ hộ mới
                            hoTenMoiTxt.setText("");
                            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
                            if(nhanKhauTable.getItems().isEmpty()){
                                while (queryResult.next()) {
                                    if(!queryResult.getString("QuanHeVoiChuHo").equals("")) {
                                        nhanKhauTable.getItems().add(new NhanKhauTableModel(
                                                queryResult.getString("MaNhanKhau"),
                                                queryResult.getString("HoTen"),
                                                queryResult.getString("QuanHeVoiChuHo")));
                                    }
                                }
                            } else {
                                nhanKhauTable.getItems().clear();
                                while (queryResult.next()) {
                                    nhanKhauTable.getItems().add(new NhanKhauTableModel(
                                            queryResult.getString("MaNhanKhau"),
                                            queryResult.getString("HoTen"),
                                            queryResult.getString("QuanHeVoiChuHo")));
                                }
                            }
                            String chuHoIdSql = "SELECT nhan_khau.ID from nhan_khau, ho_khau where nhan_khau.ID = ho_khau.IdChuHo and nhan_khau.hoTen = "+ "'"+ hoTenOldTxt.getText()+ "'" +
                                    " and ho_khau.ID = " + maHoKhauTxt.getText();
                            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet idQueryResult = stmt.executeQuery(chuHoIdSql);
                            idQueryResult.first();
                            maChuHoOldTxt.setText(idQueryResult.getString("ID"));
                            nhanKhauTable.setItems(nhanKhauTable.getItems());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        nhanKhauTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    NhanKhauTableModel selectedRow = nhanKhauTable.getSelectionModel().getSelectedItem();
                    if (selectedRow != null) {
                        String maNhanKhau = selectedRow.getMaNhanKhau();
                        String hoTenNhanKhau = selectedRow.getHoTenNhanKhau();
                        hoTenMoiTxt.setText(hoTenNhanKhau);
                        maChuHoNewTxt.setText(maNhanKhau);
                    }
                }
            }
        });
    }
}
