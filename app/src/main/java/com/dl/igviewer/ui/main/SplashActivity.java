package com.dl.igviewer.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dl.igviewer.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
    }

    private void findViews() {

    }

    private void setupViews() {

    }
}
