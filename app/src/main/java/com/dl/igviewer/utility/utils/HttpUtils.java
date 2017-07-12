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


    /**
     * 記得要在background thread執行
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getJsonStringFromUrl(String url) throws IOException {
        Response response = sHttpClient.newCall(getRequest(url)).execute();

        // 可以在Response的部分加上callback，但要注意的是，callback的method都是在background thread執行
//        Call call = sHttpClient.newCall(getRequest(url));
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                //timeout或 沒有網路
//                //注意！這裏是background thread
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                //成功
//                //注意！這裏是background thread
//            }
//        });

        return response.body().string();
    }

    public static String postJsonStringFromUrl(String url, Map<String, String> bodyMap) throws IOException {
        Response response = sHttpClient.newCall(postRequest(url, bodyMap)).execute();

        return response.body().string();
    }

    /**
     * 記得要在background thread執行
     *
     * @param uri
     * @return
     */
    public static Bitmap downloadBitmap(String uri) {
        Response response = null;

        try {
            response = sHttpClient.newCall(getRequest(uri)).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response == null ? null : BitmapFactory.decodeStream(response.body().byteStream());
    }

    /**
     * Get request
     * 只需要提供URL即可
     *
     * @param url
     * @return
     */
    private static Request getRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    /**
     * Post request
     * 除了基本的URL之外，還需要提供body的參數
     *
     * @param url
     * @param bodyMap
     * @return
     */
    private static Request postRequest(String url, Map<String, String> bodyMap) {
        // 用FormBody.Builder來產生post request需要傳遞的body
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
