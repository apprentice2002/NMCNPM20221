package com.cnpm.controllers;

import com.mysql.cj.protocol.Resultset;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class ThongKeNhanKhauController implements Initializable {
    Resultset resultset = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
