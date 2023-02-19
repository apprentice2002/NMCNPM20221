package com.cnpm.controllers.nhan_khau;

import com.cnpm.entities.TamTru;
import com.cnpm.entities.TamVang;
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

public class DanhSachTamVangController implements Initializable {

    @FXML private TableColumn<TamVang, String> denNgayCol;
    @FXML private TableColumn<TamVang, Integer> idCol;
    @FXML private TableColumn<TamVang, Integer> idNhanKhauCol;
    @FXML private TableColumn<TamVang, String> lyDoCol;
    @FXML private TableColumn<TamVang, String> noiTamTruCol;
    @FXML private TableView<TamVang> table;
    @FXML private TableColumn<TamVang, String> tuNgayCol;

    ObservableList<TamVang> TamVangList = FXCollections.observableArrayList();
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
        TamVangList.clear();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TamVangList.add(new TamVang(
                        resultSet.getInt("ID"),
                        resultSet.getInt("idNhanKhau"),
                        resultSet.getString("noiTamTru"),
                        formatter.format(resultSet.getDate("tuNgay")),
                        formatter.format(resultSet.getDate("denNgay")),
                        resultSet.getString("lyDo")
                ));
            }
            table.setItems(TamVangList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        query = "SELECT ID, idNhanKhau, noiTamTru, tuNgay, denNgay, lyDo FROM tam_vang";
        connection = DBConnection.getConnection();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("idNhanKhau"));
        tuNgayCol.setCellValueFactory(new PropertyValueFactory<>("tuNgay"));
        denNgayCol.setCellValueFactory(new PropertyValueFactory<>("denNgay"));
        lyDoCol.setCellValueFactory(new PropertyValueFactory<>("lyDo"));
        noiTamTruCol.setCellValueFactory(new PropertyValueFactory<>("noiTamTru"));

        refresh();
    }

}
