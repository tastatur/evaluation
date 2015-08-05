/*
 * Copyright 2014-2015 Ioannis Karatassis
 *
 * Master Thesis
 */
package de.unidue.misc.search.karatassis;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Stream Utilities
 */
public class StreamUtils {

    public static String convertStreamToString(InputStream is) throws IOException {
        String result = "";
        try {
            result = IOUtils.toString(is, "UTF-8");
        }
        finally {
            IOUtils.closeQuietly(is);
        }
        return result;
    }
}
