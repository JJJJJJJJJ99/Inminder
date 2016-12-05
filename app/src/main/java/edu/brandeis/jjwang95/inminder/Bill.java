package edu.brandeis.jjwang95.inminder;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class Bill extends Fragment {
    public SQLiteDatabase db;
    public DBHelper dbhelper;
    public ProgressBar progress;
    public double sum = 0;
    public int request_Code;
    View rootView;

    public Bill(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_bill, container, false);

        dbhelper = DBHelper.getInstance(getActivity());
        db = dbhelper.getWritableDatabase();

        // Prepare for list view
        Cursor c = db.rawQuery("SELECT * FROM bill_table", null);
        String[] columns = new String[] { "title", "amount"};
        int[] views = new int[]{R.id.textView_title, R.id.textView_amount};
        BillCursorAdapter adapter = new BillCursorAdapter(getActivity(), R.layout.bill_entry, c, columns, views);
        ListView billlst = (ListView) rootView.findViewById(R.id.bill_list);
        billlst.setAdapter(adapter);

        billlst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BillNote.class);
                intent.putExtra("id", id);
                Bill.this.startActivityForResult(intent, request_Code);
            }
        });

        // Balance
        TextView balance = (TextView) rootView.findViewById(R.id.balance);
        Button plus = (Button) rootView.findViewById(R.id.plus_budget);
        plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), BillSetBudget.class);
                Bill.this.startActivity(intent);
            }
        });
        sum =  dbhelper.getBudget() - dbhelper.getSum();
        balance.setText(Double.toString(sum));

        /* Progress Bar
        int pro = (int) Math.round(sum); // Converting a double sum into integer for display in progress bar
        progress = (ProgressBar) findViewById(R.id.progressBar3);
        if (pro<0){
            progress.setProgress(-pro);
            progress.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        } else{
            progress.setProgress(pro);
            progress.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        }*/

        // Add Expense
        Button add = (Button) rootView.findViewById(R.id.button_add);
        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(new Intent("edu.brandeis.jjwang95.inminder.BillAdd" ));
                Bill.this.startActivity(myIntent);
            }
        });
        return rootView;
    }

}
