/*
 * Copyright 2014-2015 Ioannis Karatassis
 *
 * Master Thesis
 */
package de.unidue.misc.search.karatassis;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * A bing utility class
 */
public class BingUtils {

    public static final String ACCOUNT_KEY = System.getProperty("de.unidue.search.api.key");

    /**
     * @return The header fields provided to the bing search API
     */
    public static Map<String, String> getHeaderFields() throws UnsupportedEncodingException {
        Map<String, String> fields = new HashMap<>();
        fields.put("Authorization", getAuthentificationHeaderField());
        return fields;
    }

    private static String getAuthentificationHeaderField() throws UnsupportedEncodingException {
        String encrypted = base64Encode(":" + ACCOUNT_KEY);
        return "Basic " + encrypted;
    }

    private static String base64Encode(final String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("UTF-8");
        return Base64.getEncoder().encodeToString(bytes);
    }
}
