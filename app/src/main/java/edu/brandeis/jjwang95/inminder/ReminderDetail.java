package edu.brandeis.jjwang95.inminder;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;

public class ReminderDetail extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    TextView name, notes, time;
    Button update, delete;
    ReminderObject curr;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);

        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);

        name = (TextView) findViewById(R.id.nameDetail);
        notes = (TextView) findViewById(R.id.noteDetail);
        time = (TextView) findViewById(R.id.timeDetail);
        update = (Button) findViewById(R.id.updateBtn);
        delete = (Button) findViewById(R.id.deleteBtn);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b != null){
            id = (int)b.get("id");
        }

        curr = dbHelper.getReminder(id);

        name.setText(curr.getName());
        notes.setText(curr.getNote());
        time.setText(curr.getTime());

        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                dbHelper.deleteReminder((long) id);
                Intent data = new Intent();
                setResult(RESULT_OK,data);
                finish();
            }
        });


    }
}
