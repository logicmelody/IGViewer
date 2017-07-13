package com.dl.igviewer.ui.main.feed.viewholder;

import android.view.View;
import android.widget.Button;

import com.dl.igviewer.R;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.ui.main.feed.FeedViewAdapter.OnLoadMoreListener;

/**
 * Created by logicmelody on 2017/7/13.
 */

public class LoadMoreViewHolder extends BaseViewHolder {

    private OnLoadMoreListener mOnLoadMoreListener;

    private Button mLoadMoreButton;


    public LoadMoreViewHolder(View itemView, OnLoadMoreListener listener) {
        super(itemView);
        mOnLoadMoreListener = listener;
        setupViews();
    }

    private void setupViews() {
        mLoadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnLoadMoreListener == null) {
                    return;
                }

                mOnLoadMoreListener.onLoadMore();
            }
        });
    }

    @Override
    protected void findViews(View itemView) {
        mLoadMoreButton = (Button) itemView.findViewById(R.id.button_feed_item_load_more);
    }

    @Override
    public void bind(IGImage igImage) {

    }
}
