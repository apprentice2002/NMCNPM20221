package com.cnpm.utilities;

import java.sql.Date;

public class thayDoiNhanKhauTableModel {
    private String maHoKhau;
    private String tenChuHo;
    private Date ngayThayDoi;
    private int themNhanKhau;
    private int xoaNhanKhau;
    private String ghiChu;

    public void setMaHoKhau(String maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    public String getMaHoKhau() {
        return maHoKhau;
    }

    public int getThemNhanKhau() {
        return themNhanKhau;
    }

    public int getXoaNhanKhau() {
        return xoaNhanKhau;
    }

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public String getTenChuHo() {
        return tenChuHo;
    }

    public Date getNgayThayDoi() {
        return ngayThayDoi;
    }

    public void setNgayThayDoi(Date ngayThayDoi) {
        this.ngayThayDoi = ngayThayDoi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public thayDoiNhanKhauTableModel(String maHoKhau, String tenChuHo, Date ngayThayDoi, int themNhanKhau, int xoaNhanKhau, String ghiChu) {
        this.maHoKhau = maHoKhau;
        this.tenChuHo = tenChuHo;
        this.ngayThayDoi = ngayThayDoi;
        this.themNhanKhau = themNhanKhau;
        this.xoaNhanKhau = xoaNhanKhau;
        this.ghiChu = ghiChu;
    }
}
