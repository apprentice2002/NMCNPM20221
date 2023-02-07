package com.cnpm.controllers;

import com.cnpm.utilities.DBConnection;
import com.cnpm.utilities.UserSession;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class DangNhapController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;
    @FXML
    private Label alert;

    @FXML
    public void dangNhap(ActionEvent event) {
        if (username.getText().isBlank() || password.getText().isBlank()) {
            alert.setText("Tên đăng nhập và mật khẩu không được để trống!!!");
        } else {
            Connection connection = DBConnection.getConnection();
            String verify = "SELECT * FROM users WHERE userName='" + username.getText() + "' AND passwd='" + password.getText() + "'";

            try {
                Statement statement = connection.createStatement();
                ResultSet queryResult = statement.executeQuery(verify);

                if (queryResult.next()) {
                    String user = username.getText();
                    Set<String> privileges = new HashSet<>();
                    switch (user) {
                        case "1" -> privileges.add("admin1");
                        case "2" -> privileges.add("admin2");
                        case "3" -> privileges.add("admin3");
                        default -> {
                        }
                    }
                    UserSession.setUsername(user);
                    UserSession.setPrivileges(privileges);
                    Utilities.changeScene(event, "/com/cnpm/scenes/trang-chu.fxml" , 720 , 640);
                } else {
                    alert.setText("Tên đăng nhập hoặc mật khẩu không chính xác!!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
