package com.dl.igviewer.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dl.igviewer.R;
import com.dl.igviewer.backgroundtask.GetAuthenticationTokenAsyncTask;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener,
        GetAuthenticationTokenAsyncTask.OnGetAuthenticationTokenListener {

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

                    String code = data.getStringExtra(EXTRA_INSTAGRAM_CODE);

                    if (TextUtils.isEmpty(code)) {
                        break;
                    }

                    mLoginButton.setVisibility(View.GONE);

                    new GetAuthenticationTokenAsyncTask(SplashActivity.this, this).execute(code);

                } else if (RESULT_CANCELED == resultCode) {
                    mLoginButton.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    @Override
    public void onGetAuthenticationTokenSuccessful() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onGetAuthenticationTokenFailed() {
        mLoginButton.setVisibility(View.VISIBLE);
    }
}
