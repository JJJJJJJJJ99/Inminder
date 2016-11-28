package edu.brandeis.jjwang95.inminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by WangJingjing on 11/22/16.
 */

public class BillSetBudget extends AppCompatActivity {
    EditText setBudget;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_budget);
        setBudget = (EditText) findViewById(R.id.budget_set);
        final Button set = (Button) findViewById(R.id.button_set);
        set.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper.getInstance(getApplicationContext()).setBudget(Double.parseDouble(setBudget.getText().toString()));
                Intent i = new Intent(BillSetBudget.this, Bill.class);
                BillSetBudget.this.startActivity(i);
            }
        });


    }
}
