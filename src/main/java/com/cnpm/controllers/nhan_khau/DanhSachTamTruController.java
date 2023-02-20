package com.cnpm.controllers.nhan_khau;

import com.cnpm.entities.NhanKhau;
import com.cnpm.entities.TamTru;
import com.cnpm.utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class DanhSachTamTruController implements Initializable {

    @FXML private TableColumn<TamTru, String> denNgayCol;
    @FXML private TableColumn<TamTru, Integer> idCol;
    @FXML private TableColumn<TamTru, Integer> idNhanKhauCol;
    @FXML private TableColumn<TamTru, String> lyDoCol;
    @FXML private TableView<TamTru> table;
    @FXML private TableColumn<TamTru, String> tuNgayCol;

    ObservableList<TamTru> TamTruList = FXCollections.observableArrayList();
    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;
    String query = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();
    }

    private void refresh() {
        TamTruList.clear();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TamTruList.add(new TamTru(
                        resultSet.getInt("ID"),
                        resultSet.getInt("idNhanKhau"),
                        formatter.format(resultSet.getDate("tuNgay")),
                        formatter.format(resultSet.getDate("denNgay")),
                        resultSet.getString("lyDo")
                ));
            }
            table.setItems(TamTruList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        query = "SELECT ID, idNhanKhau, tuNgay, denNgay, lyDo FROM tam_tru";
        connection = DBConnection.getConnection();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("idNhanKhau"));
        tuNgayCol.setCellValueFactory(new PropertyValueFactory<>("tuNgay"));
        denNgayCol.setCellValueFactory(new PropertyValueFactory<>("denNgay"));
        lyDoCol.setCellValueFactory(new PropertyValueFactory<>("lyDo"));

        refresh();
    }
}
