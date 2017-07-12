package com.dl.igviewer.utility.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by logicmelody on 2016/1/28.
 */
public class JsonUtils {

    public static String getStringFromJson(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key) ? jsonObject.getString(key) : "";
    }

    public static int getIntFromJson(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key) ? jsonObject.getInt(key) : -1;
    }

    public static long getLongFromJson(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key) ? jsonObject.getLong(key) : -1L;
    }

    public static boolean getBooleanFromJson(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key) ? jsonObject.getBoolean(key) : false;
    }

    public static JSONObject getJsonObjectFromJson(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key) ? jsonObject.getJSONObject(key) : null;
    }

    public static JSONArray getJsonArrayFromJson(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key) ? jsonObject.getJSONArray(key) : null;
    }
}
