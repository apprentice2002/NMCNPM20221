package com.cnpm.utilities;

import java.util.ArrayList;
import java.util.List;

public class SharedDataModel {
    private List<themHoKhauNhanKhauTableModel> selectedRows = new ArrayList<>();

    public void addSelectedRow(themHoKhauNhanKhauTableModel nk) {
        selectedRows.add(nk);
    }

    public void removeSelectedRow(themHoKhauNhanKhauTableModel nk) {
        selectedRows.remove(nk);
    }

    public void removeAllRow() {
        selectedRows.clear();
    }
    public List<themHoKhauNhanKhauTableModel> getSelectedRows() {
        return selectedRows;
    }
}
