package com.cnpm.utilities;

public class TableModel {
    private String maHoKhau;
    private String hoTenChuHo;
    private String diaChiHoKhau;


    public TableModel(String maHoKhau, String hoTenChuHo, String diaChiHoKhau) {
        this.maHoKhau = maHoKhau;
        this.diaChiHoKhau = diaChiHoKhau;
        this.hoTenChuHo = hoTenChuHo;
    }

    public String getMaHoKhau() {
        return maHoKhau;
    }

    public String getDiaChiHoKhau() {
        return diaChiHoKhau;
    }

    public String getHoTenChuHo() {
        return hoTenChuHo;
    }

    public void setDiaChiHoKhau(String diaChiHoKhau) {
        this.diaChiHoKhau = diaChiHoKhau;
    }

    public void setMaHoKhau(String maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    public void setHoTenChuHo(String hoTenChuHo) {
        this.hoTenChuHo = hoTenChuHo;
    }

}
