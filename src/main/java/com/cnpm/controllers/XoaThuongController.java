package com.cnpm.controllers;

import com.cnpm.entities.PhatThuong;
import com.cnpm.utilities.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class XoaThuongController implements Initializable {
    @FXML
    private Button btn_cancel_them;

    @FXML
    private Button btn_confirm_them;

    @FXML
    private TextField note;
    @FXML
    private TextField them_id_phat_thuong;
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PhatThuong pt=null;
    PreparedStatement preparedStatement = null;
    @FXML
    public void huy(ActionEvent event) throws IOException {
        Stage stage = (Stage) btn_cancel_them.getScene().getWindow();
        stage.close();
    }
    public void xacNhan(ActionEvent event) throws IOException {
        String idPhatThuong = them_id_phat_thuong.getText();
        if (idPhatThuong.equals("") ){
            note.setText("Vui lòng điền đủ thông tin cần thiết");
    } else {
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage)btn_confirm_them .getScene().getWindow();
            stage.close();
        }
    }
    private void getQuery() {
        query="DELETE from phat_thuong"+
                "WHERE idMinhChung='"+pt.getIdPhatThuong()+"'";

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
