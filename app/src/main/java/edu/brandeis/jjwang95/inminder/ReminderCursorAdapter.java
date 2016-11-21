package edu.brandeis.jjwang95.inminder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Created by WangJingjing on 11/14/16.
 */

public class ReminderCursorAdapter extends SimpleCursorAdapter {
    DBHelper dbHelper;
    SQLiteDatabase db;
    public ReminderCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flag){
        super(context,layout, cursor, from, to, flag);
    }

//    public View getView(final int position, View view, ViewGroup parent){
//        View myView = super.getView(position,view,parent);
//        dbHelper = DBHelper.getInstance(getApplicationContext());
//        dbHelper.onOpen(db);
//        deleteBtn = (Button) myView.findViewById(R.id.deleteBtn);
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Cursor curr = (Cursor)getItem(position);
//                int id = curr.getInt(curr.getColumnIndex(dBHandler.KEY_ID));
//                dBHandler.deleteEntry(id);
//                myAdapter.changeCursor(dBHandler.getAllEntries());
//            }
//        });
//        return myView;
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_reminder, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
