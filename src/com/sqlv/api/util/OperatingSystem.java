package com.sqlv.api.util;

/**
 * @author Jacob
 * @since 10/27/2015
 */
public enum OperatingSystem {

    WINDOWS,
    LINUX,
    MAC,
    OTHER;

    /**
     * Gets the user's running operating system.
     * @return If found, the <t>OperatingSystem</t> enum representing the os the user is running; otherwise, <t>OTHER</t>;
     */
    public static OperatingSystem getSystem() {
        String os = System.getProperty("os.name");
        for (OperatingSystem o : values()) {
            if (o.toString().toLowerCase().contains(os)) {
                return o;
            }
        }
        return OTHER;
    }
}
