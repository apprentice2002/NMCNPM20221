package com.cnpm.controllers;

import com.cnpm.entities.MinhChung;
import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class XoaMinhChung implements Initializable {
    @FXML
    private Button btn_cancel_them;

    @FXML
    private Button btn_confirm_them;


    @FXML
    private TextField  note;
    @FXML
    private TextField dien_id_minh_chung;
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    MinhChung minhchung=null;
    PreparedStatement preparedStatement = null;
    @FXML
    public void huy(ActionEvent event) throws IOException {
        Stage stage = (Stage) btn_cancel_them.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void xacNhan(ActionEvent event) throws IOException {
        String idMinhChung = dien_id_minh_chung.getText();
        if (idMinhChung.equals("")) {
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
        query="DELETE from minh_chung"+
                "WHERE idMinhChung='"+minhchung.getIdMinhChung()+"'";
    }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}

