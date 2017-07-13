package com.dl.igviewer.ui.main;

import android.content.Context;
import android.preference.PreferenceManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;

import com.dl.igviewer.datastructure.IGUser;

public class InstagramDataCache {

    private static final String PREFERENCE_TOKEN = "com.dl.igviewer.PREFERENCE_TOKEN";

    private volatile static InstagramDataCache sInstagramDataCache = null;

    private String mToken;

    private IGUser mLoginUser;


    private InstagramDataCache() {}

    public static InstagramDataCache getInstance() {
        if (sInstagramDataCache == null) {
            synchronized (InstagramDataCache.class) {
                if (sInstagramDataCache == null) {
                    sInstagramDataCache = new InstagramDataCache();
                }
            }
        }

        return sInstagramDataCache;
    }

    public static void saveTokenToSharedPreference(Context context, String token) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                         .putString(PREFERENCE_TOKEN, token).apply();
    }

    public static String getTokenFromSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERENCE_TOKEN, "");
    }

    public static boolean hasTokenInSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(PREFERENCE_TOKEN);
    }

    public void setLoginUser(IGUser loginUser) {
        mLoginUser = loginUser;
    }

    public IGUser getLoginUser() {
        return mLoginUser;
    }

    public void release(Context context) {
        CookieManager.getInstance().removeAllCookie();
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(PREFERENCE_TOKEN).apply();

        mToken = null;
        mLoginUser = null;
    }
}
