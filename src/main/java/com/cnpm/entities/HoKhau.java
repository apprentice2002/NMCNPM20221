package com.cnpm.entities;

import java.util.List;

public class HoKhau {
    private String maHoKhau;
    private String ngayTao;
    private String maChuHo;
    private String diaChiHoKhau;
    private String soThanhVien;

    public HoKhau (String maHoKhau, String ngayTao, String maChuHo, String diaChi, String soThanhVien) {
        this.maHoKhau = maHoKhau;
        this.ngayTao = ngayTao;
        this.soThanhVien = soThanhVien;
        this.diaChiHoKhau = diaChi;
        this.maChuHo = maChuHo;
    }
    public HoKhau(String maHoKhau, String soThanhVien, String diaChiHoKhau) {
        this.maHoKhau = maHoKhau;
        this.diaChiHoKhau = diaChiHoKhau;
        this.soThanhVien = soThanhVien;
    }

    public HoKhau(String maHoKhau, String diaChiHoKhau) {
        this.maHoKhau = maHoKhau;
        this.diaChiHoKhau = diaChiHoKhau;
    }

    public HoKhau(String maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    public void setSoThanhVien(String soThanhVien) {
        this.soThanhVien = soThanhVien;
    }

    public void setMaHoKhau(String maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    public String getMaHoKhau() {
        return maHoKhau;
    }

    public String getDiaChiHoKhau() {
        return diaChiHoKhau;
    }

    public String getMaChuHo() {
        return maChuHo;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public String getSoThanhVien() {
        return soThanhVien;
    }

    public void setDiaChiHoKhau(String diaChiHoKhau) {
        this.diaChiHoKhau = diaChiHoKhau;
    }

    public void setMaChuHo(String maChuHo) {
        this.maChuHo = maChuHo;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

}
