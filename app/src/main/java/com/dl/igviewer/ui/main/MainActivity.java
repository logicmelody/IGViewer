package com.dl.igviewer.ui.main;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dl.igviewer.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private CircleImageView mLoginUserAvatarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
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
}
