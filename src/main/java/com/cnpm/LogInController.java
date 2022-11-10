package com.cnpm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class LogInController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private Button btn_login;

    @FXML
    private PasswordField password;

    @FXML
    private Label logInMessage;

    @FXML
    public void login(ActionEvent event) {
        if (username.getText().isBlank() || password.getText().isBlank()) {
            logInMessage.setText("Please enter admin name and password");
        } else {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
            String verify = "SELECT * FROM administrators WHERE admin_name='" + username.getText() + "' AND password='" + password.getText() + "'";

            try {
                Statement statement = connection.createStatement();
                ResultSet queryResult = statement.executeQuery(verify);

                if (queryResult.next()) {
                    String user = username.getText();
                    Set<String> privileges = new HashSet<String>();
                    switch (user) {
                        case "admin1" -> privileges.add("admin1");
                        case "admin2" -> privileges.add("admin2");
                        case "admin3" -> privileges.add("admin3");
                        default -> {
                        }
                    }
                    UserSession.setUsername(user);
                    UserSession.setPrivileges(privileges);
                    Utilities.changeScene(event, "home.fxml", "Quan Ly");
                } else {
                    logInMessage.setText("fail");
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
