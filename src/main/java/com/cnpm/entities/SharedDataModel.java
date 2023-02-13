package com.cnpm.entities;

import com.cnpm.entities.NhanKhauTableModel;

import java.util.ArrayList;
import java.util.List;

public class SharedDataModel {
    private List<NhanKhauTableModel> selectedRows = new ArrayList<>();

    public void addSelectedRow(NhanKhauTableModel nk) {
        selectedRows.add(nk);
    }

    public void removeSelectedRow(NhanKhauTableModel nk) {
        selectedRows.remove(nk);
    }

    public void removeAllRow() {
        selectedRows.clear();
    }
    public List<NhanKhauTableModel> getSelectedRows() {
        return selectedRows;
    }
}
