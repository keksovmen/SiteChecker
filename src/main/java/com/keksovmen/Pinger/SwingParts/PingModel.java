package com.keksovmen.Pinger.SwingParts;

import com.keksovmen.Pinger.Util.SiteState;
import com.keksovmen.Pinger.Util.Observer;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;

public class PingModel extends AbstractTableModel implements Observer<SiteState> {

    static final int STATE_COLUMN = 0;
    static final int ADDRESS_COLUMN = 1;
    static final int TIME_COLUMN = 2;

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
            case STATE_COLUMN:
                return state.isActive();
            case ADDRESS_COLUMN:
                return state.getAddress();
            case TIME_COLUMN:
                return state.getLastResponseTime();
            default:
                return "DEFAULT";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case STATE_COLUMN:
                return "State";
            case ADDRESS_COLUMN:
                return "Address";
            case TIME_COLUMN:
                return "Delay ms";
            default:
                return "DEFAULT";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
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
