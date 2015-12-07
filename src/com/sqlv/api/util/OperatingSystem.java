package com.sqlv.api.util;

/**
 * @author Jacob Doiron
 * @since 10/27/2015
 */
public enum OperatingSystem {

    WINDOWS,
    LINUX,
    MAC,
    OTHER;

    /**
     * Gets the user's running operating system.
     *
     * @return If found, the <t>OperatingSystem</t> enum representing the os the user is running; otherwise, <t>OTHER</t>;
     */
    public static OperatingSystem getSystem() {
        String os = System.getProperty("os.name").toLowerCase();
        for (OperatingSystem o : values()) {
            if (os.contains(o.toString().toLowerCase())) {
                return o;
            }
        }
        return OTHER;
    }
}
