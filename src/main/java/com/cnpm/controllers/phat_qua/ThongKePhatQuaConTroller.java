package com.cnpm.controllers.phat_qua;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.ThongKePhatQuaTableModel;
import com.cnpm.utilities.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ThongKePhatQuaConTroller implements Initializable {
    @FXML
    private Button thong_ke_thuong;
    @FXML
    private ChoiceBox<String> dotPhatChoiceBox;
    @FXML
    private Label note;
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
    private TableView<ThongKePhatQuaTableModel> table;
    @FXML
    private TableColumn<ThongKePhatQuaTableModel, String> idHoKhauCol;
    @FXML
    private TableColumn<ThongKePhatQuaTableModel, String> hoTenCol;
    @FXML
    private TableColumn<ThongKePhatQuaTableModel, String> soPhanQuaCol;
    @FXML
    private TableColumn<ThongKePhatQuaTableModel, String> tongGiaTri;
    ThongKePhatQuaTableModel data = null;
    ObservableList<ThongKePhatQuaTableModel> listView = FXCollections.observableArrayList();


    private List<ThongKePhatQuaTableModel> performFiltering(String option, String searchText) {
        List<ThongKePhatQuaTableModel> filteredData = new ArrayList<>();
        for (ThongKePhatQuaTableModel data : table.getItems()) {
            if (data.getHoTen().equals(option) && data.getHoTen().contains(searchText)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }
    public void refresh()throws  SQLException {
        ObservableList<String> dotPhatList = FXCollections.observableArrayList();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT tenDotPhat FROM dot_phat")) {

            while (resultSet.next()) {
                dotPhatList.add(resultSet.getString("tenDotPhat"));
            }

            // Thêm danh sách đợt phát vào choicebox
            dotPhatChoiceBox.setItems(dotPhatList);

        } catch (SQLException e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
        }

// Sử dụng choicebox để thực hiện truy vấn cơ sở dữ liệu
        dotPhatChoiceBox.setOnAction(event -> {
            table.getItems().clear();
            String tenDotPhat = dotPhatChoiceBox.getValue();
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT DISTINCT hk.ID AS idHoKhau, nk2.hoTen AS hoTen, COUNT(nk1.ID) as soPhanQua, SUM(qua.giaTri) AS tongGiaTri\n" +
                    "FROM nhan_khau AS nk1, nhan_khau AS nk2 , ho_khau AS hk, dot_phat AS dp, phat_qua AS pq, thanh_vien_cua_ho tvh, qua\n" +
                    "WHERE dp.idDotPhat =  pq.idDotPhat\n" +
                    "AND qua.idQua = pq.idQua \n" +
                    "AND pq.idNhanKhau = nk1.ID\n" +
                    "AND nk1.ID = tvh.idNhanKhau\n" +
                    "AND tvh.idHoKhau = hk.ID\n" +
                    "AND hk.idChuHo = nk2.ID\n" +
                    "AND dp.tenDotPhat = '" +tenDotPhat+ "'\n" +
                    "AND pq.daDuyet = 1\n" +
                    "GROUP BY hk.ID;";
            try {
                //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
                Statement statement = connection.createStatement();
                ResultSet queryResult = statement.executeQuery(sql);
                // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
                while (queryResult.next()) {
                    listView.add(new ThongKePhatQuaTableModel(queryResult.getInt("idHoKhau"),
                            queryResult.getString("hoTen"),
                            queryResult.getInt("soPhanQua"),
                            queryResult.getInt("tongGiaTri")));

                }
                table.setItems(listView);

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();

                // Execute the query to get the data from both tables
                ResultSet resultSet = statement.executeQuery("SELECT SUM(qua.giaTri) AS tong\n" +
                        "  FROM qua\n" +
                        "  INNER JOIN phat_qua ON qua.idQua=phat_qua.idQua\n" +
                        "  INNER JOIN dot_phat ON phat_qua.idDotPhat=dot_phat.idDotPhat\n" +
                        "  WHERE "+
                        " dot_phat.tenDotPhat = '" + tenDotPhat + "'\n" +
                        " AND phat_qua.daDuyet=1;");
                if (resultSet.next()) {
                    int total = resultSet.getInt("tong");
                    note.setText("Tổng tiền: " + total + " VND");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("idHoKhau"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        soPhanQuaCol.setCellValueFactory(new PropertyValueFactory<>("soPhanQua"));
        tongGiaTri.setCellValueFactory(new PropertyValueFactory<>("tongGiaTri"));

        optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm id Hộ Khẩu");
        optionChoiceBox.setValue("Lựa chọn tìm kiếm");
        dotPhatChoiceBox.setValue("Chọn đợt phát quà ");

        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<ThongKePhatQuaTableModel> filteredData = new FilteredList<>(listView, b -> true);
            keywordTextField.textProperty().addListener(((observable1, oldValue1, newValue1) -> {
                filteredData.setPredicate(data -> {
                    if (newValue1.isEmpty() || newValue1.isBlank() || newValue1 == null) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if (data.getHoTen().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên") {
                        return true;
                    } else if (Integer.toString(data.getIdHoKhau()).toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm id Hộ Khẩu") {
                        return true;
                    } else
                        return false;
                });
            }));
            // Sắp xếp thứ tự
            SortedList<ThongKePhatQuaTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });

        try {
            refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

//    public void tongTien() throws SQLException {
//        try {
//            Connection connection = DBConnection.getConnection();
//            Statement statement = connection.createStatement();
//
//            // Execute the query to get the data from both tables
//            ResultSet resultSet = statement.executeQuery("SELECT SUM(qua.giaTri)\n" +
//                    "  FROM qua\n" +
//                    "  INNER JOIN phat_qua ON qua.idQua=phat_qua.idQua\n" +
//                    "  INNER JOIN dot_phat ON phat_qua.idDotPhat=dot_phat.idDotPhat\n" +
//                    "  WHERE "+
//                    " dot_phat.tenDotPhat = '" + tenDotPhat + "'\n" +
//                    " AND phat_qua.daDuyet=1;");
//            if (resultSet.next()) {
//                int total = resultSet.getInt("tong");
//                note1.setText("Tổng tiền: " + total);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
}