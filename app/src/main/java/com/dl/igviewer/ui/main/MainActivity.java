package com.dl.igviewer.ui.main;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.dl.igviewer.R;
import com.dl.igviewer.backgroundtask.GetRecentMediaAsyncTask;
import com.dl.igviewer.datastructure.IGRecentMedia;
import com.dl.igviewer.ui.main.feed.FeedViewAdapter;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements GetRecentMediaAsyncTask.OnGetRecentMediaListener {

    private Toolbar mToolbar;

    private CircleImageView mLoginUserAvatarView;

    private RecyclerView mFeedView;
    private FeedViewAdapter mFeedViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        new GetRecentMediaAsyncTask(this, this).execute();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupFeedView();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mLoginUserAvatarView = (CircleImageView) findViewById(R.id.circle_image_view_main_login_user_avatar);
        mFeedView = (RecyclerView) findViewById(R.id.recycler_view_main_feed);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);

            Picasso.with(this).load(InstagramDataCache.getInstance().getLoginUser().getProfilePicture())
                              .placeholder(R.drawable.ic_login_user_avatar_placeholder)
                              .into(mLoginUserAvatarView);
        }
    }

    private void setupFeedView() {
        mFeedViewAdapter = new FeedViewAdapter(this);

        mFeedView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mFeedView.setAdapter(mFeedViewAdapter);
    }

    @Override
    public void onGetRecentMediaSuccessful(IGRecentMedia igRecentMedia) {
        if (igRecentMedia.getImageList() == null || igRecentMedia.getImageList().size() == 0) {
            return;
        }

        mFeedViewAdapter.add(igRecentMedia.getImageList());
    }

    @Override
    public void onGetRecentMediaFailed() {

    }
}
