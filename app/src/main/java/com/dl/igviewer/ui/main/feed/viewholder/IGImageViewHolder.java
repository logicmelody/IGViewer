package com.dl.igviewer.ui.main.feed.viewholder;

import android.view.View;
import android.widget.TextView;

import com.dl.igviewer.R;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.ui.main.feed.FeedViewAdapter.OnClickFeedItemListener;
import com.dl.igviewer.utility.utils.GeneralUtils;
import com.dl.igviewer.utility.view.DynamicHeightNetworkImageView;
import com.squareup.picasso.Picasso;

public class IGImageViewHolder extends BaseViewHolder {

    private DynamicHeightNetworkImageView mImageView;
    private TextView mLikeCountTextView;
    private TextView mText;

    private OnClickFeedItemListener mOnClickFeedItemListener;


    public IGImageViewHolder(View itemView, OnClickFeedItemListener listener) {
        super(itemView);
        findViews(itemView);
        mOnClickFeedItemListener = listener;
    }

    @Override
    protected void findViews(View itemView) {
        mImageView = (DynamicHeightNetworkImageView) itemView.findViewById(R.id.image_view_feed_item_image);
        mLikeCountTextView = (TextView) itemView.findViewById(R.id.text_view_feed_item_like_count);
        mText = (TextView) itemView.findViewById(R.id.text_view_feed_item_text);
    }

    @Override
    public void bind(final IGImage igImage) {
        if (igImage == null) {
            return;
        }

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickFeedItemListener == null) {
                    return;
                }

                mOnClickFeedItemListener.onClickFeedItem(igImage);
            }
        });

        bindImage(igImage);
        bindLikeCount(igImage);
        bindText(igImage);
    }

    private void bindImage(IGImage igImage) {
        mImageView.setAspectRatio(igImage.getRatio());

        Picasso.with(itemView.getContext())
                .load(igImage.getStandardUrl())
                .into(mImageView);
    }

    private void bindLikeCount(IGImage igImage) {
        mLikeCountTextView.setText(GeneralUtils.generateLikesString(itemView.getContext(), igImage.getLikeCount()));
    }

    private void bindText(IGImage igImage) {
        mText.setText(igImage.getText());
    }
}
