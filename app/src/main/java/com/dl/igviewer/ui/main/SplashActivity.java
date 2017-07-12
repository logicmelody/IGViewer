package com.dl.igviewer.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dl.igviewer.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mLoginButton;


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
        mLoginButton = (Button) findViewById(R.id.button_splash_screen_login);
    }

    private void setupViews() {
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_splash_screen_login:
                startActivity(new Intent(SplashActivity.this, InstagramLoginActivity.class));

                break;
        }
    }
}
