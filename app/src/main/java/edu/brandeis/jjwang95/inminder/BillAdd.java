package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by WangJingjing on 11/22/16.
 */

public class BillAdd extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        final DBHelper dbhelper = DBHelper.getInstance(getApplicationContext());
        //SQLiteDatabase db = dbhelper.getWritableDatabase();
        EditText title = (EditText) findViewById(R.id.editText_title);
        EditText amount = (EditText) findViewById(R.id.editText_amount);
        EditText note = (EditText) findViewById(R.id.editText_note);
        Button cancel = (Button) findViewById(R.id.button_cancel);
        Button save = (Button) findViewById(R.id.button_save);
        final String titleText = title.getText().toString();
        final String amountText = amount.getText().toString();
        final String noteText = note.getText().toString();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(BillAdd.this, MainActivity.class);
                BillAdd.this.startActivity(myIntent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillObject bill = new BillObject(titleText,amountText,noteText);
                dbhelper.createBill(bill);
                Intent myIntent = new Intent(BillAdd.this, MainActivity.class);
                BillAdd.this.startActivity(myIntent);
            }
        });


    }
}
