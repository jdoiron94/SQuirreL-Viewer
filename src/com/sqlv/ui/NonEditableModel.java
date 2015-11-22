package com.sqlv.ui;

import javax.swing.table.DefaultTableModel;

/**
 * @author Jacob Doiron
 * @since 11/18/2015
 */
public class NonEditableModel extends DefaultTableModel {

    /**
     * Constructs a NonEditableModel which disables editing of JTable cells.
     *
     * @param data        The data array.
     * @param columnNames The table header names.
     */
    public NonEditableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
