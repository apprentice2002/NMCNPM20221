package com.cnpm.controllers.MinhChung;


import com.cnpm.utilities.*;
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

public class MinhChungcontroller implements Initializable {
    @FXML
    private Button them_minh_chung;

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
    private TableView<MinhChungTableModel> table;
    @FXML
    private TableColumn<MinhChungTableModel, String> idMinhChungCol;
    @FXML
    private TableColumn<MinhChungTableModel, String> hoTenCol;
    @FXML
    private TableColumn<MinhChungTableModel, String> thanhTichHocTapCol;
    @FXML
    private TableColumn<MinhChungTableModel, String> truongCol;
    @FXML
    private TableColumn<MinhChungTableModel, String> lopCol;
    @FXML
    private TableColumn<MinhChungTableModel, String> ngayKhaiBaoCol;
    MinhChungTableModel data =null;
    ObservableList<MinhChungTableModel> listView = FXCollections.observableArrayList();

    @FXML
    private TableColumn<MinhChungTableModel, String> xoaCol;
    private List<MinhChungTableModel> performFiltering(String option, String searchText) {
        List<MinhChungTableModel> filteredData = new ArrayList<>();
        for (MinhChungTableModel data : table.getItems()) {
            if (data.getHoTen().equals(option) && data.getHoTen().contains(searchText)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }


    public void refresh(){table.getItems().clear();
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT idMinhChung,nhan_khau.hoTen, thanhTichHocTap,truong, lop, ngayKhaiBao\n" +
                "                FROM nhan_khau,minh_chung\n" +
                "                WHERE minh_chung.ma_nhan_khau = nhan_khau.ID";
        try {

            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                listView.add(new MinhChungTableModel(queryResult.getInt("idMinhChung"),
                        queryResult.getString("hoTen"),
                        queryResult.getString("thanhTichHocTap"),
                        queryResult.getString("truong"),
                        queryResult.getString("lop"),
                        queryResult.getDate("ngayKhaiBao")));

            }
            table.setItems(listView);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        idMinhChungCol.setCellValueFactory(new PropertyValueFactory<>("idMinhChung"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        truongCol.setCellValueFactory(new PropertyValueFactory<>("truong"));
        xoaCol.setCellValueFactory(new PropertyValueFactory<>("deleteBox"));
        thanhTichHocTapCol.setCellValueFactory(new PropertyValueFactory<>("thanhTichHocTap"));
        lopCol.setCellValueFactory(new PropertyValueFactory<>("lop"));
        ngayKhaiBaoCol.setCellValueFactory(new PropertyValueFactory<>("ngayKhaiBao"));

        optionChoiceBox.getItems().addAll("Tìm theo họ tên","Tìm theo thành tích học tập");
        optionChoiceBox.setValue("Lựa chọn tìm kiếm");

        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<MinhChungTableModel> filteredData = new FilteredList<>(listView, b -> true);
            keywordTextField.textProperty().addListener(((observable1, oldValue1, newValue1) -> {
                filteredData.setPredicate(data -> {
                    if (newValue1.isEmpty() || newValue1.isBlank() || newValue1 == null) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if (data.getHoTen().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên") {
                        return true;
                    } else if (data.getThanhTichHocTap().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo thành tích học tập"){
                        return true;
                    } else
                        return false;
                });
            }));
            // Sắp xếp thứ tự
            SortedList<MinhChungTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });

        refresh();
    }
    public void xoaMinhChung(ActionEvent event) {

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
        confirmationLayout.add(new Label("Bạn có muốn tiếp tục xóa minh chứng này không ?"), 0, 0, 2, 1);
        confirmationLayout.add(yesButton, 0, 1);
        confirmationLayout.add(noButton, 1, 1);

        Scene confirmationScene = new Scene(confirmationLayout, 300, 100);
        Stage confirmationStage = new Stage();
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();




        //Tìm kiếm những hộ khẩu được tích checkbox
        ObservableList<MinhChungTableModel> dataListRemove = FXCollections.observableArrayList();
        for (MinhChungTableModel data: table.getItems()) {
            if(data.getDeleteBox().isSelected()) {
                dataListRemove.add(data);
            }
        }

        noButton.setOnAction(e1 -> {
            confirmationStage.close();
        });

        yesButton.setOnAction(e2 -> {
            //Cập nhật CSDL khi xóa hộ khẩu
            String delteQSql = "DELETE FROM minh_chung WHERE idMinhChung = ?";
            try {
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedDeleteHKStmt = connection.prepareStatement(delteQSql);
                for(MinhChungTableModel data: dataListRemove) {

                    int idMinhChung  = data.getIdMinhChung();
                    System.out.println(idMinhChung);
                    preparedDeleteHKStmt.setInt(1,idMinhChung);
                    preparedDeleteHKStmt.execute();

                }
                // Nảy ra màn hình xóa dữ liệu thành công
                Utilities.popNewWindow(e2,"/com/cnpm/scenes/xoa-thanh-cong.fxml");
            } catch (Exception e) {
                System.out.println(e);
            }
            refresh();
            confirmationStage.close();
        });


    }

    @FXML
    public void themMinhChung(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/them_minh_chung.fxml");
        refresh();
    }

}