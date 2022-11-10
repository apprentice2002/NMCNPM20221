package com.cnpm;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class NhanKhau {
    private int ma_nhan_khau;
    private String ho_ten;
    private String bi_danh;
    private Date ngay_sinh;
    private String noi_sinh;
    private String gioi_tinh;
    private String nguyen_quan;
    private String dan_toc;
    private String ton_giao;
    private String quoc_tich;
    private String nghe_nghiep;
    private String noi_lam_viec;
    private String so_dien_thoai;
    private String CMND;
    private Date ngay_cap;
    private Date ngay_chuyen_den;
    private String noi_thuong_tru_truoc;
    private String trang_thai;

    public NhanKhau(int ma_nhan_khau, String ho_ten, Date ngay_sinh, String noi_sinh, String gioi_tinh) {
        this.ma_nhan_khau = ma_nhan_khau;
        this.ho_ten = ho_ten;
        this.ngay_sinh = ngay_sinh;
        this.noi_sinh = noi_sinh;
        this.gioi_tinh = gioi_tinh;
    }
}
