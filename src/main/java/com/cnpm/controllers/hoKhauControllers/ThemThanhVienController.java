package com.cnpm.controllers.hoKhauControllers;

import com.cnpm.controllers.ChonNhanKhauController;
import com.cnpm.utilities.DBConnection;
import com.cnpm.entities.HoKhauTableModel;
import com.cnpm.entities.NhanKhauTableModel;
import com.cnpm.entities.SharedDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.cnpm.utilities.DBConnection.connection;

public class ThemThanhVienController implements Initializable {
    @FXML
    public TextField maHoKhauTim;
    @FXML
    private TableColumn<HoKhauTableModel, String> maHoKhauCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> hoTenChuHoCol;
    @FXML
    private TableColumn<HoKhauTableModel, String> diaChiCol;
    @FXML
    private TableView<HoKhauTableModel> hoKhauTable;
    ObservableList<HoKhauTableModel> hoKhauView = FXCollections.observableArrayList();
    @FXML
    private Button cancleBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private Button pickMemberBtn;
    @FXML
    private Label errorLab;
    @FXML
    private TextField maHoKhauTxt;
    @FXML
    private TableView<NhanKhauTableModel> nhanKhauTable;
    @FXML
    private  TableColumn<NhanKhauTableModel, String> maNhanKhauCol;
    @FXML
    private  TableColumn<NhanKhauTableModel, Date> hoTenCol;
    @FXML
    private  TableColumn<NhanKhauTableModel, String> quanHeVoiChuHoCol;
    @FXML
    private ChoiceBox<String> optionChoiceBox;
    private SharedDataModel sharedDataModel = new SharedDataModel();

    private ChoiceBox<String> choiceBox;
    @FXML
    private Button deleteNhanKhauBtn;
    @FXML
    private Button deleteAllNhanKhauBtn;
    private boolean containsNhanKhau(ObservableList<NhanKhauTableModel> list, String name){
        return list.stream().map(NhanKhauTableModel::getHoTenNhanKhau).filter(name::equals).findFirst().isPresent();
    }
    private void removeNhanKhau(NhanKhauTableModel selectedRow) {
        sharedDataModel.getSelectedRows().removeIf(obj->obj.getHoTenNhanKhau().equals(selectedRow.getHoTenNhanKhau()));
    }

    private void refreshTable() {
        hoKhauTable.getItems().clear();
        String hoKhauSql = "SELECT ho_khau.ID as MaHoKhau, nhan_khau.hoTen AS TenChuHo, diaChi as DiaChiHoKhau " +
                "FROM nhan_khau, ho_khau " +
                "WHERE nhan_khau.ID = IdChuHo AND ho_khau.daXoa is NULL and " +
                "ho_khau.ngayChuyenDi is null and nhan_khau.daXoa is null";
        Connection connection = DBConnection.getConnection();
        try {
            connection = DBConnection.getConnection();
            //Thực hiện các câu lệnh kết nối DB và truy vấn SQL
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(hoKhauSql);
            // Thêm các dữ liệu từ DB vào khung nhìn và thiết lập dữ liệu vào bảng
            while (queryResult.next()) {
                hoKhauView.add(new HoKhauTableModel(
                        queryResult.getString("MaHoKhau"),
                        queryResult.getString("TenChuHo"),
                        queryResult.getString("DiaChiHoKhau")));
            }
            hoKhauTable.setItems(hoKhauView);
            nhanKhauTable.getItems().clear();
        }catch (Exception e) {
            System.out.println(e);
        }
    }



    public void cancle(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }

