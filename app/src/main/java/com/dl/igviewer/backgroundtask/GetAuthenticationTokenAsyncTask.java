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

public class GetAuthenticationTokenAsyncTask extends AsyncTask<String, Void, String> {

    public interface OnGetAuthenticationTokenListener {
        void onGetAuthenticationTokenSuccessful();
        void onGetAuthenticationTokenFailed();
    }

    private Context mContext;
    private OnGetAuthenticationTokenListener mOnGetAuthenticationTokenListener;


    public GetAuthenticationTokenAsyncTask(Context context, OnGetAuthenticationTokenListener listener) {
        mContext = context;
        mOnGetAuthenticationTokenListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonString;
        String token;

        try {
            jsonString = HttpUtils.postJsonStringFromUrl(InstagramApiUtils.getTokenUrl(),
                                                         InstagramApiUtils.getTokenBodyMap(mContext, params[0]));

            JSONObject authenticationObject = new JSONObject(jsonString);
            token = JsonUtils.getStringFromJson(authenticationObject, InstagramApiUtils.EndPointKeys.ACCESS_TOKEN);

            if (TextUtils.isEmpty(token)) {
                return "";
            }

            IGUser loginUser = InstagramApiUtils
                    .getLoginUserFromAuthentication(authenticationObject.getJSONObject(InstagramApiUtils.EndPointKeys.USER));

            if (loginUser == null) {
                return "";
            }

            saveTokenAndLoginUser(token, loginUser);

        } catch (IOException e) {
            e.printStackTrace();
            token = "";

        } catch (JSONException e) {
            e.printStackTrace();
            token = "";
        }

        return token;
    }

    private void saveTokenAndLoginUser(String token, IGUser loginUser) {
        InstagramDataCache.saveTokenToSharedPreference(mContext, token);
        InstagramDataCache.getInstance().setLoginUser(loginUser);
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);

        if (TextUtils.isEmpty(token)) {
            mOnGetAuthenticationTokenListener.onGetAuthenticationTokenFailed();

        } else {
            mOnGetAuthenticationTokenListener.onGetAuthenticationTokenSuccessful();
        }
    }
}
