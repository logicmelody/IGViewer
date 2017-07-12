package com.dl.igviewer.datastructure;

import android.graphics.Bitmap;

/**
 * Created by logicmelody on 2017/7/10.
 */

public class IGUser {

    private String mId;
    private String mUserName;
    private String mFullName;
    private String mProfilePicture;

    private Bitmap mProfilePictureBitmap;


    public IGUser(String id, String userName, String fullName, String profilePicture) {
        mId = id;
        mUserName = userName;
        mFullName = fullName;
        mProfilePicture = profilePicture;
    }

    public String getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getProfilePicture() {
        return mProfilePicture;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Id = ").append(mId).append("\n")
                     .append("User name = ").append(mUserName).append("\n")
                     .append("Full name = ").append(mFullName).append("\n")
                     .append("Profile picture = ").append(mProfilePicture).append("\n");

        return stringBuilder.toString();
    }
}
