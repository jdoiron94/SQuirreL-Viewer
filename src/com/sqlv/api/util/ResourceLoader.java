package com.sqlv.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jacob Doiron
 * @since 11/22/2015
 */
public class ResourceLoader {

    private final String directory;

    /**
     * Constructs a ResourceLoader which allows loading of resources from within a jar file.
     *
     * @param directory The directory to load from.
     */
    public ResourceLoader(String directory) {
        if (!directory.endsWith("/")) {
            directory += "/";
        }
        this.directory = directory;
    }

    /**
     * Reads in an InputStream and transforms it into a byte array.
     *
     * @param is The InputStream to read in.
     * @return The equivalent byte array.
     */
    public byte[] readStream(InputStream is) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            for (int i; (i = is.read(buffer)) != -1;) {
                stream.write(buffer, 0, i);
            }
            stream.flush();
            return stream.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Gets the InputStream for the file from the specified path.
     *
     * @param path The path to follow.
     * @return The InputStream of the file.
     */
    public InputStream getStream(String path) {
        return ClassLoader.getSystemResourceAsStream(directory + path);
    }
}
