package com.dl.igviewer.datastructure;

import android.os.Parcel;
import android.os.Parcelable;

public class IGImage implements Parcelable {

    private String mId;
    private String mThumbnailUrl;
    private String mStandardUrl;

    private float mRatio = 1F;

    private long mCreatedTime;
    private int mLikeCount;


    public IGImage(String id, String thumbnailUrl, String standardUrl,
                   float ratio, long createdTime, int likeCount) {
        mId = id;
        mThumbnailUrl = thumbnailUrl;
        mStandardUrl = standardUrl;
        mRatio = ratio;
        mCreatedTime = createdTime;
        mLikeCount = likeCount;
    }

    protected IGImage(Parcel in) {
        mId = in.readString();
        mThumbnailUrl = in.readString();
        mStandardUrl = in.readString();
        mRatio = in.readFloat();
        mCreatedTime = in.readLong();
        mLikeCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mThumbnailUrl);
        dest.writeString(mStandardUrl);
        dest.writeFloat(mRatio);
        dest.writeLong(mCreatedTime);
        dest.writeInt(mLikeCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IGImage> CREATOR = new Creator<IGImage>() {
        @Override
        public IGImage createFromParcel(Parcel in) {
            return new IGImage(in);
        }

        @Override
        public IGImage[] newArray(int size) {
            return new IGImage[size];
        }
    };

    public String getId() {
        return mId;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getStandardUrl() {
        return mStandardUrl;
    }

    public long getCreatedTime() {
        return mCreatedTime;
    }

    public int getLikeCount() {
        return mLikeCount;
    }

    public float getRatio() {
        return mRatio;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IGImage").append("\n")
                     .append("Id = ").append(mId).append("\n")
                     .append("ThumbnailUrl = ").append(mThumbnailUrl).append("\n")
                     .append("StandardUrl = ").append(mStandardUrl).append("\n")
                     .append("Ratio = ").append(mRatio).append("\n")
                     .append("CreatedTime = ").append(mCreatedTime).append("\n")
                     .append("LikeCount = ").append(mLikeCount).append("\n");

        return stringBuilder.toString();
    }
}
