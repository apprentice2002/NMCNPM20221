package com.cnpm.controllers.PhatThuong;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.PhatThuongTableModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PhatThuongTTController implements Initializable {
    @FXML
    private Button thong_ke_thuong;
    @FXML
    private TextField note;
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
    private TableView<PhatThuongTableModel> table;
    @FXML
    private TableColumn<PhatThuongTableModel, String> idPhatThuongCol;

    @FXML
    private TableColumn<PhatThuongTableModel, String> hoTenCol;
    @FXML
    private TableColumn<PhatThuongTableModel, String> tenQuaCol;
    @FXML
    private TableColumn<PhatThuongTableModel, String> thanhTichHocTapCol;
    @FXML
    private TableColumn<PhatThuongTableModel, String> tenDotPhatCol;
    @FXML
    private TableColumn<PhatThuongTableModel, String> giaTriCol;
    @FXML
    private TableColumn<PhatThuongTableModel, String> daDuyetCol;
    PhatThuongTableModel data =null;
    ObservableList<PhatThuongTableModel> listView = FXCollections.observableArrayList();

    @FXML
    private TableColumn<PhatThuongTableModel, String> xoaCol;
    private List<PhatThuongTableModel> performFiltering(String option, String searchText) {
        List<PhatThuongTableModel> filteredData = new ArrayList<>();
        for (PhatThuongTableModel data : table.getItems()) {
            if (data.getHoTen().equals(option) && data.getHoTen().contains(searchText)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }
    public void refresh(){table.getItems().clear();
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT idPhatThuong,nhan_khau.hoTen, thanhTichHocTap, tenQua, tenDotPhat, giaTri, daDuyet\n" +
                "        FROM nhan_khau, phat_thuong,minh_chung, qua, dot_phat\n" +
                "        WHERE phat_thuong.idMinhChung = minh_chung.idMinhChung\n" +
                "        AND minh_chung.ma_nhan_khau = nhan_khau.ID\n" +
                "        AND phat_thuong.idQua = qua.idQua\n" +
                "        AND phat_thuong.idDotPhat = dot_phat.idDotPhat";
        try {
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                listView.add(new PhatThuongTableModel( queryResult.getInt("idPhatThuong"),
                        queryResult.getString("hoTen"),
                        queryResult.getString("tenQua"),
                        queryResult.getString("thanhTichHocTap"),
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

        idPhatThuongCol.setCellValueFactory(new PropertyValueFactory<>("idPhatThuong"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        tenQuaCol.setCellValueFactory(new PropertyValueFactory<>("tenQua"));

        thanhTichHocTapCol.setCellValueFactory(new PropertyValueFactory<>("thanhTichHocTap"));
        tenDotPhatCol.setCellValueFactory(new PropertyValueFactory<>("tenDotPhat"));
        giaTriCol.setCellValueFactory(new PropertyValueFactory<>("giaTri"));
        daDuyetCol.setCellValueFactory(new PropertyValueFactory<>("daDuyet"));
        xoaCol.setCellValueFactory(new PropertyValueFactory<>("deleteBox"));

        optionChoiceBox.getItems().addAll("Tìm theo họ tên","Tìm theo thành tích học tập");
        optionChoiceBox.setValue("Lựa chọn tìm kiếm");

        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<PhatThuongTableModel> filteredData = new FilteredList<>(listView, b -> true);
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
            SortedList<PhatThuongTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });
        refresh();

    }
    public void duyet(ActionEvent event) {

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
        confirmationLayout.add(new Label("Bạn có muốn tiếp tục duyệt phần thưởng này không ?"), 0, 0, 2, 1);
        confirmationLayout.add(yesButton, 0, 1);
        confirmationLayout.add(noButton, 1, 1);

        Scene confirmationScene = new Scene(confirmationLayout, 300, 100);
        Stage confirmationStage = new Stage();
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();




        //Tìm kiếm những hộ khẩu được tích checkbox
        ObservableList<PhatThuongTableModel> dataListRemove = FXCollections.observableArrayList();
        for (PhatThuongTableModel data: table.getItems()) {
            if(data.getDeleteBox().isSelected()) {
                dataListRemove.add(data);
            }
        }

        noButton.setOnAction(e1 -> {
            confirmationStage.close();
        });

        yesButton.setOnAction(e2 -> {
            //Cập nhật CSDL khi xóa hộ khẩu
            String delteQSql = " UPDATE phat_thuong\n" +
                    "                    SET\n" +
                    "                    daDuyet= 1\n" +
                    "                    WHERE idPhatThuong=?";
            Connection connection = DBConnection.getConnection();
            try {
                PreparedStatement preparedDeleteHKStmt = connection.prepareStatement(delteQSql);
                for(PhatThuongTableModel data: dataListRemove) {
                    int idPhatThuong  = data.getIdPhatThuong();
                    preparedDeleteHKStmt.setInt(1,idPhatThuong);
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
    public void huyDuyet(ActionEvent event) {

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
        confirmationLayout.add(new Label("Bạn có muốn tiếp tục hủy duyệt phần thưởng này không ?"), 0, 0, 2, 1);
        confirmationLayout.add(yesButton, 0, 1);
        confirmationLayout.add(noButton, 1, 1);

        Scene confirmationScene = new Scene(confirmationLayout, 300, 100);
        Stage confirmationStage = new Stage();
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();




        //Tìm kiếm những hộ khẩu được tích checkbox
        ObservableList<PhatThuongTableModel> dataListRemove = FXCollections.observableArrayList();
        for (PhatThuongTableModel data: table.getItems()) {
            if(data.getDeleteBox().isSelected()) {
                dataListRemove.add(data);
            }
        }

        noButton.setOnAction(e1 -> {
            confirmationStage.close();
        });

        yesButton.setOnAction(e2 -> {
            //Cập nhật CSDL khi xóa hộ khẩu
            String delteQSql = " UPDATE phat_thuong\n" +
                    "                    SET\n" +
                    "                    daDuyet= 0\n" +
                    "                    WHERE idPhatThuong=?";
            Connection connection = DBConnection.getConnection();
            try {
                PreparedStatement preparedDeleteHKStmt = connection.prepareStatement(delteQSql);
                for(PhatThuongTableModel data: dataListRemove) {
                    int idPhatThuong  = data.getIdPhatThuong();
                    preparedDeleteHKStmt.setInt(1,idPhatThuong);
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
    public void thongke1(ActionEvent event) throws IOException , SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = DBConnection.getConnection();
            statement = connection.createStatement();

            // Execute the query to get the data from both tables
            resultSet = statement.executeQuery("SELECT SUM(qua.giaTri)\n" +
                    "  FROM qua\n" +
                    "  INNER JOIN phat_thuong ON qua.idQua=phat_thuong.idQua\n" +
                    "  WHERE phat_thuong.daDuyet=1;");
            System.out.println(resultSet);

                int total = resultSet.getInt(1);
                note.setText("Tổng tiền: " + total);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        Utilities.popNewWindow(event, "/com/cnpm/scenes/thong_ke_phat_qua.fxml");
    }
    @FXML
    public void thongKePhatThuong(ActionEvent event) throws IOException {
        Utilities.popNewWindow(event, "/com/cnpm/scenes/thong_ke_thuong.fxml");
    }





}
