package com.dl.igviewer.ui.main.feed.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dl.igviewer.datastructure.IGImage;

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {

    abstract protected void findViews(View itemView);
    abstract public void bind(IGImage igImage);

    protected View mRootView;


    public BaseViewHolder(View itemView) {
        super(itemView);
        mRootView = itemView;
        findViews(itemView);
    }
}
