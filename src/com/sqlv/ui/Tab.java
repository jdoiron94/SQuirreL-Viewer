package com.sqlv.ui;

import com.sqlv.Relvar;

import javax.swing.*;

/**
 * @author Jacob Doiron
 * @since 11/14/2015
 */
public class Tab {

    private final String title;
    private final JTable table;
    private final Relvar relvar;

    /**
     * Constructs a Tab with a title and an associated JTable.
     *
     * @param title The title to be set.
     * @param table The JTable associated with the tab.
     */
    public Tab(String title, JTable table, Relvar relvar) {
        this.title = title;
        this.table = table;
        this.relvar = relvar;
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

    /**
     * @return The Relvar associated with the tab.
     */
    public Relvar getRelvar() {
        return relvar;
    }
}
