package com.cnpm.utilities;

import java.util.Date;

public class themHoKhauNhanKhauTableModel {
    private String maNhanKhau;
    private Date ngaySinh;
    private String quanHeVoiChuHo;

    public void setQuanHeVoiChuHo(String quanHeVoiChuHo) {
        this.quanHeVoiChuHo = quanHeVoiChuHo;
    }

    public void setMaNhanKhau(String maNhanKhau) {
        this.maNhanKhau = maNhanKhau;
    }

    public String getMaNhanKhau() {
        return maNhanKhau;
    }

    public String getQuanHeVoiChuHo() {
        return quanHeVoiChuHo;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

}
