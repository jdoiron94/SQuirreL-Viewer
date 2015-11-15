package com.sqlv.api.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Jacob Doiron
 * @since 11/14/2015
 */
public class IO {

    /**
     * Reads in all of the bytes of the specified file.
     *
     * @param file The file to read.
     * @return The byte array of the file, if not null; otherwise, null.
     */
    public static byte[] readFile(File file) {
        Path path = file.toPath();
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
