package com.dl.igviewer.ui.main.feed.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dl.igviewer.R;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.ui.main.feed.FeedViewAdapter.OnLoadMoreListener;
import com.dl.igviewer.utility.utils.GeneralUtils;
import com.dl.igviewer.utility.utils.HttpUtils;

/**
 * Created by logicmelody on 2017/7/13.
 */

public class LoadMoreViewHolder extends BaseViewHolder {

    private OnLoadMoreListener mOnLoadMoreListener;

    private Button mLoadMoreButton;
    private ProgressBar mProgressBar;


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

                if (!HttpUtils.isConnectToInternet(itemView.getContext())) {
                    GeneralUtils.showConnectionErrorToast(itemView.getContext(), HttpUtils.ErrorCode.NO_CONNECTION);

                    return;
                }

                showProgressBar(true);
                mOnLoadMoreListener.onLoadMore();
            }
        });
    }

    private void showProgressBar(boolean isShow) {
        if (isShow) {
            mLoadMoreButton.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);

        } else {
            mLoadMoreButton.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void findViews(View itemView) {
        mLoadMoreButton = (Button) itemView.findViewById(R.id.button_feed_item_load_more);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar_feed_item_load_more);
    }

    @Override
    public void bind(IGImage igImage) {
        showProgressBar(false);
    }
}
