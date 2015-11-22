package com.sqlv;

import java.util.List;

/**
 * @author Jacob Doiron
 * @since 11/21/2015
 */
public class Query {

    private final String query;
    private final String attribute;
    private final String value;
    private final Relvar relvar;

    private final List<String> attributes;

    /**
     * Constructs a Query from relevant information.
     *
     * @param query      The query in its entirety.
     * @param attribute  The attribute to search for.
     * @param value      The value to search for.
     * @param relvar     The related relvar.
     * @param attributes The attributes to pull.
     */
    public Query(String query, String attribute, String value, Relvar relvar, List<String> attributes) {
        this.query = query;
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
