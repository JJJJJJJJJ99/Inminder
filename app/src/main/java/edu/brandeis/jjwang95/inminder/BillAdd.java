package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by WangJingjing on 11/22/16.
 */

public class BillAdd extends AppCompatActivity {
    DBHelper dbHelper;
    EditText title;
    EditText amount;
    EditText note;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dbHelper = DBHelper.getInstance(getApplicationContext());
        setContentView(R.layout.bill_add);
        //SQLiteDatabase db = dbhelper.getWritableDatabase();
        title = (EditText) findViewById(R.id.editText_title);
        amount = (EditText) findViewById(R.id.editText_amount);
        note = (EditText) findViewById(R.id.editText_note);
        //Button cancel = (Button) findViewById(R.id.button_cancel);
        Button save = (Button) findViewById(R.id.button_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillObject bill = new BillObject();
                bill.setNote(note.getText().toString());
                bill.setAmount(Double.parseDouble(amount.getText().toString()));
                dbHelper.addToSum(Double.parseDouble(amount.getText().toString()));
                bill.setTitle(title.getText().toString());
                long id = dbHelper.createBill(bill);
                bill.setId(id);
                Intent myIntent = new Intent();
                setResult(RESULT_OK, myIntent);
                //BillAdd.this.startActivity(myIntent);
                finish();
            }
        });


    }
}
