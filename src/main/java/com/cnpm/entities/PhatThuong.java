package com.cnpm.entities;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PhatThuong {
    private int idPhatThuong;
    private int idDotPhat;
    private int idQua;
    private int idMinhChung;
    private int daDuyet;

    public PhatThuong(int idPhatThuong,int daDuyet) {
        this.daDuyet = daDuyet;
        this.idPhatThuong=idPhatThuong;
    }

    public PhatThuong (int idPhatThuong, int idDotPhat, int idQua , int idMinhChung, int daDuyet) {
        this.idPhatThuong= idPhatThuong;
        this.idMinhChung = idMinhChung;
        this.idDotPhat = idDotPhat;
        this.idQua = idQua;
        this. daDuyet= daDuyet;
    }
}
