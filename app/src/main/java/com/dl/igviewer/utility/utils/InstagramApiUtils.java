package com.dl.igviewer.utility.utils;

import android.content.Context;
import android.text.TextUtils;

import com.dl.igviewer.R;
import com.dl.igviewer.datastructure.IGUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InstagramApiUtils {

    private static final String AUTHORIZATION_URL =
            "https://api.instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=code";
    private static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";
    private static final String API_URL = "https://api.instagram.com/v1";

    public static final class EndPointKeys {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String USER = "user";
        public static final String ID = "id";
        public static final String USER_NAME = "username";
        public static final String FULL_NAME = "full_name";
        public static final String PROFILE_PICTURE = "profile_picture";
        public static final String DATA = "data";
        public static final String MAX_ID = "max_id";
        public static final String MIN_ID = "min_id";
        public static final String COUNT = "count";
        public static final String PAGINATION = "pagination";
        public static final String NEXT_URL = "next_url";
        public static final String IMAGES = "images";
        public static final String IMAGE = "image";
        public static final String THUMBNAIL = "thumbnail";
        public static final String URL = "url";
        public static final String STANDARD_RESOLUTION = "standard_resolution";
        public static final String CREATED_TIME = "created_time";
        public static final String LIKES = "likes";
        public static final String TYPE = "type";
        public static final String WIDTH = "width";
        public static final String HEIGHT = "height";
    }


    public static String getAuthorizationUrl(Context context) {
        return AUTHORIZATION_URL.replaceAll("CLIENT-ID", context.getString(R.string.instagram_client_id))
                                .replaceAll("REDIRECT-URI", context.getString(R.string.instagram_client_redirect_uri));
    }

    public static String getAuthorizationUrl(String clientId, String redirectUri) {
        return AUTHORIZATION_URL.replaceAll("CLIENT-ID", clientId).replaceAll("REDIRECT-URI", redirectUri);
    }

    public static String getTokenUrl() {
        return TOKEN_URL;
    }

    public static Map<String, String> getTokenBodyMap(Context context, String code) {
        Map<String, String> map = new HashMap<>();

        map.put("client_id", context.getString(R.string.instagram_client_id));
        map.put("client_secret", context.getString(R.string.instagram_client_secret));
        map.put("redirect_uri", context.getString(R.string.instagram_client_redirect_uri));
        map.put("grant_type", "authorization_code");
        map.put("code", code);

        return map;
    }

    public static String getLoginUserUrl(String token) {
        String baseUrl = API_URL + "/users/self/";
        Map<String, String> map = new HashMap<>();

        map.put(EndPointKeys.ACCESS_TOKEN, token);

        return UrlUtils.buildUrlString(baseUrl, map);
    }

    public static String getRecentMediaUrl(String token, String userId, String maxId, String minId, int count) {
        StringBuilder baseUrlStringBuilder = new StringBuilder(API_URL);
        baseUrlStringBuilder.append("/users")
                .append(TextUtils.isEmpty(userId) ? "/self" : "/" + userId)
                .append("/media/recent/");

        Map<String, String> map = new HashMap<>();

        map.put(EndPointKeys.ACCESS_TOKEN, token);

        if (!TextUtils.isEmpty(maxId)) {
            map.put(EndPointKeys.MAX_ID, maxId);
        }

        if (!TextUtils.isEmpty(minId)) {
            map.put(EndPointKeys.MIN_ID, minId);
        }

        if (count > 0) {
            map.put(EndPointKeys.COUNT, String.valueOf(count));
        }

        return UrlUtils.buildUrlString(baseUrlStringBuilder.toString(), map);
    }

    public static IGUser getLoginUserFromAuthentication(JSONObject userObject) throws JSONException {
        if (userObject == null) {
            return null;
        }

        String id = JsonUtils.getStringFromJson(userObject, EndPointKeys.ID);
        String userName = JsonUtils.getStringFromJson(userObject, EndPointKeys.USER_NAME);
        String fullName = JsonUtils.getStringFromJson(userObject, EndPointKeys.FULL_NAME);
        String profilePicture = JsonUtils.getStringFromJson(userObject, EndPointKeys.PROFILE_PICTURE);

        return new IGUser(id, userName, fullName, profilePicture);
    }

    public static IGUser getLoginUserFromEndPoint(JSONObject dataObject) throws JSONException {
        if (dataObject == null) {
            return null;
        }

        String id = JsonUtils.getStringFromJson(dataObject, EndPointKeys.ID);
        String userName = JsonUtils.getStringFromJson(dataObject, EndPointKeys.USER_NAME);
        String fullName = JsonUtils.getStringFromJson(dataObject, EndPointKeys.FULL_NAME);
        String profilePicture = JsonUtils.getStringFromJson(dataObject, EndPointKeys.PROFILE_PICTURE);

        return new IGUser(id, userName, fullName, profilePicture);
    }
}