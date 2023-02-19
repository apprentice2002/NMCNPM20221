package com.cnpm.controllers.phat_qua;

import com.cnpm.entities.PhatQua;
import com.cnpm.utilities.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Label errorLabel;
    @FXML
    private TextField  them_id_dot_phat_qua;

    @FXML
    private TextField   them_ma_nhan_khau;
    @FXML
    private TextField  them_id_qua;


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


        if (them_id_dot_phat_qua.getText().equals("") || them_ma_nhan_khau.getText().equals("")
                || them_id_qua.getText().equals("") ){
            errorLabel.setText("Vui lòng điền đủ thông tin cần thiết");
        } else {
            errorLabel.setText("");
            int idDotPhat = Integer.parseInt(them_id_dot_phat_qua.getText());
            int idNhanKhau=Integer.parseInt(them_ma_nhan_khau.getText());
            int idQua = Integer.parseInt(them_id_qua.getText());
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,  idDotPhat);
                preparedStatement.setInt(2, idNhanKhau);
                preparedStatement.setInt(3, idQua);
                preparedStatement.setInt(4, 0);
                preparedStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) btn_confirm_them.getScene().getWindow();
            stage.close();
        }
    }
    private void getQuery() {
        query = "INSERT INTO phat_qua (idDotPhat, idNhanKhau, idQua, daDuyet)" +
                " VALUES (?,?,?,?)";
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
    }

}

