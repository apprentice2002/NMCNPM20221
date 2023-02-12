package com.cnpm.controllers;

import com.cnpm.utilities.UserSession;
import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TrangChuController implements Initializable {

    @FXML private StackPane main_display;

    @FXML
    public void hoKhau(ActionEvent event) {
        changeDisplay(main_display, "/com/cnpm/views/ho-khau.fxml");
    }

    @FXML
    public void nhanKhau(ActionEvent event) {
        changeDisplay(main_display, "/com/cnpm/views/nhan-khau.fxml");
    }

    @FXML
    public void trangChu(ActionEvent event) {
        changeDisplay(main_display, "/com/cnpm/views/mac-dinh.fxml");
    }

    @FXML
    public void phatThuong(ActionEvent event) {
        changeDisplay(main_display, "/com/cnpm/views/phat-thuong.fxml");
    }
    @FXML
    public void phatQua(ActionEvent event) {
        changeDisplay(main_display, "/com/cnpm/views/phat-qua.fxml");
    }
    @FXML
    public void minhChung(ActionEvent event) {
        changeDisplay(main_display, "/com/cnpm/views/minh_chung.fxml");
    }

    @FXML
    public void thayDoi(ActionEvent event) {
        changeDisplay(main_display, "/com/cnpm/views/thong-ke.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeDisplay(main_display, "/com/cnpm/views/mac-dinh.fxml");
        String user_string = UserSession.getUsername();
//        user.setText(user_string);
    }

    public void changeDisplay(StackPane pane, String fxmlFile) {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(fxml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
