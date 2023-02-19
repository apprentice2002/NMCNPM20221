package com.cnpm.controllers;

import com.cnpm.entities.PhatThuong;
import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ThemThuongController implements Initializable {
    @FXML
    private Button btn_cancel_them;

    @FXML
    private Button btn_confirm_them;


    @FXML
    private TextField  note;
    @FXML
    private TextField them_id_phat_thuong;
    @FXML
    private TextField them_id_dot_phat;

    @FXML
    private TextField  them_id_qua;
    @FXML
    private TextField them_id_minh_chung;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PhatThuong pt=null;
    PreparedStatement preparedStatement = null;

    @FXML
    public void huy(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "/com/cnpm/scenes/home.fxml");
    }

    @FXML
    public void xacNhan(ActionEvent event) throws IOException {
        int   idPhatThuong = Integer.parseInt(them_id_phat_thuong.getText());
        int  idDotPhat = Integer.parseInt(them_id_dot_phat.getText());
        int  idMinhChung=Integer.parseInt(them_id_minh_chung.getText());
        int  idQua = Integer.parseInt(them_id_qua.getText());
        int  daDuyet = Integer.parseInt(them_id_qua.getText());
        if (true){
            note.setText("Vui lòng điền đủ thông tin cần thiết");
    } else {
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idPhatThuong);
                preparedStatement.setInt(2,  idDotPhat);
                preparedStatement.setInt(3, idQua);
                preparedStatement.setInt(4, idMinhChung);
                preparedStatement.setInt(5, daDuyet);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Utilities.changeScene(event, "/com/cnpm/scenes/home.fxml");
        }
    }
    private void getQuery() {
        query = "INSERT INTO phat_thuong (idPhatThuong, idDotPhat, idQua, idMinhChung, daDuyet,) " +
                " VALUES (?,?,?,?,?)";
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
