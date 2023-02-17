package com.cnpm.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongKePhatThuongTableModel {
    private int idHoKhau1;
    private String hoTen1;
    private int soPhanThuong1;
    private int tongGiaTri1;
    public    ThongKePhatThuongTableModel(int idHoKhau1 ,String hoTen1,int soPhanThuong1,int tongGiaTri1) {
        this.idHoKhau1=idHoKhau1;
        this.hoTen1=hoTen1;
        this.soPhanThuong1=soPhanThuong1;
        this.tongGiaTri1=tongGiaTri1;
    }
}