    public void xacNhan(ActionEvent event) {
        boolean canUpdate = true;
        for (NhanKhauTableModel nk: nhanKhauTable.getItems()) {
            if(nk.getQuanHeVoiChuHo().isEmpty()) canUpdate = false;
        }
        if(maHoKhauTxt.getText().isEmpty() || nhanKhauTable.getItems().isEmpty()) {
            errorLab.setText("Vui lòng nhập đủ dữ liêu");
        } else if (canUpdate) {
            errorLab.setText("Nhập dữ liệu nhân khẩu sai, hãy kiểm tra lại !");
        } else {
            String maNhanKhau;
            String hoTen;
            String quanHeVoiChuHo;
            String idHoKhau = maHoKhauTxt.getText();
            String insertSQL = "INSERT INTO `thanh_vien_cua_ho` (`idNhanKhau`, `idHoKhau`, `quanHeVoiChuHo`) VALUES (?, ?, ?);";
            for (NhanKhauTableModel nk : nhanKhauTable.getItems() ) {
                maNhanKhau = nk.getMaNhanKhau();
                hoTen = nk.getHoTenNhanKhau();
                quanHeVoiChuHo = nk.getQuanHeVoiChuHo();
                try {
                    PreparedStatement preparedStmtFindName = connection.prepareStatement(insertSQL);
                    preparedStmtFindName.setString(1,maNhanKhau);
                    preparedStmtFindName.setString(2,idHoKhau);
                    preparedStmtFindName.setString(3,quanHeVoiChuHo);
                    preparedStmtFindName.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            refreshTable();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
        maHoKhauCol.setCellValueFactory(new PropertyValueFactory<>("maHoKhau"));
        hoTenChuHoCol.setCellValueFactory(new PropertyValueFactory<>("hoTenChuHo"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChiHoKhau"));

        maNhanKhauCol.setCellValueFactory(new PropertyValueFactory<>("maNhanKhau"));
        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTenNhanKhau"));
        quanHeVoiChuHoCol.setCellValueFactory(new PropertyValueFactory<>("quanHeVoiChuHo"));

        errorLab.setText("");

        quanHeVoiChuHoCol.setCellFactory(column -> {
            return new TableCell<NhanKhauTableModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        choiceBox = new ChoiceBox<>();
                        choiceBox.getItems().addAll("Con","Bố","Mẹ", "Vợ", "Chồng", "Anh","Chị","Em");
                        choiceBox.getSelectionModel().select(item);
                        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                            NhanKhauTableModel nhanKhau = getTableView().getItems().get(getIndex());
                            nhanKhau.setQuanHeVoiChuHo(newValue);
                        });
                        setGraphic(choiceBox);
                    }
                }
            };
        });
        optionChoiceBox.getItems().addAll("Tìm theo họ tên", "Tìm theo ID");
        optionChoiceBox.setValue("Lựa chọn tìm kiếm");

        // Thêm action cho choicebox
        optionChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Lọc theo TextField
            FilteredList<HoKhauTableModel> filteredData = new FilteredList<>(hoKhauView, b->true);
            maHoKhauTim.textProperty().addListener(((observable1, oldValue1, newValue1)->{
                filteredData.setPredicate(hoKhau->{
                    if(newValue1.isEmpty() || newValue1.isBlank() || newValue1 == null) {
                        return true;
                    }
                    String searchKeyword = newValue1.toLowerCase();
                    if(hoKhau.getMaHoKhau().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo ID" ) {
                        return true;
                    } else if(hoKhau.getHoTenChuHo().toLowerCase().indexOf(searchKeyword) > -1 && optionChoiceBox.getSelectionModel().getSelectedItem() == "Tìm theo họ tên") {
                        return true;
                    } else
                        return false;
                });
            } ));
            // Sắp xếp thứ tự
            SortedList<HoKhauTableModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(hoKhauTable.comparatorProperty());
            hoKhauTable.setItems(sortedData);
        });

        hoKhauTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    nhanKhauTable.getItems().clear();
                    HoKhauTableModel selectedRow = hoKhauTable.getSelectionModel().getSelectedItem();
                    maHoKhauTxt.setText(selectedRow.getMaHoKhau());
                }
            }
        });

        pickMemberBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cnpm/scenes/chon-nhan-khau.fxml"));
                Parent root = loader.load();
                ChonNhanKhauController controller = loader.getController();
                controller.setSharedDataModel(sharedDataModel);
                Scene nhanKhauScene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Chọn Nhân khẩu");
                stage.setScene(nhanKhauScene);
                stage.show();
                System.out.println(sharedDataModel.getSelectedRows().size());
                stage.setOnHidden(e -> {
                    nhanKhauTable.getItems().clear();
                    for (NhanKhauTableModel nk : sharedDataModel.getSelectedRows()) {
                        if(!containsNhanKhau(nhanKhauTable.getItems(), nk.getHoTenNhanKhau())) {
                            nhanKhauTable.getItems().add(nk);
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deleteNhanKhauBtn.setOnAction(event -> {
            NhanKhauTableModel selectedItem = nhanKhauTable.getSelectionModel().getSelectedItem();
            removeNhanKhau(selectedItem);
            nhanKhauTable.getItems().removeIf(obj->obj.getHoTenNhanKhau().equals(selectedItem.getHoTenNhanKhau()));
        });
        deleteAllNhanKhauBtn.setOnAction(event -> {
            nhanKhauTable.getItems().clear();
            sharedDataModel.removeAllRow();
            nhanKhauTable.getSelectionModel().clearSelection();
        });
    }
}
