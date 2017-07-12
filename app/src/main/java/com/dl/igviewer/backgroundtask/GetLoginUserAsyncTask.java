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
        void onGetLoginUserFailed();
    }

    private Context mContext;
    private OnGetLoginUserListener mOnGetLoginUserListener;


    public GetLoginUserAsyncTask(Context context, OnGetLoginUserListener listener) {
        mContext = context;
        mOnGetLoginUserListener = listener;
    }

    @Override
    protected IGUser doInBackground(Void... params) {
        IGUser loginUser;
        String token = InstagramDataCache.getTokenFromSharedPreference(mContext);
        String jsonString;

        if (TextUtils.isEmpty(token)) {
            return null;
        }

        try {
            jsonString = HttpUtils.getJsonStringFromUrl(InstagramApiUtils.getLoginUserUrl(token));

            if (TextUtils.isEmpty(jsonString)) {
                return null;
            }

            JSONObject loginUserObject = new JSONObject(jsonString);
            JSONObject dataObject =
                    JsonUtils.getJsonObjectFromJson(loginUserObject, InstagramApiUtils.EndPointKeys.DATA);

            if (dataObject == null) {
                return null;
            }

            loginUser = InstagramApiUtils.getLoginUserFromEndPoint(dataObject);
            InstagramDataCache.getInstance().setLoginUser(loginUser);

        } catch (IOException e) {
            e.printStackTrace();
            loginUser = null;

        } catch (JSONException e) {
            e.printStackTrace();
            loginUser = null;
        }

        return loginUser;
    }

    @Override
    protected void onPostExecute(IGUser igUser) {
        super.onPostExecute(igUser);

        if (igUser == null) {
            mOnGetLoginUserListener.onGetLoginUserFailed();

        } else {
            mOnGetLoginUserListener.onGetLoginUserSuccessful();
        }
    }
}
