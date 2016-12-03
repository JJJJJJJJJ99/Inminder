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
    Button update, delete,cancel;
    ReminderObject curr;
    int id, request_Code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);

        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);

        name = (TextView) findViewById(R.id.nameDetail);
        notes = (TextView) findViewById(R.id.noteDetail);
        time = (TextView) findViewById(R.id.timeDetail);
        update = (Button) findViewById(R.id.re_detail_edit);
        delete = (Button) findViewById(R.id.re_detail_delete);
        cancel = (Button) findViewById(R.id.re_detail_cancel);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b != null){
            id = (int)b.get("id");
        }

        curr = dbHelper.getReminder(id);

        name.setText(curr.getName());
        notes.setText(curr.getNote());
        time.setText(curr.getTime());

        update.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent newIntent = new Intent("edu.brandeis.jjwang95.inminder.EditReminder");
                newIntent.putExtra("name",curr.getName());
                newIntent.putExtra("notes", curr.getNote());
                newIntent.putExtra("time", curr.getTime());
                newIntent.putExtra("id", id);
                startActivityForResult(newIntent,request_Code);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                dbHelper.deleteReminder((long) id);
                Intent data = new Intent();
                setResult(RESULT_OK,data);
                finish();
                overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == request_Code){
            if (resultCode == RESULT_OK) {
                dbHelper = DBHelper.getInstance(getApplicationContext());
                dbHelper.onOpen(db);
                curr = dbHelper.getReminder(id);
                name.setText(curr.getName());
                notes.setText(curr.getNote());
                time.setText(curr.getTime());
            }
        }
    }
}
