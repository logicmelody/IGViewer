package com.dl.igviewer.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dl.igviewer.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_INSTAGRAM_CODE = "com.dl.dlexerciseandroid.EXTRA_INSTAGRAM_CODE";

    private static final int REQUEST_INSTAGRAM_LOGIN = 1;

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
                startActivityForResult(new Intent(SplashActivity.this, InstagramLoginActivity.class), REQUEST_INSTAGRAM_LOGIN);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_INSTAGRAM_LOGIN:
                if (RESULT_OK == resultCode) {
                    if (data == null) {
                        break;
                    }

                    mLoginButton.setVisibility(View.GONE);

                    String code = data.getStringExtra(EXTRA_INSTAGRAM_CODE);

                    Log.d("danny", "Instagram code = " + code);

                } else if (RESULT_CANCELED == resultCode) {
                    mLoginButton.setVisibility(View.VISIBLE);
                }

                break;
        }
    }
}
