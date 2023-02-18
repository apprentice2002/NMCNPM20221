package com.cnpm.entities;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PhatQua {
    private int idPhatQua;
    private int idDotPhat;
    private int idQua;
    private int idNhanKhau;
    private String daDuyet;

    public PhatQua(int idPhatQua,String daDuyet) {
        this.idPhatQua= idPhatQua;
        this.daDuyet = daDuyet;
    }

    public PhatQua (int idPhatQua, int idDotPhat, int idQua , int idNhanKhau, String daDuyet) {
        this.idPhatQua= idPhatQua;
        this.idNhanKhau = idNhanKhau;
        this.idDotPhat = idDotPhat;
        this.idQua = idQua;
        this.daDuyet= daDuyet;
    }
}
