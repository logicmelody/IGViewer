package com.dl.igviewer.ui.profile;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dl.igviewer.R;
import com.dl.igviewer.ui.main.InstagramDataCache;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView mLoginUserAvatarView;
    private TextView mLoginUserTextView;

    private Button mLogoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupActionBar();
    }

    private void findViews() {
        mLoginUserAvatarView = (CircleImageView) findViewById(R.id.circle_image_view_profile_login_user_avatar);
        mLoginUserTextView = (TextView) findViewById(R.id.text_view_profile_login_user_name);
        mLogoutButton = (Button) findViewById(R.id.button_profile_logout);
    }

    private void setupViews() {
        Picasso.with(this).load(InstagramDataCache.getInstance().getLoginUser().getProfilePicture())
                .placeholder(R.drawable.ic_login_user_avatar_placeholder)
                .into(mLoginUserAvatarView);

        mLoginUserTextView.setText(InstagramDataCache.getInstance().getLoginUser().getFullName());

        mLogoutButton.setOnClickListener(this);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_profile_logout:
                setResult(RESULT_OK);
                finish();

                break;
        }
    }
}
