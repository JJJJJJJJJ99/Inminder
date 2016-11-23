package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class Bill extends AppCompatActivity {
    public SQLiteDatabase db;
    public DBHelper dbhelper;
    public ProgressBar progress;
    public int sum;
    public int budgetAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        dbhelper = DBHelper.getInstance(getApplicationContext());
        db = dbhelper.getWritableDatabase();

        // Prepare for list view
        Cursor c = db.rawQuery("SELECT * FROM bill_table", null);
        //String[] columns = new String[] { "title", "amount"};
        //int[] views = new int[]{R.id.textView_title, R.id.textView_amount};
        BillCursorAdapter adapter = new BillCursorAdapter(getApplicationContext(), c);
        ListView billlst = (ListView) findViewById(R.id.bill_list);
        billlst.setAdapter(adapter);

        TextView balance = (TextView) findViewById(R.id.balance);
        Button plus = (Button) findViewById(R.id.plus_budget);
        budgetAmount = 0;
        plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityForResult(new Intent("edu.brandeis.jjwang95.inminder.Bill" ), 2);
            }
        });

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null){
            budgetAmount = (int) b.get("budget");
        }
        sum = sum + budgetAmount;
        //Log.d("SUM", Integer.toString(sum));
        balance.setText(Integer.toString(sum));

        // Progress Bar
        sum = sum - getExpense();
        ProgressBar progress = (ProgressBar) findViewById(R.id.progressBar3);
        if (sum<0){
            progress.setProgress(-sum);
            progress.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        } else{
            progress.setProgress(sum);
            progress.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        Button add = (Button) findViewById(R.id.button_add);
        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Bill.this, BillAdd.class);
                Bill.this.startActivity(myIntent);

            }
        });


    }

    public int getExpense(){
        int sum = 0;
        Cursor c2 = db.rawQuery("SELECT SUM(amount) FROM bill_table ", null);
        c2.moveToFirst();
        if(c2.getCount() > 0) {
            sum=c2.getInt(0);
        }
        return sum;
    }
}
