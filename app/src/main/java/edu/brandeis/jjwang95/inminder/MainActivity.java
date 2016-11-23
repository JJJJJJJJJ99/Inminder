package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.beardedhen.androidbootstrap.BootstrapButton;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);
        BootstrapButton rem_button = (BootstrapButton) findViewById(R.id.reminder_button);
        BootstrapButton bill_button = (BootstrapButton) findViewById(R.id.bill_button);
        BootstrapButton pass_button = (BootstrapButton) findViewById(R.id.password_button);

        /*********************Test*******************************************************/
//        Log.d("Start", "SHOW");
        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);
//        Log.d("Create dbHelper", "Created");
//        PasswordObject testPassword = new PasswordObject("lalalala", "1111");
//        long testPassword_id = dbHelper.createPassword(testPassword);

//        testPassword.setId(testPassword_id);
        //Log.d("Added one password", "Password count" + dbHelper.getAllPasswords().size());
//        dbHelper.deleteAllPassword();
        //Log.d("Delete All", "Password count" + dbHelper.getAllPasswords().size());
//
//        dbHelper.closeDB();
        /*********************Test*******************************************************/

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
