package com.cnpm.utilities;

public class NhanKhauTableModel {
    private String maNhanKhau;
    private String hoTenNhanKhau;
    private String quanHeVoiChuHo;

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
