package com.cnpm.controllers;
import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.MinhChungTableModel;
import com.cnpm.utilities.PhatQuaTableModel;
import com.cnpm.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class PhatQuaController implements Initializable{
    @FXML
    private Button them_qua;

    @FXML
    private TextField thong_tin_tim_kiem;
    @FXML
    private ChoiceBox loc;
    @FXML
    private ChoiceBox<String> optionChoiceBox;

    @FXML
    private Button findBtn;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TableView<PhatQuaTableModel> table;
    @FXML
    private TableColumn<PhatQuaTableModel, String> idPhatQuaCol;
    @FXML
    private TableColumn<PhatQuaTableModel, String> hoTenCol;
    @FXML
    private TableColumn<PhatQuaTableModel, String> tenQuaCol;
    @FXML
    private TableColumn<PhatQuaTableModel, String> tuoiCol;
    @FXML
    private TableColumn<PhatQuaTableModel, String> tenDotPhatCol;
    @FXML
    private TableColumn<PhatQuaTableModel, String> giaTriCol;
    @FXML
    private TableColumn<PhatQuaTableModel, String> daDuyetCol;
    PhatQuaTableModel data =null;
    ObservableList<PhatQuaTableModel> listView = FXCollections.observableArrayList();

    @FXML
    private TableColumn<PhatQuaController, String> xoaCol;
    private List<PhatQuaTableModel> performFiltering(String option, String searchText) {
        List<PhatQuaTableModel> filteredData = new ArrayList<>();
        for (PhatQuaTableModel data : table.getItems()) {
            if (data.getHoTen().equals(option) && data.getHoTen().contains(searchText)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }
    public void refresh(){
        table.getItems().clear();
        Connection connection = DBConnection.getConnection();
        String sql ="SELECT idPhatQua,nhan_khau.hoTen, (YEAR(CURDATE()) - YEAR(nhan_khau.namSinh)) as tuoi, tenQua, tenDotPhat, giaTri, daDuyet\n" +
                "                FROM nhan_khau, phat_qua, qua, dot_phat\n" +
                "                WHERE nhan_khau.ID = phat_qua.ma_nhan_khau\n" +
                "\t\t\t          AND phat_qua.idQua = qua.idQua\n" +
                "                AND phat_qua.idDotPhat = dot_phat.idDotPhat";
        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                listView.add(new PhatQuaTableModel(queryResult.getInt("idPhatQua"),
                        queryResult.getString("hoTen"),
                        queryResult.getString("tenQua"),
                        queryResult.getInt("tuoi"),
                        queryResult.getString("tenDotPhat"),
                        queryResult.getInt("giaTri"),
                        queryResult.getInt("daDuyet")));

            }
            table.setItems(listView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idPhatQuaCol.setCellValueFactory(new PropertyValueFactory<>("idPhatQua"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        tenQuaCol.setCellValueFactory(new PropertyValueFactory<>("tenQua"));
        xoaCol.setCellValueFactory(new PropertyValueFactory<>("deleteBox"));
        tuoiCol.setCellValueFactory(new PropertyValueFactory<>("tuoi"));
        tenDotPhatCol.setCellValueFactory(new PropertyValueFactory<>("tenDotPhat"));
        giaTriCol.setCellValueFactory(new PropertyValueFactory<>("giaTri"));
        daDuyetCol.setCellValueFactory(new PropertyValueFactory<>("daDuyet"));

        optionChoiceBox.getItems().addAll("Tìm theo họ tên","Tìm theo tuổi");
        optionChoiceBox.setValue("Lựa chọn tìm kiếm");

        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<PhatQuaTableModel> filteredData = new FilteredList<>(listView, b -> true);
            keywordTextField.textProperty().addListener(((observable1, oldValue1, newValue1) -> {
                filteredData.setPredicate(data -> {
                    if (newValue1.isEmpty() || newValue1.isBlank() || newValue1 == null) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if (data.getHoTen().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên") {
                        return true;
                    } else if (Integer.toString(data.getTuoi()).toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo tuổi") {
                        return true;
                    } else
                        return false;
                });
            }));
            // Sắp xếp thứ tự
            SortedList<PhatQuaTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });
        refresh();


    }
        public void xoaQua(ActionEvent event) {

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
            confirmationLayout.add(new Label("Bạn có muốn tiếp tục xóa phần quà này không ?"), 0, 0, 2, 1);
            confirmationLayout.add(yesButton, 0, 1);
            confirmationLayout.add(noButton, 1, 1);

            Scene confirmationScene = new Scene(confirmationLayout, 300, 100);
            Stage confirmationStage = new Stage();
            confirmationStage.setScene(confirmationScene);
            confirmationStage.show();




            //Tìm kiếm những hộ khẩu được tích checkbox
            ObservableList<PhatQuaTableModel> dataListRemove = FXCollections.observableArrayList();
            for (PhatQuaTableModel data: table.getItems()) {
                if(data.getDeleteBox().isSelected()) {
                    dataListRemove.add(data);
                }
            }

            noButton.setOnAction(e1 -> {
                confirmationStage.close();
            });

            yesButton.setOnAction(e2 -> {
                //Cập nhật CSDL khi xóa hộ khẩu
                String delteQSql = "DELETE FROM phat_qua WHERE idPhatQua = ?";
                Connection connection = DBConnection.getConnection();
                try {
                    PreparedStatement preparedDeleteHKStmt = connection.prepareStatement(delteQSql);
                    for(PhatQuaTableModel data: dataListRemove) {
                        int idPhatQua  = data.getIdPhatQua();
                        preparedDeleteHKStmt.setInt(1,idPhatQua);
                        preparedDeleteHKStmt.execute();
                    }
                    // Nảy ra màn hình xóa dữ liệu thành công
                    Utilities.popNewWindow(e2,"/com/cnpm/scenes/xoa-thanh-cong.fxml");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                refresh();
                confirmationStage.close();
            });

        }


    @FXML
    public void themQua(ActionEvent event) throws IOException {
        refresh();
        Utilities.popNewWindow(event, "/com/cnpm/scenes/them_qua.fxml");
    }



}
