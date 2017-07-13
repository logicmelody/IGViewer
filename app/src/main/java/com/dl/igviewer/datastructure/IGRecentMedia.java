package com.dl.igviewer.datastructure;

import java.util.List;

public class IGRecentMedia {

    private String mNextUrl;
    private List<IGImage> mImageList;


    public IGRecentMedia(String nextUrl, List<IGImage> imageList) {
        mNextUrl = nextUrl;
        mImageList = imageList;
    }

    public String getNextUrl() {
        return mNextUrl;
    }

    public List<IGImage> getImageList() {
        return mImageList;
    }
}
