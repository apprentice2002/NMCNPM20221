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
    private int ma_nhan_khau;
    private int daDuyet;

    public PhatQua(int idPhatQua,int daDuyet) {
        this.idPhatQua= idPhatQua;
        this.daDuyet = daDuyet;
    }

    public PhatQua (int idPhatQua, int idDotPhat, int idQua , int ma_nhan_khau, int daDuyet) {
        this.idPhatQua= idPhatQua;
        this.ma_nhan_khau = ma_nhan_khau;
        this.idDotPhat = idDotPhat;
        this.idQua = idQua;
        this.daDuyet= daDuyet;
    }
}
