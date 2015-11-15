package com.sqlv;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob Doiron
 * @since 11/14/2015
 */
public class Relvar {

    private final String title;
    private final String key;

    private final List<String> attributes;

    /**
     * Constructs a Relvar object with an associated title, primary key, and attributes.
     *
     * @param title The title of the relvar.
     */
    public Relvar(String title, String key) {
        this.title = title;
        this.key = key;
        this.attributes = new ArrayList<>();
    }

    /**
     * Adds an attribute to the relvar.
     *
     * @param attribute The attribute to add.
     */
    public void addAttribute(String attribute) {
        attributes.add(attribute);
    }

    /**
     * @return The title of the relvar.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The primary key of the relvar.
     */
    public String getKey() {
        return key;
    }

    /**
     * @return A list of the attributes in the relvar.
     */
    public List<String> getAttributes() {
        return attributes;
    }
}
