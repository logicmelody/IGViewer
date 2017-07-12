package com.dl.igviewer.utility.utils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by daz on 10/9/15.
 */
public class UrlUtils {

    public static String buildUrlString(String baseUrl, Map<String, String> queries) {
        String queryString = UrlUtils.buildQueryString(queries);

        if (queryString.length() > 0) {
            return baseUrl + "?" + queryString;

        } else {
            return baseUrl;
        }
    }

    private static String buildQueryString(Map<String, String> queries) {
        String queryString = "";

        if (queries != null) {
            Iterator iter = queries.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                queryString += entry.getKey() + "=" + entry.getValue();
                queryString += "&";
            }

            if (queryString.length() > 0) {
                queryString = queryString.substring(0, queryString.length() - 1);
            }
        }

        return queryString;
    }
}
