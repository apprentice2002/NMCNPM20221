package com.cnpm.utilities;

import com.cnpm.entities.HoKhau;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class HoKhauTableModel extends HoKhau {

    private String hoTenChuHo;

    private CheckBox deleteBox;

    public HoKhauTableModel(String maHoKhau, String ngayTao, String maChuHo, String diaChi, String soThanhVien) {
        super(maHoKhau, ngayTao, maChuHo, diaChi, soThanhVien);
    }
    public HoKhauTableModel(String maHoKhau, String hoTenChuHo, String diaChi, String soThanhVien) {
        super(maHoKhau,diaChi,soThanhVien);
        this.hoTenChuHo = hoTenChuHo;
        this.setMaHoKhau(maHoKhau);
        this.setSoThanhVien(soThanhVien);
        this.setDiaChiHoKhau(diaChi);
        this.deleteBox = new CheckBox();
    }

    public HoKhauTableModel(String maHoKhau, String tenChuHo, String diaChiHoKhau) {
        super(maHoKhau,tenChuHo,diaChiHoKhau);
    }

    public String getHoTenChuHo() {
        return hoTenChuHo;
    }

    @Override
    public String getMaHoKhau() {
        return super.getMaHoKhau();
    }

    public CheckBox getDeleteBox() {
        return deleteBox;
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
