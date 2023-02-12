package com.cnpm.controllers;

import com.cnpm.entities.NhanKhau;
import com.cnpm.utilities.DBConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;
import java.sql.ResultSet;

public class NhanKhauController implements Initializable {

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement1 = null;
    ResultSet resultSet = null;
    ObservableList<NhanKhau> NhanKhauList = FXCollections.observableArrayList();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    public void refresh() {
        NhanKhauList.clear();
        try {
            preparedStatement1 = connection.prepareStatement(query);
            resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                NhanKhauList.add(new NhanKhau(
                        resultSet.getInt("ID"),
                        resultSet.getString("maNhanKhau"),
                        resultSet.getString("hoTen"),
                        formatter.format(resultSet.getDate("namSinh")),
                        resultSet.getString("diaChiHienNay")
                ));
            }
            table.setItems(NhanKhauList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void themNhanKhau(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/them-nhan-khau.fxml");
    }

    @FXML private ChoiceBox<String> optionChoiceBox;
    @FXML private TextField keywordTextField;
    @FXML private TableColumn<NhanKhau, String> diaChiHienNayCol;
    @FXML private TableColumn<NhanKhau, String> hoTenCol;
    @FXML private TableColumn<NhanKhau, String> maNhanKhauCol;
    @FXML private TableColumn<NhanKhau, String> ngaySinhCol;
    @FXML private TableColumn<NhanKhau, Integer> idCol;
    @FXML private TableColumn<NhanKhau, CheckBox> xoaCol;
    @FXML private TableView<NhanKhau> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();
    }

    private void load() {
        query = "SELECT ID, maNhanKhau, hoTen, namSinh, diaChiHienNay FROM nhan_khau";
        connection = DBConnection.getConnection();

        diaChiHienNayCol.setCellValueFactory(new PropertyValueFactory<>("dia_chi_hien_nay"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("ho_ten"));
        maNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("ma_nhan_khau"));
        ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("ngay_sinh"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        xoaCol.setCellValueFactory(new PropertyValueFactory<>("deleteBox"));

        optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm theo ID");
        optionChoiceBox.setValue("Lựa chọn tìm kiếm");

        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<NhanKhau> filteredData = new FilteredList<>(NhanKhauList, b->true);
            keywordTextField.textProperty().addListener(((observable1, oldValue1, newValue1)->{
                filteredData.setPredicate(nhanKhau->{
                    if(newValue1.isEmpty() || newValue1.isBlank()) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if (Integer.toString(nhanKhau.getID()).contains(searchKeyword) && Objects.equals(optionChoiceBox.getSelectionModel().getSelectedItem(), "Tìm theo ID")) {
                        return true;
                    }
                    return nhanKhau.getHo_ten().toLowerCase().contains(searchKeyword) && Objects.equals(optionChoiceBox.getSelectionModel().getSelectedItem(), "Tìm theo họ tên");
                });
            } ));
            // Sắp xếp thứ tự
            SortedList<NhanKhau> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });

        refresh();
    }

    private void restartScene(Scene scene) {
        Stage stage = (Stage) scene.getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void dangKyTamVang(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/dang-ky-tam-vang.fxml");
    }

    @FXML
    public void dangKyTamTru(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/dang-ky-tam-tru.fxml");
    }

    @FXML
    public void xoaNhanKhau(ActionEvent event) {
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
        confirmationLayout.add(new Label("Bạn có muốn tiếp tục xóa nhân khẩu này không ?"), 0, 0, 2, 1);
        confirmationLayout.add(yesButton, 0, 1);
        confirmationLayout.add(noButton, 1, 1);

        Scene confirmationScene = new Scene(confirmationLayout, 300, 100);
        Stage confirmationStage = new Stage();
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();
        //Tìm kiếm những hộ khẩu được tích checkbox
        ObservableList<NhanKhau> dataListRemove = FXCollections.observableArrayList();
        for (NhanKhau nhanKhau: table.getItems()) {
            if(nhanKhau.getDeleteBox().isSelected()) {
                dataListRemove.add(nhanKhau);
            }
        }

        noButton.setOnAction(e1 -> {
            confirmationStage.close();
        });

        yesButton.setOnAction(e2 -> {
            //Cập nhật CSDL khi xóa hộ khẩu
            String delete_query = "DELETE FROM nhan_khau WHERE hoTen=?";
            try {
                for(NhanKhau nhanKhau: dataListRemove) {
                    PreparedStatement preparedStatement = connection.prepareStatement(delete_query);
                    preparedStatement.setString(1, nhanKhau.getHo_ten());
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            confirmationStage.close();
            restartScene(table.getScene());
        });
    }

    @FXML
    public void thayDoiNhanKhau(ActionEvent event) {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/thay-doi-nhan-khau.fxml");
    }
}
