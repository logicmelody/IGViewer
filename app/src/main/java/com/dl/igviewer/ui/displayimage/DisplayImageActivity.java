package com.dl.igviewer.ui.displayimage;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.dl.igviewer.R;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.ui.main.InstagramDataCache;
import com.squareup.picasso.Picasso;

public class DisplayImageActivity extends AppCompatActivity {

    public static final String EXTRA_IG_IMAGE = "com.dl.igviewer.EXTRA_IG_IMAGE";

    private ImageView mImageView;

    private IGImage mIGImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        initialize();

        mIGImage = getIntent().getParcelableExtra(EXTRA_IG_IMAGE);

        Log.d("danny", mIGImage.getStandardUrl());
    }

    private void initialize() {
        findViews();
        loadImage();
        setupActionBar();
    }

    private void findViews() {
        mImageView = (ImageView) findViewById(R.id.image_view_display_image);
    }

    private void loadImage() {
        if (mIGImage == null) {
            return;
        }

        //Picasso.with(this).load(mIGImage.getStandardUrl()).into(mImageView);
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
}
