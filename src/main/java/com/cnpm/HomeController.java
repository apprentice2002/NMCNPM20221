package com.cnpm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button btn_logout;

    @FXML
    private Button ho_khau;

    @FXML
    private StackPane main_display;

    @FXML
    private Button nhan_khau;

    @FXML
    private Button trang_chu;

    @FXML
    private Label user;

    @FXML
    public void logout(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "log-in.fxml", "Dang nhap");
        UserSession.setUsername(null);
        UserSession.setPrivileges(null);
    }

    @FXML
    public void quanLyHoKhau(ActionEvent event) {
        changeDisplay(main_display, "ho-khau.fxml");
    }

    @FXML
    public void quanLyNhanKhau(ActionEvent event) {
        changeDisplay(main_display, "nhan-khau.fxml");
    }

    @FXML
    public void trangChu(ActionEvent event) {
        changeDisplay(main_display, "base-display.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeDisplay(main_display, "base-display.fxml");
        String user_string = UserSession.getUsername();
        user.setText(user_string);
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
