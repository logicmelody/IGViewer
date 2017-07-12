package com.dl.igviewer.datastructure;

public class IGImage {

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
