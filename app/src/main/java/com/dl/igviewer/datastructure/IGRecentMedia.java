package com.dl.igviewer.datastructure;

import java.util.List;

public class IGRecentMedia {

    private String mNextMaxId;
    private List<IGImage> mImageList;


    public IGRecentMedia(String nextMaxId, List<IGImage> imageList) {
        mNextMaxId = nextMaxId;
        mImageList = imageList;
    }

    public String getNextMaxId() {
        return mNextMaxId;
    }

    public List<IGImage> getImageList() {
        return mImageList;
    }
}
