package edu.brandeis.jjwang95.inminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Password_website extends AppCompatActivity {
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_website);

        web = (WebView) findViewById(R.id.password_web);
        web.setWebViewClient(new Callback());

        String url = getIntent().getStringExtra("site");
        if (URLUtil.isValidUrl(url)) {
            web.loadUrl(url);
        } else {
            Toast.makeText(getApplicationContext(), "Not a valid website!", Toast.LENGTH_LONG).show();

        }


    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
