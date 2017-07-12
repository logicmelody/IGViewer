package com.dl.igviewer.ui.main.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dl.igviewer.R;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.ui.main.feed.viewholder.BaseViewHolder;
import com.dl.igviewer.ui.main.feed.viewholder.IGImageViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FeedViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<IGImage> mImageList;


    public FeedViewAdapter(Context context) {
        mContext = context;
        mImageList = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new IGImageViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_feed, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int i) {
        viewHolder.bind(mImageList.get(i));
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public void add(List<IGImage> imageList) {
        mImageList.addAll(imageList);
        notifyDataSetChanged();
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
