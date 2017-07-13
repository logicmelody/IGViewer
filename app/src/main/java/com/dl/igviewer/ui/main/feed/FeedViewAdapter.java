package com.dl.igviewer.ui.main.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dl.igviewer.R;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.ui.main.feed.viewholder.BaseViewHolder;
import com.dl.igviewer.ui.main.feed.viewholder.IGImageViewHolder;
import com.dl.igviewer.ui.main.feed.viewholder.LoadMoreViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FeedViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public interface OnClickFeedItemListener {
        void onClickFeedItem(IGImage igImage);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private Context mContext;
    private List<FeedViewItem> mDataList;

    private OnLoadMoreListener mOnLoadMoreListener;
    private OnClickFeedItemListener mOnClickFeedItemListener;


    public static final class ViewType {
        public static final int IMAGE = 0;
        public static final int LOAD_MORE = 1;
    }

    public class FeedViewItem {

        public int viewType;
        public IGImage igImage;

        public FeedViewItem(int viewType, IGImage igImage) {
            this.viewType = viewType;
            this.igImage = igImage;
        }
    }

    public FeedViewAdapter(Context context,
                           OnLoadMoreListener onLoadMoreListener, OnClickFeedItemListener onClickFeedItemListener) {
        mContext = context;
        mOnLoadMoreListener = onLoadMoreListener;
        mOnClickFeedItemListener = onClickFeedItemListener;
        mDataList = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case ViewType.IMAGE:
                return new IGImageViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_feed, viewGroup, false),
                        mOnClickFeedItemListener);

            case ViewType.LOAD_MORE:
                return new LoadMoreViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_feed_load_more, viewGroup, false),
                        mOnLoadMoreListener);

            default:
                return new IGImageViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_feed, viewGroup, false),
                        mOnClickFeedItemListener);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        viewHolder.bind(mDataList.get(position).igImage);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).viewType;
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) lp;

            if (holder.getItemViewType() == ViewType.LOAD_MORE) {
                params.setFullSpan(true);

            } else {
                params.setFullSpan(false);
            }
        }
    }

    public void add(List<IGImage> imageList) {
        if (imageList.size() == 0) {
            return;
        }

        if (mDataList.size() == 0) {
            mDataList.add(new FeedViewItem(ViewType.LOAD_MORE, null));
        }

        for (IGImage igImage : imageList) {
            mDataList.add(mDataList.size() - 1, new FeedViewItem(ViewType.IMAGE, igImage));
        }

        notifyDataSetChanged();
    }

    public void removeLoadMoreButton() {
        mDataList.remove(mDataList.size() - 1);
        notifyDataSetChanged();
    }
}
