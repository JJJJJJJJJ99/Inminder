package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button rem_button = (Button) findViewById(R.id.reminder_button);
        Button bill_button = (Button) findViewById(R.id.bill_button);
        Button pass_button = (Button) findViewById(R.id.password_button);

        rem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("edu.brandeis.jjwang95.inminder.Reminder"));
            }
        });

        bill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("edu.brandeis.jjwang95.inminder.Bill"));
            }
        });

        pass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("edu.brandeis.jjwang95.inminder.Password"));
            }
        });



    }
}
