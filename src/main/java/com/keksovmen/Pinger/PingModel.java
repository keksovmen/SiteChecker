package com.keksovmen.Pinger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;

public class PingModel extends AbstractTableModel implements Observer<SiteState> {

    private List<SiteState> unmodifiableSiteList = new LinkedList<>();

    @Override
    public int getRowCount() {
        return unmodifiableSiteList.size();
    }

    @Override
    public int getColumnCount() {
        return SiteState.FILEDS_AMOUTN;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SiteState state = unmodifiableSiteList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return state.isActive();
            case 1:
                return state.getAddress();
            case 2:
                return state.getLastResponseTime();
            default:
                return "DEFAULT";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "State";
            case 1:
                return "Address";
            case 2:
                return "Delay";
            default:
                return "DEFAULT";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
//            case 0:
//                return Icon.class;
            default:
                return Object.class;
        }
    }


    @Override
    public void observe(List<SiteState> data) {
        unmodifiableSiteList = data;
        fireTableDataChanged();
    }

}
