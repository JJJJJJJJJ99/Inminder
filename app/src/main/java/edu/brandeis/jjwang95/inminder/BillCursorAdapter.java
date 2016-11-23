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

public class BillCursorAdapter extends CursorAdapter {
    public BillCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.bill_entry, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleText = (TextView) view.findViewById(R.id.textView_title);
        TextView amountText = (TextView) view.findViewById(R.id.textView_amount);
        titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));
        amountText.setText(cursor.getString(cursor.getColumnIndexOrThrow("amount")));

    }
}
