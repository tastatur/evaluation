/*
 * Copyright 2014-2015 Ioannis Karatassis
 *
 * Master Thesis
 */
package de.unidue.misc.search.karatassis;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;


/**
 * JSON Utilities
 */
public class JSONUtils {

    /**
     * Get the string associated with a key. The default value is returned if
     * there is no such association.
     *
     * @param root          The root object
     * @param key           The key
     * @param defaultResult The default result
     * @return Get the string associated with a key or default value if there is
     * no such association
     */
    public static String getString(JSONObject root, String key, String defaultResult) {
        String res;
        try {
            res = root.getString(key);
        } catch (JSONException e) {
            res = defaultResult;
        }
        return res;
    }
}
