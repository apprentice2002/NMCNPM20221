package com.cnpm.entities;

import javafx.scene.control.CheckBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NhanKhau {
    private int ID;
    private String ma_nhan_khau;
    private String ho_ten;
    private String ngay_sinh;
    private String dia_chi_hien_nay;

    private CheckBox deleteBox;

    public NhanKhau(int ID, String ma_nhan_khau, String ho_ten, String ngay_sinh, String dia_chi_hien_nay) {
        this.ID = ID;
        this.ma_nhan_khau = ma_nhan_khau;
        this.ho_ten = ho_ten;
        this.ngay_sinh = ngay_sinh;
        this.dia_chi_hien_nay = dia_chi_hien_nay;
        this.deleteBox = new CheckBox();
    }
}
