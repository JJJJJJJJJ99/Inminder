package edu.brandeis.jjwang95.inminder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by WangJingjing on 11/14/16.
 */

public class PasswordCursorAdapter extends CursorAdapter {
    public PasswordCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.entries_password, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView website = (TextView) view.findViewById(R.id.website);
        TextView emails = (TextView) view.findViewById(R.id.emails);

        website.setText(cursor.getString(cursor.getColumnIndex("website")));
        emails.setText(cursor.getString(cursor.getColumnIndex("password")));
    }
}
