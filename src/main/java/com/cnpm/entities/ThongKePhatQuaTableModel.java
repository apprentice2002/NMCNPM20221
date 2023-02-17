package com.cnpm.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongKePhatQuaTableModel {
    private int idHoKhau;
    private String hoTen;
    private int soPhanQua;
    private int tongGiaTri;
public    ThongKePhatQuaTableModel(int idHoKhau ,String hoTen,int soPhanQua,int tongGiaTri) {
    this.idHoKhau=idHoKhau;
    this.hoTen=hoTen;
    this.soPhanQua=soPhanQua;
    this.tongGiaTri=tongGiaTri;
}


}
