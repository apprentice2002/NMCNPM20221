package com.cnpm.entities;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class MinhChung {
    private int idMinhChung;
    private int ma_nhan_khau;
    private String truong;
    private String lop;
    private String thanhTichHocTap;
    private Date  ngayKhaiBao;
    public MinhChung(String truong,String lop ,String thanhTichHocTap,Date  ngayKhaiBao ) {
        this.truong = truong;
        this.lop = lop;
        this. thanhTichHocTap= thanhTichHocTap;
        this.ngayKhaiBao = ngayKhaiBao;}
    public MinhChung( int idMinhChung,int ma_nhan_khau, String truong,String lop ,String thanhTichHocTap,Date  ngayKhaiBao ) {
        this.idMinhChung= idMinhChung;
        this.ma_nhan_khau = ma_nhan_khau;
        this.truong = truong;
        this.lop = lop;
        this. thanhTichHocTap= thanhTichHocTap;
        this.ngayKhaiBao = ngayKhaiBao ;
    }
}
