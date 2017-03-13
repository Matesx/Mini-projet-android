package com.example.matteo.mini_projet_android;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Matteo on 13/03/2017.
 */

public class EmploiDuTemps extends Activity {

    private static final String LOG_TAG = "Log : ";
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";
    private String url = "https://edt.univ-tlse3.fr/FSI/FSImentionM/Info/g31090.html";
    private String pageWeb;
    private WebView webView;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emploi_du_temps);



        webView = (WebView) findViewById(R.id.WebView1);

        traitement();


    }

    public void traitement(){
        // set web view client
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url); // load the url
            return true;
        }
    }




}
