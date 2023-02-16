package com.cnpm.controllers.PhatThuong;

import com.cnpm.entities.PhatThuong;
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
    @FXML
    private TextField them_da_duyet;



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

    @FXML
    public void xacNhan(ActionEvent event) throws IOException {
//        int   idPhatThuong = Integer.parseInt(them_id_phat_thuong.getText());
        int  idDotPhat = Integer.parseInt(them_id_dot_phat.getText());
        int  idMinhChung=Integer.parseInt(them_id_minh_chung.getText());
        int  idQua = Integer.parseInt(them_id_qua.getText());
        int  daDuyet = Integer.parseInt(them_id_qua.getText());
        if (false){
            note.setText("Vui lòng điền đủ thông tin cần thiết");
    } else {
            getQuery();
            try {
                connection = DBConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
//                preparedStatement.setInt(1, idPhatThuong);
                preparedStatement.setInt(1,  idDotPhat);
                preparedStatement.setInt(2, idQua);
                preparedStatement.setInt(3, idMinhChung);
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
        query = "INSERT INTO phat_thuong ( idDotPhat, idQua, idMinhChung, daDuyet) " +
                " VALUES (?,?,?,?)";
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}

