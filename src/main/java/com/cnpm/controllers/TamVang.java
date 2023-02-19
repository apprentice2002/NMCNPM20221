package com.cnpm.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TamVang {
    private int id;
    private int idNhanKhau;
    private String noiTamTru;
    private String tuNgay;
    private String denNgay;
    private String lyDo;

    public TamVang(int id, int idNhanKhau, String noiTamTru, String tuNgay, String denNgay, String lyDo) {
        this.id = id;
        this.idNhanKhau = idNhanKhau;
        this.noiTamTru = noiTamTru;
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.lyDo = lyDo;
    }
}
