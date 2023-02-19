package com.cnpm.controllers.phat_thuong;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.ThongKePhatQuaTableModel;
import com.cnpm.utilities.ThongKePhatThuongTableModel;
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

public class ThongKePhatThuongController implements Initializable {
    @FXML
    private Label note;
    @FXML
    private TextField thong_tin_tim_kiem;
    @FXML
    private ChoiceBox loc;
    @FXML
    private ChoiceBox<String> optionChoiceBox;
    @FXML
    private ChoiceBox<String> dotPhatChoiceBox;

    @FXML
    private Button findBtn;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TableView<ThongKePhatThuongTableModel> table;
    @FXML
    private TableColumn<ThongKePhatThuongTableModel, String> idHoKhauCol;
    @FXML
    private TableColumn<ThongKePhatThuongTableModel, String> hoTenCol;
    @FXML
    private TableColumn<ThongKePhatThuongTableModel, String> soPhanThuongCol;
    @FXML
    private TableColumn<ThongKePhatThuongTableModel, String> tongGiaTri;
    ThongKePhatThuongTableModel data = null;
    ObservableList<ThongKePhatThuongTableModel> listView = FXCollections.observableArrayList();


    private List<ThongKePhatThuongTableModel> performFiltering(String option, String searchText) {
        List<ThongKePhatThuongTableModel> filteredData = new ArrayList<>();
        for (ThongKePhatThuongTableModel data : table.getItems()) {
            if (data.getHoTen1().equals(option) && data.getHoTen1().contains(searchText)) {
                filteredData.add(data);
            }
        }
        return filteredData;
    }
    public void refresh()throws  SQLException {
        //ChoiceBox<Integer> dotPhatChoiceBox = new ChoiceBox<>();

// Lấy danh sách các đợt phát từ cơ sở dữ liệu và thêm vào choicebox
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
            String sql ="SELECT DISTINCT hk.ID AS idHoKhau1, nk2.hoTen AS hoTen1, COUNT(nk1.ID) as soPhanThuong1, SUM(qua.giaTri) AS tongGiaTri1 \n" +
                    "FROM nhan_khau AS nk1, nhan_khau AS nk2 , ho_khau AS hk, dot_phat AS dp, phat_thuong AS pt, thanh_vien_cua_ho tvh, qua, minh_chung AS mc\n" +
                    "WHERE dp.idDotPhat =  pt.idDotPhat\n" +
                    "AND qua.idQua = pt.idQua \n" +
                    "AND pt.idMinhChung = mc.idMinhChung\n" +
                    "AND mc.ma_nhan_khau = nk1.ID\n" +
                    "AND nk1.ID = tvh.idNhanKhau\n" +
                    "AND tvh.idHoKhau = hk.ID\n" +
                    "AND hk.idChuHo = nk2.ID\n" +
                    "AND dp.tenDotPhat = '" +tenDotPhat+ "'\n" +
                    "AND pt.daDuyet = 1\n" +
                    "GROUP BY hk.ID;";
            try {
                //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
                Statement statement = connection.createStatement();
                ResultSet queryResult = statement.executeQuery(sql);
                // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
                while (queryResult.next()) {
                    listView.add(new ThongKePhatThuongTableModel(queryResult.getInt("idHoKhau1"),
                            queryResult.getString("hoTen1"),
                            queryResult.getInt("soPhanThuong1"),
                            queryResult.getInt("tongGiaTri1")));

                }
                table.setItems(listView);


            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();

                // Execute the query to get the data from both tables
                ResultSet resultSet = statement.executeQuery("SELECT SUM(qua.giaTri)\n" +
                        "  FROM qua\n" +
                        "  INNER JOIN phat_thuong ON qua.idQua=phat_thuong.idQua\n" +
                        "  INNER JOIN dot_phat ON phat_thuong.idDotPhat=dot_phat.idDotPhat\n" +
                        "  WHERE "+
                        " dot_phat.tenDotPhat = '" + tenDotPhat + "'\n" +
                        " AND phat_thuong.daDuyet=1;"
                );

                if (resultSet.next()) {
                    int total = resultSet.getInt(1);
                    note.setText("Tổng tiền: " + total +  " VND");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("idHoKhau1"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen1"));
        soPhanThuongCol.setCellValueFactory(new PropertyValueFactory<>("soPhanThuong1"));
        tongGiaTri.setCellValueFactory(new PropertyValueFactory<>("tongGiaTri1"));
        dotPhatChoiceBox.setValue("Chọn đợt phát thưởng");
        optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm id Hộ Khẩu");
        optionChoiceBox.setValue("Lựa chọn tìm kiếm");

        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<ThongKePhatThuongTableModel> filteredData = new FilteredList<>(listView, b -> true);
            keywordTextField.textProperty().addListener(((observable1, oldValue1, newValue1) -> {
                filteredData.setPredicate(data -> {
                    if (newValue1.isEmpty() || newValue1.isBlank() || newValue1 == null) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if (data.getHoTen1().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên") {
                        return true;
                    } else if (Integer.toString(data.getIdHoKhau1()).toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm id Hộ Khẩu") {
                        return true;
                    } else
                        return false;
                });
            }));
            // Sắp xếp thứ tự
            SortedList<ThongKePhatThuongTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });


        try {
            refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
//    public void thongKeTongTien() throws  SQLException {
//        ObservableList<String> dotPhatList = FXCollections.observableArrayList();
//        try (Connection connection = DBConnection.getConnection();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT tenDotPhat FROM dot_phat")) {
//
//            while (resultSet.next()) {
//                dotPhatList.add(resultSet.getString("tenDotPhat"));
//            }
//
//            // Thêm danh sách đợt phát vào choicebox
//            dotPhatChoiceBox.setItems(dotPhatList);
//
//        } catch (SQLException e) {
//            // Xử lý ngoại lệ
//            e.printStackTrace();
//        }
//
//// Sử dụng choicebox để thực hiện truy vấn cơ sở dữ liệu
//        dotPhatChoiceBox.setOnAction(event -> {
//            String tenDotPhat = dotPhatChoiceBox.getValue();
//
//            try {
//                Connection connection = DBConnection.getConnection();
//                Statement statement = connection.createStatement();
//
//                // Execute the query to get the data from both tables
//                ResultSet resultSet = statement.executeQuery("SELECT SUM(qua.giaTri)\n" +
//                        "  FROM qua\n" +
//                        "  INNER JOIN phat_thuong ON qua.idQua=phat_thuong.idQua\n" +
//                        "  INNER JOIN dot_phat ON phat_thuong.idDotPhat=dot_phat.idDotPhat\n" +
//                        "  WHERE "+
//                        " dot_phat.tenDotPhat = '" + tenDotPhat + "'\n" +
//                        " AND phat_thuong.daDuyet=1;"
//                );
//
//                if (resultSet.next()) {
//                    int total = resultSet.getInt(1);
//                    note.setText("Tổng tiền: " + total);
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });
//
//    }
}
