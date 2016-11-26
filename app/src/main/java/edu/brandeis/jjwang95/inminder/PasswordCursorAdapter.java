package edu.brandeis.jjwang95.inminder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by WangJingjing on 11/14/16.
 */

public class PasswordCursorAdapter extends SimpleCursorAdapter {
    private final LayoutInflater inflater;

    public PasswordCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.entries_password, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView website = (TextView) view.findViewById(R.id.website);
        TextView email = (TextView) view.findViewById(R.id.email);

        website.setText(cursor.getString(cursor.getColumnIndex("website")));
        email.setText(cursor.getString(cursor.getColumnIndex("email")));
    }

}
