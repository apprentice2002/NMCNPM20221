package com.cnpm.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TamTru {
    private int id;
    private int idNhanKhau;
    private String tuNgay;
    private String denNgay;
    private String lyDo;

    public TamTru(int id, int idNhanKhau, String tuNgay, String denNgay, String lyDo) {
        this.id = id;
        this.idNhanKhau = idNhanKhau;
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.lyDo = lyDo;
    }
}
