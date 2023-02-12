package com.cnpm.utilities;

import com.cnpm.entities.PhatQua;
import javafx.scene.control.CheckBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhatQuaTableModel extends PhatQua {
    private String hoTen;

    private CheckBox deleteBox;
    private String tenQua;
    private int tuoi;
    private String tenDotPhat;
    private int giaTri;
    public PhatQuaTableModel(String hoTen, String tenQua, int tuoi, String tenDotPhat,int giaTri,int daDuyet) {
        super(daDuyet);
        this.hoTen = hoTen;
        this.tenQua = tenQua;
        this.tuoi = tuoi;
        this.tenDotPhat = tenDotPhat;
        this.giaTri= giaTri;
        this.setDaDuyet(daDuyet);
        this.deleteBox = new CheckBox();
    }
    public PhatQuaTableModel(int idQua){
        super(idQua);

    }

}

