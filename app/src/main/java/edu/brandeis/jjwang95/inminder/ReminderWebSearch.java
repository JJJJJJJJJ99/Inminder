package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ReminderWebSearch extends AppCompatActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_web_search);
        webview = (WebView) findViewById(R.id.reminder_webview);

//        Intent intent = getIntent();
//        String webpage = intent.getExtras().getString("keyword");
//        Log.e("web check", webpage);
//        String[] keywordList = keywords.split(" ");
//        String webpage = "https://www.google.com/#q=gg";
//        for (String s : keywordList){
//            webpage = webpage + s + "+";
//        }
        webview.setWebViewClient(new Callback());
        webview.loadUrl("http://www.google.com");

    }
    private class Callback extends WebViewClient {
        public boolean shouldOverrideURLLoading(WebView view){
            return (false);
        }

    }
}
