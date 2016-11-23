package edu.brandeis.jjwang95.inminder;

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
    protected void onCreate(Bundle savedInstanceState){
        final EditText setBudget = (EditText) findViewById(R.id.budget_set);
        Button set = (Button) findViewById(R.id.button_set);
        set.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BillSetBudget.this, Bill.class);
                i.putExtra("budget", Integer.parseInt(setBudget.getText().toString()));
                //setResult(2, i);
                startActivityForResult(i, 2);
            }
        });
    }
}
