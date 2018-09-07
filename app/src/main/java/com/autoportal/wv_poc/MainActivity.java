package com.autoportal.wv_poc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String url = "https://autoportal.com/";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        // ATTENTION: This was auto-generated to handle app links.
        handleDeepLink();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleDeepLink();
    }

    private void handleDeepLink() {
        Intent appLinkIntent = getIntent();
        Uri appLinkData = appLinkIntent.getData();
        if (appLinkData == null) {
            Toast.makeText(this, "no url to handle", Toast.LENGTH_LONG).show();
        }else {
            url = appLinkData.toString();
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }


}
