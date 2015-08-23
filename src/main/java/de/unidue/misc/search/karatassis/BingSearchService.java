/*
 * Copyright 2014-2015 Ioannis Karatassis
 *
 * Master Thesis
 */
package de.unidue.misc.search.karatassis;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class represents a service that connects to the bing search api for
 * retrieving search results.
 */
@Component("searchService")
public final class BingSearchService {

    private static final String ID = "ID";
    private static final String TITLE = "Title";
    private static final String DISPLAYURL = "DisplayUrl";
    private static final String URL = "Url";
    private static final String DESCRIPTION = "Description";

    private static final String BASE_URL = "https://api.datamarket.azure.com/Bing/Search/v1/Web";

    @Value("${de.unidue.search.api.key}")
    private String accountKey;

    public List<BingWebResult> executeSearchQuery(final String query) throws Exception {
        final String finalBingUrl = generateBingUrl(query);
        List<BingWebResult> searchResults = new ArrayList<>();
        final JSONObject jsonResult = fetchJSONResult(finalBingUrl);
        final JSONArray res = jsonResult.getJSONObject("d").getJSONArray("results");
        for (int i = 0; i < res.length(); i++) {
            final JSONObject o = res.getJSONObject(i);
            BingWebResult bingResultEntry = new BingWebResult();
            bingResultEntry.setId(JSONUtils.getString(o, ID, ""));
            bingResultEntry.setTitle(JSONUtils.getString(o, TITLE, ""));
            bingResultEntry.setDescription(JSONUtils.getString(o, DESCRIPTION, ""));
            bingResultEntry.setDisplayUrl(JSONUtils.getString(o, DISPLAYURL, ""));
            bingResultEntry.setUrl(JSONUtils.getString(o, URL, ""));
            searchResults.add(bingResultEntry);
        }

        return searchResults;
    }

    private String encode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8");
    }


    private String generateBingUrl(final String query) throws UnsupportedEncodingException {
        return BASE_URL.concat("?Query=").concat(encode(String.format("'%s'", query))).concat
                ("&$format=JSON").concat("&Market=").concat(encode("'de-DE'")).concat("&Latitude=52.5186&Longitude=13.4081").concat("&$top=10");
    }


    private JSONObject fetchJSONResult(String site) throws Exception {
        JSONObject json;
        URL url = new URL(site);
        URLConnection connection = url.openConnection();
        for (Map.Entry<String, String> field : BingUtils.getHeaderFields(accountKey).entrySet()) {
            connection.setRequestProperty(field.getKey(), field.getValue());
        }
        String jsonString = StreamUtils.convertStreamToString(connection.getInputStream());
        json = new JSONObject(jsonString);
        return json;
    }
}