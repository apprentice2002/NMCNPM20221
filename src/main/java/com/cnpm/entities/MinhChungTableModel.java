package com.cnpm.entities;

import com.cnpm.entities.MinhChung;
import javafx.scene.control.CheckBox;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class MinhChungTableModel extends MinhChung {
    private String hoTen;

    private CheckBox deleteBox;
    public MinhChungTableModel(int idMinhChung,String hoTen,String truong, String lop , String thanhTichHocTap, Date ngayKhaiBao ) {
        super(truong,lop,thanhTichHocTap,ngayKhaiBao);
        this.setIdMinhChung(idMinhChung);
        this.hoTen=hoTen;
        this.setThanhTichHocTap(thanhTichHocTap);
        this.setTruong(truong);
        this.setLop(lop);
        this.setNgayKhaiBao(ngayKhaiBao);
        this.deleteBox = new CheckBox();

    }

}
