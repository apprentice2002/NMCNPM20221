package com.cnpm.entities;

import com.cnpm.entities.HoKhau;
import javafx.scene.control.CheckBox;

import java.sql.Date;

public class HoKhauTableModel extends HoKhau {

    private String hoTenChuHo;
    private String themNhanKhau;
    private Date ngayThayDoi;
    private String xoaNhanKhau;
    private String ghiChu;

    public HoKhauTableModel(String maHoKhau, String hoTenChuHo, Date ngayThayDoi  , String themNhanKhau, String xoaNhanKhau, String ghiChu) {
        super(maHoKhau);
        this.hoTenChuHo = hoTenChuHo;
        this.themNhanKhau = themNhanKhau;
        this.ngayThayDoi = ngayThayDoi;
        this.xoaNhanKhau = xoaNhanKhau;
        this.ghiChu = ghiChu;
    }

    public HoKhauTableModel(String maHoKhau, String ngayTao, String maChuHo, String diaChi, String soThanhVien) {
        super(maHoKhau, ngayTao, maChuHo, diaChi, soThanhVien);
    }


    public HoKhauTableModel(String maHoKhau, String hoTenChuHo, String diaChi, String soThanhVien) {
        super(maHoKhau,diaChi,soThanhVien);
        this.hoTenChuHo = hoTenChuHo;
        this.setMaHoKhau(maHoKhau);
        this.setSoThanhVien(soThanhVien);
        this.setDiaChiHoKhau(diaChi);
    }

    public HoKhauTableModel(String maHoKhau, String tenChuHo, String diaChiHoKhau) {
        super(maHoKhau,diaChiHoKhau);
        this.hoTenChuHo = tenChuHo;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public Date getNgayThayDoi() {
        return ngayThayDoi;
    }

    public String getThemNhanKhau() {
        return themNhanKhau;
    }

    public String getXoaNhanKhau() {
        return xoaNhanKhau;
    }

    public String getHoTenChuHo() {
        return hoTenChuHo;
    }

    @Override
    public String getMaHoKhau() {
        return super.getMaHoKhau();
    }


    @Override
    public String getDiaChiHoKhau() {
        return super.getDiaChiHoKhau();
    }

    @Override
    public String getMaChuHo() {
        return super.getMaChuHo();
    }

    @Override
    public String getNgayTao() {
        return super.getNgayTao();
    }

    @Override
    public String getSoThanhVien() {
        return super.getSoThanhVien();
    }
}
