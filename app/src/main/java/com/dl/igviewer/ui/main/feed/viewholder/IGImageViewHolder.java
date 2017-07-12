package com.dl.igviewer.ui.main.feed.viewholder;

import android.view.View;
import android.widget.TextView;

import com.dl.igviewer.R;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.utility.view.DynamicHeightNetworkImageView;
import com.squareup.picasso.Picasso;

public class IGImageViewHolder extends BaseViewHolder {

    private DynamicHeightNetworkImageView mImageView;
    private TextView mLikeCountTextView;


    public IGImageViewHolder(View itemView) {
        super(itemView);
        findViews(itemView);
        setupViews();
    }

    @Override
    protected void findViews(View itemView) {
        mImageView = (DynamicHeightNetworkImageView) itemView.findViewById(R.id.image_view_feed_item_image);
        mLikeCountTextView = (TextView) itemView.findViewById(R.id.text_view_feed_item_like_count);
    }

    private void setupViews() {
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void bind(IGImage igImage) {
        bindImage(igImage);
        bindLikeCount(igImage);
    }

    private void bindImage(IGImage igImage) {
        Picasso.with(itemView.getContext())
                .load(igImage.getStandardUrl())
                .into(mImageView);

        mImageView.setAspectRatio(igImage.getRatio());
    }

    private void bindLikeCount(IGImage igImage) {
        StringBuilder sb = new StringBuilder();
        int likeCount = igImage.getLikeCount() < 0 ? 0 : igImage.getLikeCount();

        sb.append(likeCount).append(" ").append(itemView.getContext().getString(R.string.all_likes));

        mLikeCountTextView.setText(sb.toString());
    }
}
