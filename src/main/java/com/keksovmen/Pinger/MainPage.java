package com.keksovmen.Pinger;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
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
            if(selectedRow == -1) return;

            String site = (String) tableModel.getValueAt(selectedRow, 1);
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
        dataTable.getColumnModel().getColumn(0).setMaxWidth(50);
        dataTable.getColumnModel().getColumn(2).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
                JPanel colorPanel = new JPanel();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                boolean val = (boolean) tableModel.getValueAt(row, column);
                colorPanel.setBackground(val ? Color.GREEN : Color.RED);
                return colorPanel;
            }
        });
//        dataTable.setSelectionModel(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jFrame.setContentPane(rootPane);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(400, 300);
        jFrame.setVisible(true);

    }
}
