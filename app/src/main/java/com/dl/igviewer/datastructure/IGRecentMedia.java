package com.dl.igviewer.datastructure;

import java.util.List;

public class IGRecentMedia {

    private String mNextUrl;
    private boolean mIsFromRefresh;
    private List<IGImage> mImageList;



    public IGRecentMedia(String nextUrl, boolean isFromRefresh, List<IGImage> imageList) {
        mNextUrl = nextUrl;
        mIsFromRefresh = isFromRefresh;
        mImageList = imageList;
    }

    public String getNextUrl() {
        return mNextUrl;
    }

    public boolean getIsFromRefresh() {
        return mIsFromRefresh;
    }

    public List<IGImage> getImageList() {
        return mImageList;
    }
}
