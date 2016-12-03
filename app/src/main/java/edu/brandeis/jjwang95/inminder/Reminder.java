package edu.brandeis.jjwang95.inminder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Reminder extends AppCompatActivity {
    private DBHelper dbHelper;
    private SimpleCursorAdapter reAdapter;
    private SQLiteDatabase db;
    ListView listview;
    Button detailBtn, addBtn;
    int request_Code;
    static Reminder _instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        _instance = this;
        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);
        Cursor cursor = dbHelper.getAllReminders();

        String[] keys = new String[]{"name","date"};
        int[] boundTo = new int[]{R.id.nameShow, R.id.timeShow};
        listview = (ListView) findViewById(R.id.reminder_list);

        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent("edu.brandeis.jjwang95.inminder.AddReminder");
                startActivityForResult(intent,request_Code);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        reAdapter = new SimpleCursorAdapter(this, R.layout.reminder_entry, cursor, keys, boundTo, 0) {
            public View getView(final int position, View view, ViewGroup parent) {
                View myView = super.getView(position, view, parent);
                detailBtn = (Button) myView.findViewById(R.id.detailBtn);
                detailBtn.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Cursor curr = (Cursor)getItem(position);
                        int id = curr.getInt(curr.getColumnIndex("_id"));
                        Intent intent = new Intent(Reminder.this, ReminderDetail.class);
                        intent.putExtra("id",id);
                        startActivityForResult(intent,request_Code);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
                return myView;
            }
        };
        listview.setAdapter(reAdapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == request_Code){
            if (resultCode == RESULT_OK) {
                dbHelper = DBHelper.getInstance(getApplicationContext());
                dbHelper.onOpen(db);
                reAdapter.changeCursor(dbHelper.getAllReminders());
            }
        }
    }

    public static Reminder getInstance() {
        return _instance;
    }
}
