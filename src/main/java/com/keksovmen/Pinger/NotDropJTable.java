package com.keksovmen.Pinger;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

public class NotDropJTable extends JTable {

    @Override
    public void tableChanged(TableModelEvent e) {
        int selectedColumn = getSelectedColumn();
        int selectedRow = getSelectedRow();

        super.tableChanged(e);

        changeSelection(selectedRow, selectedColumn, true, false);
    }
}
