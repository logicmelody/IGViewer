package com.dl.igviewer.utility.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

    private static final OkHttpClient sHttpClient = new OkHttpClient();


    public static String getJsonStringFromUrl(String url) throws IOException {
        Response response = sHttpClient.newCall(getRequest(url)).execute();

        return response.body().string();
    }

    public static String postJsonStringFromUrl(String url, Map<String, String> bodyMap) throws IOException {
        Response response = sHttpClient.newCall(postRequest(url, bodyMap)).execute();

        return response.body().string();
    }

    public static Bitmap downloadBitmap(String uri) {
        Response response = null;

        try {
            response = sHttpClient.newCall(getRequest(uri)).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response == null ? null : BitmapFactory.decodeStream(response.body().byteStream());
    }

    private static Request getRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    private static Request postRequest(String url, Map<String, String> bodyMap) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : bodyMap.entrySet()) {
            formBodyBuilder.add(entry.getKey(), entry.getValue());
        }

        return new Request.Builder()
                .url(url)
                .post(formBodyBuilder.build())
                .build();
    }
}
