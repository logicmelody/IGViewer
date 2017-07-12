package com.dl.igviewer.ui.main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dl.igviewer.R;
import com.dl.igviewer.backgroundtask.GetRecentMediaAsyncTask;
import com.dl.igviewer.datastructure.IGRecentMedia;
import com.dl.igviewer.ui.login.SplashActivity;
import com.dl.igviewer.ui.main.feed.FeedViewAdapter;
import com.dl.igviewer.ui.profile.ProfileActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GetRecentMediaAsyncTask.OnGetRecentMediaListener {

    private static final int REQUEST_PROFILE = 3;

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
        setupViews();
        setupActionBar();
        setupFeedView();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mLoginUserAvatarView = (CircleImageView) findViewById(R.id.circle_image_view_main_login_user_avatar);
        mFeedView = (RecyclerView) findViewById(R.id.recycler_view_main_feed);
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
}
