package com.dl.igviewer.backgroundtask;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.dl.igviewer.datastructure.IGUser;
import com.dl.igviewer.ui.main.InstagramDataCache;
import com.dl.igviewer.utility.utils.HttpUtils;
import com.dl.igviewer.utility.utils.InstagramApiUtils;
import com.dl.igviewer.utility.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetLoginUserAsyncTask extends AsyncTask<Void, Void, IGUser> {

    public interface OnGetLoginUserListener {
        void onGetLoginUserSuccessful();
        void onGetLoginUserFailed(int errorCode);
    }

    private Context mContext;
    private OnGetLoginUserListener mOnGetLoginUserListener;

    private int mErrorCode;


    public GetLoginUserAsyncTask(Context context, OnGetLoginUserListener listener) {
        mContext = context;
        mOnGetLoginUserListener = listener;
    }

    @Override
    protected IGUser doInBackground(Void... params) {
        if (!HttpUtils.isConnectToInternet(mContext)) {
            mErrorCode = HttpUtils.ErrorCode.NO_CONNECTION;

            return null;
        }

        IGUser loginUser;
        String token = InstagramDataCache.getTokenFromSharedPreference(mContext);
        String jsonString;

        if (TextUtils.isEmpty(token)) {
            mErrorCode = HttpUtils.ErrorCode.GET_DATA_FAILED;

            return null;
        }

        try {
            jsonString = HttpUtils.getJsonStringFromUrl(InstagramApiUtils.getLoginUserUrl(token));

            if (TextUtils.isEmpty(jsonString)) {
                mErrorCode = HttpUtils.ErrorCode.GET_DATA_FAILED;

                return null;
            }

            JSONObject loginUserObject = new JSONObject(jsonString);
            JSONObject dataObject =
                    JsonUtils.getJsonObjectFromJson(loginUserObject, InstagramApiUtils.EndPointKeys.DATA);

            if (dataObject == null) {
                mErrorCode = HttpUtils.ErrorCode.GET_DATA_FAILED;

                return null;
            }

            loginUser = InstagramApiUtils.getLoginUserFromEndPoint(dataObject);
            InstagramDataCache.getInstance().setLoginUser(loginUser);

        } catch (IOException e) {
            e.printStackTrace();
            mErrorCode = HttpUtils.ErrorCode.GET_DATA_FAILED;
            loginUser = null;

        } catch (JSONException e) {
            e.printStackTrace();
            mErrorCode = HttpUtils.ErrorCode.GET_DATA_FAILED;
            loginUser = null;
        }

        return loginUser;
    }

    @Override
    protected void onPostExecute(IGUser igUser) {
        super.onPostExecute(igUser);

        if (igUser == null) {
            mOnGetLoginUserListener.onGetLoginUserFailed(mErrorCode);

        } else {
            mOnGetLoginUserListener.onGetLoginUserSuccessful();
        }
    }
}
