package edu.brandeis.jjwang95.inminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by WangJingjing on 11/22/16.
 */

public class BillSetBudget extends AppCompatActivity {
    Button set;
    EditText setBudget;
    public DBHelper dbHelper;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_budget);
        dbHelper =  DBHelper.getInstance(getApplicationContext());
        setBudget = (EditText) findViewById(R.id.budget_set);
//        set = (Button) findViewById(R.id.button_set);
//        set.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Log.d("Here", "Budget");
////                DBHelper.getInstance(getApplicationContext()).setBudget(Double.parseDouble(setBudget.getText().toString()));
//
//                dbHelper.setBudget(Double.parseDouble(setBudget.getText().toString()));
//                Log.d("budget", setBudget.getText().toString());
//                Intent i = new Intent();
//                setResult(RESULT_OK, i);
//                finish();
//            }
//        });
//        final Button cancel = (Button) findViewById(R.id.bill_button_cancel);
//        cancel.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                //BillSetBudget.this.startActivity(i);
//                finish();
//            }
//        });


    }
}
