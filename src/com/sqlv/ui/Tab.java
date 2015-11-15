package com.sqlv.ui;

import javax.swing.JTable;

/**
 * @author Jacob Doiron
 * @since 11/14/2015
 */
public class Tab {

    private final String title;
    private final JTable table;

    /**
     * Constructs a Tab with a title and an associated JTable.
     *
     * @param title The title to be set.
     * @param table The JTable associated with the tab.
     */
    public Tab(String title, JTable table) {
        this.title = title;
        this.table = table;
    }

    /**
     * @return The title of the tab.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The JTable associated with the tab.
     */
    public JTable getTable() {
        return table;
    }
}