package com.dl.igviewer.backgroundtask;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.datastructure.IGRecentMedia;
import com.dl.igviewer.ui.main.InstagramDataCache;
import com.dl.igviewer.utility.utils.HttpUtils;
import com.dl.igviewer.utility.utils.InstagramApiUtils;
import com.dl.igviewer.utility.utils.InstagramApiUtils.EndPointKeys;
import com.dl.igviewer.utility.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetRecentMediaAsyncTask extends AsyncTask<Void, Void, IGRecentMedia> {

    private static final int DEFAULT_COUNT = 10;

    public interface OnGetRecentMediaListener {
        void onGetRecentMediaSuccessful(IGRecentMedia igRecentMedia);
        void onGetRecentMediaFailed();
    }

    private Context mContext;
    private OnGetRecentMediaListener mOnGetRecentMediaListener;

    private String mUserId;
    private String mUrl;


    public GetRecentMediaAsyncTask(Context context, OnGetRecentMediaListener listener) {
        mContext = context;
        mOnGetRecentMediaListener = listener;
    }

    public GetRecentMediaAsyncTask(Context context, OnGetRecentMediaListener listener, String url) {
        mContext = context;
        mOnGetRecentMediaListener = listener;
        mUrl = url;
    }

    @Override
    protected IGRecentMedia doInBackground(Void... params) {
        String getRecentMediaUrl = TextUtils.isEmpty(mUrl) ?
                InstagramApiUtils.getRecentMediaUrl(InstagramDataCache.getTokenFromSharedPreference(mContext),
                                                    mUserId, "", "", DEFAULT_COUNT) : mUrl;
        List<IGImage> imageList;
        String jsonString;
        String nextUrl;

        try {
            jsonString = HttpUtils.getJsonStringFromUrl(getRecentMediaUrl);
            JSONObject jsonObject = new JSONObject(jsonString);

            if (JsonUtils.getJsonObjectFromJson(jsonObject, EndPointKeys.PAGINATION) == null) {
                return null;
            }

            imageList = new ArrayList<>();
            nextUrl = JsonUtils.getStringFromJson(
                    JsonUtils.getJsonObjectFromJson(jsonObject, EndPointKeys.PAGINATION),
                                                    EndPointKeys.NEXT_URL);

            JSONArray dataJsonArray = JsonUtils.getJsonArrayFromJson(jsonObject, EndPointKeys.DATA);

            if (dataJsonArray == null) {
                return null;
            }

            for (int i = 0 ; i < dataJsonArray.length() ; i++) {
                JSONObject dataJson = dataJsonArray.getJSONObject(i);

                if (!JsonUtils.getStringFromJson(dataJson, EndPointKeys.TYPE).equals(EndPointKeys.IMAGE)) {
                    continue;
                }

                JSONObject imageJson = JsonUtils.getJsonObjectFromJson(dataJson, EndPointKeys.IMAGES);

                if (imageJson == null) {
                    continue;
                }

                JSONObject thumbnailObject = JsonUtils.getJsonObjectFromJson(imageJson, EndPointKeys.THUMBNAIL);
                JSONObject standardObject = JsonUtils.getJsonObjectFromJson(imageJson, EndPointKeys.STANDARD_RESOLUTION);
                JSONObject captionObject = JsonUtils.getJsonObjectFromJson(dataJson, EndPointKeys.CAPTION);

                String id = JsonUtils.getStringFromJson(dataJson, EndPointKeys.ID);
                String thumbnailUrl = JsonUtils.getStringFromJson(thumbnailObject, EndPointKeys.URL);
                String standardUrl = JsonUtils.getStringFromJson(standardObject, EndPointKeys.URL);
                String text = JsonUtils.getStringFromJson(captionObject, EndPointKeys.TEXT);

                int width = JsonUtils.getIntFromJson(standardObject, EndPointKeys.WIDTH);
                int height = JsonUtils.getIntFromJson(standardObject, EndPointKeys.HEIGHT);
                float ratio = (float) width / (float) height;
                long createdTime = JsonUtils.getLongFromJson(dataJson, EndPointKeys.CREATED_TIME);
                int likeCount = dataJson.getJSONObject(EndPointKeys.LIKES).getInt(EndPointKeys.COUNT);

                imageList.add(new IGImage(id, thumbnailUrl, standardUrl, text, ratio, createdTime, likeCount));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return new IGRecentMedia(nextUrl, imageList);
    }

    @Override
    protected void onPostExecute(IGRecentMedia igRecentMedia) {
        super.onPostExecute(igRecentMedia);

        if (igRecentMedia == null) {
            mOnGetRecentMediaListener.onGetRecentMediaFailed();

        } else {
            mOnGetRecentMediaListener.onGetRecentMediaSuccessful(igRecentMedia);
        }
    }
}