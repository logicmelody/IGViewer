package com.dl.igviewer.utility.utils;

import android.content.Context;
import android.widget.Toast;

import com.dl.igviewer.R;

/**
 * Created by logicmelody on 2017/7/13.
 */

public class GeneralUtils {

    public static String generateLikesString(Context context, int likeCount) {
        return new StringBuilder().append(likeCount < 0 ? 0 : likeCount).append(" ")
                                  .append(context.getString(R.string.all_likes)).toString();
    }

    public static void showConnectionErrorToast(Context context, int errorCode) {
        String toastText = context.getString(R.string.all_no_connection);

        switch (errorCode) {
            case HttpUtils.ErrorCode.NO_CONNECTION:
                toastText = context.getString(R.string.all_no_connection);

                break;

            case HttpUtils.ErrorCode.GET_DATA_FAILED:
                toastText = context.getString(R.string.all_get_data_failed);

                break;
        }

        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
    }
}
