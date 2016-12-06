package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by WangJingjing on 11/22/16.
 */

public class BillAdd extends AppCompatActivity {
    final BillObject bill = new BillObject();
    DBHelper dbHelper;
    EditText title;
    EditText amount;
    EditText note;
    EditText bill_add_expense_input;
    Button bill_add_expense_button;
    Button bill_add_expense_cancel;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bill_add);
        bill_add_expense_input = (EditText) findViewById(R.id.bill_add_expense_input);
        bill_add_expense_button = (Button) findViewById(R.id.bill_add_expense_button);
        bill_add_expense_cancel = (Button) findViewById(R.id.bill_add_expense_cancel);
        bill_add_expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.setData(Uri.parse(bill_add_expense_input.getText().toString()));
                setResult(RESULT_OK, data);
                finish();
            }
        });
        bill_add_expense_cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();
            }
        });




    }
}
