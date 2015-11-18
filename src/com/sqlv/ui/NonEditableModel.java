package com.sqlv.ui;

import javax.swing.table.DefaultTableModel;

/**
 * @author Jacob Doiron
 * @since 11/18/2015
 */
public class NonEditableModel extends DefaultTableModel {

    public NonEditableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
