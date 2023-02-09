package com.cnpm.controllers;

import com.cnpm.utilities.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DoiChuHoController {

    @FXML
    private Button cancleBtn;
    @FXML
    private TextField maHoKhauTxt;
    @FXML
    private TextField maKhuVucTxt;
    @FXML
    private TextField diaChiTxt;
    @FXML
    private TextField maChuHoOldTxt;
    @FXML
    private TextField hoTenOldTxt;
    @FXML
    private TextField ngaySinhOldTxt;
    @FXML
    private TextField cccdOldTxt;
    @FXML
    private TextField maChuHoNewTxt;
    @FXML
    private TextField hoTenChuHoMoiTxt;
    @FXML
    private TextField ngaySinhChuHoMoiTxt;
    @FXML
    private TextField cccdChuHoMoiTxt;

    public void chonChuHo(ActionEvent event) {
        Utilities.popNewWindow(event,"/com/cnpm/views/ho-khau.fxml");
    }
    public void chonHoKhau(ActionEvent event) {

    }
    public void submit(ActionEvent event) {

    }

    public void cancle(ActionEvent event) {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }


}
