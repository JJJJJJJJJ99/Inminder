package edu.brandeis.jjwang95.inminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.widget.ListView;

public class Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        SearchView search = new SearchView(getApplicationContext());
        ListView list = new ListView(getApplicationContext());

    }
}
