package com.sqlv;

import java.util.List;

/**
 * @author Jacob Doiron
 * @since 11/21/2015
 */
public class Query {

    private final String query;
    private final String table;
    private final String attribute;
    private final String value;
    private final Relvar relvar;

    private final List<String> attributes;

    public Query(String query, String table, String attribute, String value, Relvar relvar, List<String> attributes) {
        this.query = query;
        this.table = table;
        this.attribute = attribute;
        this.value = value;
        this.relvar = relvar;
        this.attributes = attributes;
    }

    /**
     * @return The query to run.
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return The table's title.
     */
    public String getTable() {
        return table;
    }

    /**
     * @return The attribute to search.
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * @return The value to search for.
     */
    public String getValue() {
        return value;
    }

    /**
     * @return The owning relvar.
     */
    public Relvar getRelvar() {
        return relvar;
    }

    /**
     * @return The attributes to take.
     */
    public List<String> getAttributes() {
        return attributes;
    }
}
