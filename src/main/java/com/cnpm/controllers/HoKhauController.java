package com.cnpm.controllers;

import com.cnpm.entities.HoKhau;
import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.HoKhauTableModel;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    private void updateUI(List<HoKhauTableModel> filteredData) {
        table.setItems(FXCollections.observableArrayList(filteredData));
    }
    private List<HoKhauTableModel> performFiltering(String option, String searchText) {
        List<HoKhauTableModel> filteredData = new ArrayList<>();
        for (HoKhauTableModel data : table.getItems()) {
            if (data.getHoTenChuHo().equals(option) && data.getHoTenChuHo().contains(searchText)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection connection = DBConnection.getConnection();
        String sql = "SELECT ho_khau.ID,nhan_khau.hoTen AS hoTenChuHo, diaChi, COUNT(thanh_vien_cua_ho.idHoKhau) AS soThanhVien FROM ho_khau, thanh_vien_cua_ho, nhan_khau WHERE ho_khau.ID = thanh_vien_cua_ho.idHoKhau AND nhan_khau.ID = thanh_vien_cua_ho.idNhanKhau GROUP BY ho_khau.ID";

        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        hoTenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("hoTenChuHo"));
        xoaCol.setCellValueFactory(new PropertyValueFactory<>("deleteBox"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChiHoKhau"));
        soThanhVienCol.setCellValueFactory(new PropertyValueFactory<>("soThanhVien"));

        optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm theo ID", "Tìm theo số thành viên");
        optionChoiceBox.setValue("Lựa chọn tìm kiếm");

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
    @FXML
    public void themHoKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/them-ho-khau.fxml");
    }
    public void doiChuHo(ActionEvent event) throws  IOException {
        Utilities.popNewWindow(event,"/com/cnpm/scenes/doi-chu-ho.fxml");
    }

    public void tachHoKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/tach-ho-khau.fxml");
    }

    public void chuyenHoKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/chuyen-ho-khau.fxml");
    }
    public void lichSuThayDoi(ActionEvent event) {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/lich-su-thay-doi.fxml");
    }
    private void restartScene(Scene scene) {
        Stage stage = (Stage) scene.getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
    public void xoaHoKhau(ActionEvent event) {

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
            String delteHKSql = "DELETE FROM ho_khau WHERE ID = ?";
            String delteQHSql = "DELETE FROM thanh_vien_cua_ho WHERE IdHoKhau = ?";
            Connection connection = DBConnection.getConnection();
            try {
                PreparedStatement preparedDeleteHKStmt = connection.prepareStatement(delteHKSql);
                PreparedStatement preparedDeleteQHStmt = connection.prepareStatement(delteQHSql);
                for(HoKhauTableModel hoKhau: dataListRemove) {
                    String idHoKhau  = hoKhau.getMaHoKhau();
                    preparedDeleteQHStmt.setString(1,idHoKhau);
                    preparedDeleteHKStmt.setString(1,idHoKhau);
                    preparedDeleteQHStmt.execute();
                    preparedDeleteHKStmt.execute();
                }
                // Nảy ra màn hình xóa dữ liệu thành công
                Utilities.popNewWindow(e2,"/com/cnpm/scenes/xoa-thanh-cong.fxml");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            confirmationStage.close();
            restartScene(table.getScene());
        });
    }
}
