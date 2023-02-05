package com.cnpm.entities;

import com.cnpm.utilities.DBConnection;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
@Setter
public class NhanKhau {
    private int id;
    private String ho_ten;
    private String ngay_sinh;
    private String gioi_tinh;
    private String dia_chi_hien_nay;

    public NhanKhau(int id, String ho_ten, String ngay_sinh, String gioi_tinh, String dia_chi_hien_nay) {
        this.id = id;
        this.ho_ten = ho_ten;
        this.ngay_sinh = ngay_sinh;
        this.gioi_tinh = gioi_tinh;
        this.dia_chi_hien_nay = dia_chi_hien_nay;
    }
}
