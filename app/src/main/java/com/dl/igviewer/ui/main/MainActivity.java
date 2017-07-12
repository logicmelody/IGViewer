package com.dl.igviewer.ui.main;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.dl.igviewer.R;
import com.dl.igviewer.backgroundtask.GetRecentMediaAsyncTask;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.datastructure.IGRecentMedia;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements GetRecentMediaAsyncTask.OnGetRecentMediaListener {

    private Toolbar mToolbar;

    private CircleImageView mLoginUserAvatarView;


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
        setupViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mLoginUserAvatarView = (CircleImageView) findViewById(R.id.circle_image_view_main_login_user_avatar);
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

    private void setupViews() {

    }

    @Override
    public void onGetRecentMediaSuccessful(IGRecentMedia igRecentMedia) {
        for (IGImage igImage : igRecentMedia.getImageList()) {
            Log.d("danny", igImage.toString());
        }
    }

    @Override
    public void onGetRecentMediaFailed() {

    }
}
