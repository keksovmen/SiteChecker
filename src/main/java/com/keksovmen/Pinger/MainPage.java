package com.keksovmen.Pinger;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class MainPage {
    private JButton addButton;
    private JButton removeButton;
    private JPanel rootPane;
    private JTable dataTable;

    private JFrame jFrame = new JFrame("Pinger");

    private final Handler siteHandler;


    public MainPage(Handler siteHandler, TableModel tableModel) {
        this.siteHandler = siteHandler;

        addButton.addActionListener(e -> {
            String result = JOptionPane.showInputDialog("Enter site:");
            if (!siteHandler.addSite(result)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error can't create file to store data",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        removeButton.addActionListener(e -> {
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow == -1) return;

            String site = (String) tableModel.getValueAt(selectedRow, PingModel.ADDRESS_COLUMN);
            if (!siteHandler.removeSite(site)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error can't find/open file where data is stored",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        dataTable.setModel(tableModel);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.getTableHeader().setReorderingAllowed(false);
        dataTable.getTableHeader().setResizingAllowed(false);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setSortable(PingModel.STATE_COLUMN, false);
        sorter.setSortable(PingModel.TIME_COLUMN, false);

        dataTable.setRowSorter(sorter);

        dataTable.getColumnModel().getColumn(PingModel.STATE_COLUMN).setMaxWidth(50);
        dataTable.getColumnModel().getColumn(PingModel.STATE_COLUMN).setCellRenderer(new ColorRenderer());

        dataTable.getColumnModel().getColumn(PingModel.TIME_COLUMN).setMaxWidth(100);


        jFrame.setContentPane(rootPane);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(400, 300);
        jFrame.setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        dataTable = new NotDropJTable();
    }

    private static class ColorRenderer implements TableCellRenderer {

        final JPanel colorPanel = new JPanel();
        final Color DARK_GREEN = new Color(0, 120, 0);
        final Color DARK_RED = new Color(140, 0, 0);

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            boolean val = (boolean) value;
            if (isSelected) {
                colorPanel.setBackground(val ? DARK_GREEN : DARK_RED);
            } else {
                colorPanel.setBackground(val ? Color.GREEN : Color.RED);
            }
            return colorPanel;
        }
    }
}