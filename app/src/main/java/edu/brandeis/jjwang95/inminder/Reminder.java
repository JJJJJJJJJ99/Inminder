package edu.brandeis.jjwang95.inminder;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class Reminder extends AppCompatActivity {
    private DBHelper dbHelper;
    private ReminderCursorAdapter reAdapter;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);
        Cursor cursor = dbHelper.getAllReminders();
        String[] keys = new String[]{"date", "name"};
        int[] boundTo = new int[]{};
    }
}
