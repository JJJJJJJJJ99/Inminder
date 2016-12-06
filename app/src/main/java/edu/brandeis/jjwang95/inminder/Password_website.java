package edu.brandeis.jjwang95.inminder;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Password_website extends AppCompatActivity {
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_website);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.actionBarTop);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        myToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Go To Website");
        web = (WebView) findViewById(R.id.password_web);
        web.setWebViewClient(new Callback());
        web.loadUrl(getIntent().getStringExtra("site"));
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
        return true;
    }

}
