package com.dl.igviewer.ui.main;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dl.igviewer.R;
import com.dl.igviewer.backgroundtask.GetRecentMediaAsyncTask;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.datastructure.IGRecentMedia;
import com.dl.igviewer.ui.displayimage.DisplayImageActivity;
import com.dl.igviewer.ui.login.SplashActivity;
import com.dl.igviewer.ui.main.feed.FeedViewAdapter;
import com.dl.igviewer.ui.profile.ProfileActivity;
import com.dl.igviewer.utility.utils.GeneralUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GetRecentMediaAsyncTask.OnGetRecentMediaListener, FeedViewAdapter.OnLoadMoreListener,
        FeedViewAdapter.OnClickFeedItemListener {

    private static final int REQUEST_PROFILE = 3;

    private Toolbar mToolbar;

    private CircleImageView mLoginUserAvatarView;
    private TextView mNoPhotosTextView;

    private RecyclerView mFeedView;
    private FeedViewAdapter mFeedViewAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private String mNextUrl;

    private boolean mIsFirstLaunch = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        new GetRecentMediaAsyncTask(this, this).execute();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupActionBar();
        setupFeedView();
        setupSwipeRefresh();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mLoginUserAvatarView = (CircleImageView) findViewById(R.id.circle_image_view_main_login_user_avatar);
        mFeedView = (RecyclerView) findViewById(R.id.recycler_view_main_feed);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_main_feed);
        mNoPhotosTextView = (TextView) findViewById(R.id.text_view_main_no_photos);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_main_feed);
    }

    private void setupViews() {
        mLoginUserAvatarView.setOnClickListener(this);
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
        mFeedViewAdapter = new FeedViewAdapter(this, this, this);

        mFeedView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mFeedView.setAdapter(mFeedViewAdapter);
    }

    private void setupSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mFeedViewAdapter.clear();
                new GetRecentMediaAsyncTask(MainActivity.this, MainActivity.this).execute();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onGetRecentMediaSuccessful(IGRecentMedia igRecentMedia) {
        updateLoadingUi();

        if (igRecentMedia.getImageList() == null || igRecentMedia.getImageList().size() == 0) {
            return;
        }

        mFeedViewAdapter.add(igRecentMedia.getImageList());
        mNoPhotosTextView.setVisibility(mFeedViewAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);

        if (TextUtils.isEmpty(igRecentMedia.getNextUrl())) {
            mFeedViewAdapter.removeLoadMoreButton();

        } else {
            mNextUrl = igRecentMedia.getNextUrl();
        }
    }

    private void updateLoadingUi() {
        if (mIsFirstLaunch) {
            mProgressBar.setVisibility(View.GONE);
            mIsFirstLaunch = false;

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onGetRecentMediaFailed(int errorCode) {
        updateLoadingUi();
        GeneralUtils.showConnectionErrorToast(this, errorCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circle_image_view_main_login_user_avatar:
                startActivityForResult(new Intent(MainActivity.this, ProfileActivity.class), REQUEST_PROFILE);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_PROFILE:
                if (RESULT_OK == resultCode) {
                    InstagramDataCache.getInstance().release(this);
                    restartLoginPage();
                }

                break;
        }
    }

    private void restartLoginPage() {
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    @Override
    public void onLoadMore() {
        new GetRecentMediaAsyncTask(this, this, mNextUrl).execute();
    }

    @Override
    public void onClickFeedItem(IGImage igImage) {
        if (igImage == null) {
            return;
        }

        displayImage(igImage);
    }

    private void displayImage(IGImage igImage) {
        Intent intent = new Intent(this, DisplayImageActivity.class);
        intent.putExtra(DisplayImageActivity.EXTRA_IG_IMAGE, igImage);

        startActivity(intent);
    }
}
