package com.dl.igviewer.ui.displayimage;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dl.igviewer.R;
import com.dl.igviewer.datastructure.IGImage;
import com.dl.igviewer.utility.view.DynamicHeightNetworkImageView;
import com.squareup.picasso.Picasso;

public class DisplayImageActivity extends AppCompatActivity {

    public static final String EXTRA_IG_IMAGE = "com.dl.igviewer.EXTRA_IG_IMAGE";

    private DynamicHeightNetworkImageView mImageView;

    private IGImage mIGImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        mIGImage = getIntent().getParcelableExtra(EXTRA_IG_IMAGE);

        initialize();
    }

    private void initialize() {
        findViews();
        loadImage();
        setupActionBar();
    }

    private void findViews() {
        mImageView = (DynamicHeightNetworkImageView) findViewById(R.id.image_view_display_image);
    }

    private void loadImage() {
        if (mIGImage == null) {
            return;
        }

        mImageView.setAspectRatio(mIGImage.getRatio());
        Picasso.with(this).load(mIGImage.getStandardUrl()).into(mImageView);
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
