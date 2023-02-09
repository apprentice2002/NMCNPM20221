package com.cnpm.controllers;

import com.cnpm.utilities.NhanKhauTableModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChonChuHoController implements Initializable {
    @FXML
    private Button cancleBtn;
    @FXML
    private Button submitBtn;

    @FXML
    private TableView<NhanKhauTableModel> chuHoTable;


    private NhanKhauTableModel selectedItem;

    public void setSelectedItem(NhanKhauTableModel selectedItem) {
        this.selectedItem = selectedItem;
    }
    public NhanKhauTableModel getSelectedItem() {
        return selectedItem;
    }

    public void submit(ActionEvent event) {
    }

    public void cancle(ActionEvent event) {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        chuHoTable.setOnMouseClicked(event -> {
//            NhanKhauTableModel selectedItem = chuHoTable.getSelectionModel().getSelectedItem();
//            controller.setSelectedItem(selectedItem);
//        });
    }
}
