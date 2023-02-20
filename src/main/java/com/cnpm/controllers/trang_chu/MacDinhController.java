package com.cnpm.controllers.trang_chu;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.UserSession;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MacDinhController implements Initializable {
    Connection connection = null;
    ResultSet resultSet1 = null, resultSet2 = null, resultSet3 = null, resultSet4 = null;
    String query1 = "", query2 = "", query3 = "", query4 = "";

    @FXML private Label hoKhauLab;
    @FXML private Label nhanKhauLab;
    @FXML private Label nhanKhauTamTruLab;
    @FXML private Label nhanKhauTamVangLab;

    @FXML
    void logout(ActionEvent event) {
        Utilities.changeScene(event, "/com/cnpm/chuc-nang-view/trang-chu-chuc-nang-view/dang-nhap.fxml");
        UserSession.setUsername(null);
        UserSession.setPrivileges(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getQuery();
        connection = DBConnection.getConnection();
        try {
            resultSet1 = connection.createStatement().executeQuery(query1);
            resultSet2 = connection.createStatement().executeQuery(query2);
            resultSet3 = connection.createStatement().executeQuery(query3);
            resultSet4 = connection.createStatement().executeQuery(query4);

            while (resultSet1.next()) {
                int value = resultSet1.getInt("total");
                nhanKhauTamTruLab.setText(Integer.toString(value));
            }
            while (resultSet2.next()) {
                int value = resultSet2.getInt("total");
                nhanKhauTamVangLab.setText(Integer.toString(value));
            }
            while (resultSet3.next()) {
                int value = resultSet3.getInt("total");
                nhanKhauLab.setText(Integer.toString(value));
            }
            while (resultSet4.next()) {
                int value = resultSet4.getInt("total");
                hoKhauLab.setText(Integer.toString(value));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getQuery() {
        query1 = "SELECT COUNT(ID) AS total FROM tam_tru";
        query2 = "SELECT COUNT(ID) AS total FROM tam_vang";
        query3 = "SELECT COUNT(ID) AS total FROM nhan_khau";
        query4 = "SELECT COUNT(ID) AS total FROM ho_khau";
    }
}
