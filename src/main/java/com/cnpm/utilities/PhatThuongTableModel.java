package com.cnpm.utilities;

import com.cnpm.entities.PhatQua;
import com.cnpm.entities.PhatThuong;
import javafx.scene.control.CheckBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhatThuongTableModel extends PhatThuong {
    private String hoTen;

    private CheckBox deleteBox;
    private String tenQua;
    private String thanhTichHocTap;
    private String tenDotPhat;
    private int giaTri;
    PhatQua phatqua =null;

    public PhatThuongTableModel(String hoTen, String tenQua, String thanhTichHocTap, String tenDotPhat, int giaTri, int daDuyet) {
        super(daDuyet);
        this.tenQua = tenQua;
        this.thanhTichHocTap = thanhTichHocTap;
        this.tenDotPhat = tenDotPhat;
        this.giaTri = giaTri;
        this.hoTen = hoTen;
        this.setDaDuyet(daDuyet);
        this.deleteBox = new CheckBox();

    }
}
