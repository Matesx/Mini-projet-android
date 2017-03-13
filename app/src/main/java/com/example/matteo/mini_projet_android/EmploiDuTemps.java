package com.example.matteo.mini_projet_android;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Matteo on 13/03/2017.
 */

public class EmploiDuTemps extends Activity {

    private String url = "https://edt.univ-tlse3.fr/FSI/FSImentionM/Info/g31090.html";
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
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }




}
