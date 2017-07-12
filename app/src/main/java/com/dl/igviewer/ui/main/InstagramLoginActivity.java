package com.dl.igviewer.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dl.igviewer.R;
import com.dl.igviewer.utility.utils.InstagramApiUtils;

public class InstagramLoginActivity extends AppCompatActivity {

    private WebView mLoginWebView;


    private class AuthWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(getString(R.string.instagram_client_redirect_uri))) {
                String parts[] = url.split("=");

                sendCodeBack(parts[1]);
            }

            return false;
        }

        private void sendCodeBack(String code) {
            Intent intent = new Intent();
            //intent.putExtra(InstagramMainActivity.EXTRA_INSTAGRAM_CODE, code);

            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_login);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupLoginWebView();
    }

    private void findViews() {
        mLoginWebView = (WebView) findViewById(R.id.web_view_instagram_login);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.all_login));
        }
    }

    private void setupLoginWebView() {
        mLoginWebView.setVerticalScrollBarEnabled(false);
        mLoginWebView.setHorizontalScrollBarEnabled(false);
        mLoginWebView.setWebViewClient(new AuthWebViewClient());
        mLoginWebView.getSettings().setJavaScriptEnabled(true);
        mLoginWebView.loadUrl(InstagramApiUtils.getAuthorizationUrl(this));
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
