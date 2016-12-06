package edu.brandeis.jjwang95.inminder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.graphics.Typeface;

/**
 * Created by WangJingjing on 11/14/16.
 */

public class BillCursorAdapter extends SimpleCursorAdapter {
    public BillCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to){
        super(context, layout, c, from, to);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.bill_entry, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        Typeface mytypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Nawabiat.ttf");

        TextView titleText = (TextView) view.findViewById(R.id.textView_title);
        TextView amountText = (TextView) view.findViewById(R.id.textView_amount);
        titleText.setTypeface(mytypeface);
        titleText.setTextSize(30);
        titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));
        amountText.setTypeface(mytypeface);
        amountText.setTextSize(30);
        amountText.setText(cursor.getString(cursor.getColumnIndexOrThrow("amount")));

    }
}
