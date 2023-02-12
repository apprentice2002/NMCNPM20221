package com.cnpm.controllers;

import com.cnpm.entities.PhatQua;
import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class ThemQuaController implements Initializable {
    @FXML
    private Button btn_cancel_them;

    @FXML
    private Button btn_confirm_them;


    @FXML
    private TextField note;
    @FXML
    private TextField  them_id_phat_qua;
    @FXML
    private TextField  them_id_dot_phat_qua;

    @FXML
    private TextField   them_ma_nhan_khau;
    @FXML
    private TextField  them_id_qua;
    @FXML
    private TextField  them_da_duyet;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PhatQua phatqua=null;
    PreparedStatement preparedStatement = null;

    @FXML
    public void huy(ActionEvent event) throws IOException {
        Stage stage = (Stage) btn_cancel_them.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void xacNhan(ActionEvent event) throws IOException {
         int   idPhatQua = Integer.parseInt(them_id_phat_qua.getText());
        int  idDotPhat = Integer.parseInt(them_id_dot_phat_qua.getText());
        int  ma_nhan_khau=Integer.parseInt(them_ma_nhan_khau.getText());
        int  idQua = Integer.parseInt(them_id_qua.getText());
        int  daDuyet = Integer.parseInt(them_da_duyet.getText());
        if (false ){
            note.setText("Vui lòng điền đủ thông tin cần thiết");
    } else {
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idPhatQua);
                preparedStatement.setInt(2,  idDotPhat);
                preparedStatement.setInt(3, ma_nhan_khau);
                preparedStatement.setInt(4, idQua);
                preparedStatement.setInt(5, daDuyet);




                preparedStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) btn_confirm_them.getScene().getWindow();
            stage.close();
        }
    }
    private void getQuery() {
        query = "INSERT INTO phat_qua (idPhatQua, idDotPhat, ma_nhan_khau, idQua, daDuyet)" +
                " VALUES (?,?,?,?,?)";
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}

