package com.dl.igviewer.utility.utils;

import android.content.Context;

import com.dl.igviewer.R;

/**
 * Created by logicmelody on 2017/7/13.
 */

public class GeneralUtils {

    public static String generateLikesString(Context context, int likeCount) {
        return new StringBuilder().append(likeCount < 0 ? 0 : likeCount).append(" ")
                                  .append(context.getString(R.string.all_likes)).toString();
    }
}
