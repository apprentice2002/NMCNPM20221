package com.cnpm.entities;

public class NhanKhauTableModel {
    private String maHoKhau;
    private String maNhanKhau;
    private String hoTenNhanKhau;
    private String quanHeVoiChuHo;
    private String gioiTinh;
    private int tuoi;

    public NhanKhauTableModel(String maNhanKhau, String hoTenNhanKhau, String gioiTinh , int tuoi, String maHoKhau) {
        this.maNhanKhau = maNhanKhau;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.gioiTinh = gioiTinh;
        this.tuoi = tuoi;
        this.maHoKhau = maHoKhau;
    }

    public String getMaHoKhau() {
        return maHoKhau;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public int getTuoi() {
        return tuoi;
    }
    public boolean equals(NhanKhauTableModel nk) {
        if(this.maNhanKhau.equals(nk.maNhanKhau)) return true;
        return false;
    }
    public String getHoTenNhanKhau() {
        return hoTenNhanKhau;
    }

    public String getMaNhanKhau() {
        return maNhanKhau;
    }

    public NhanKhauTableModel(String maNhanKhau , String hoTenNhanKhau , String quanHeVoiChuHo) {
        this.maNhanKhau = maNhanKhau;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
    }

    public String getQuanHeVoiChuHo() {
        return quanHeVoiChuHo;
    }

    public void setHoTenNhanKhau(String hoTenNhanKhau) {
        this.hoTenNhanKhau = hoTenNhanKhau;
    }

    public void setMaNhanKhau(String maNhanKhau) {
        this.maNhanKhau = maNhanKhau;
    }

    public void setQuanHeVoiChuHo(String quanHeVoiChuHo) {
        this.quanHeVoiChuHo = quanHeVoiChuHo;
    }
}
