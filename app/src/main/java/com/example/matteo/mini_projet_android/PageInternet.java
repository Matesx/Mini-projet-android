package com.example.matteo.mini_projet_android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.WebViewClient;


/**
 * Created by Matteo on 13/03/2017.
 */

public class PageInternet extends Activity {

    private String url;
    private android.webkit.WebView webView;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.page_internet);
        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");
        webView = (android.webkit.WebView) findViewById(R.id.WebView1);

        traitement();

    }

    public void traitement(){
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }




}
