package edu.brandeis.jjwang95.inminder;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Bill extends Fragment {
    public SQLiteDatabase db;
    public DBHelper dbhelper;
    public ProgressBar progress;
    public double sum = 0;
    public int request_Code;
    public View rootView;
    public ImageButton bill_add_btn;
    public ImageButton bill_set_btn;
    public BillCursorAdapter adapter;
    public ListView billlst;
    public TextView balance;


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
        dbhelper.onOpen(db);
        db = dbhelper.getWritableDatabase();

        Typeface mycustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nawabiat.ttf");
        // Prepare for list view
        Cursor c = db.rawQuery("SELECT * FROM bill_table", null);
        String[] columns = new String[] { "title", "amount"};
        int[] views = new int[]{R.id.textView_title, R.id.textView_amount};
        adapter = new BillCursorAdapter(getActivity(), R.layout.bill_entry, c, columns, views);
        billlst = (ListView) rootView.findViewById(R.id.bill_list);
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
        balance = (TextView) rootView.findViewById(R.id.balance);
        balance.setTypeface(mycustomFont);
        balance.setTextSize(30);
        TextView label = (TextView) rootView.findViewById(R.id.current_balance);
        label.setTypeface(mycustomFont, Typeface.BOLD);
        label.setTextSize(30);
        bill_set_btn =  (ImageButton) rootView.findViewById(R.id.bill_set_btn);
        bill_set_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(new Intent("edu.brandeis.jjwang95.inminder.BillSetBudget" ));
                Bill.this.startActivityForResult(myIntent, request_Code);
            }
        });


        /*Progress Bar
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
        bill_add_btn =  (ImageButton) rootView.findViewById(R.id.bill_add_btn);
        bill_add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(new Intent("edu.brandeis.jjwang95.inminder.BillAdd" ));
                Bill.this.startActivityForResult(myIntent, request_Code);
            }
        });

        /*PieChart pieChart = (PieChart) rootView.findViewById(R.id.chart);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        ArrayList<PieEntry> entries = new ArrayList<>();
        // Category food, rent, utility, entertainment, others

        Double budget = dbhelper.getBudget();
        int budgetFloat = budget.intValue();
        Log.d("Budget", Float.toString(budgetFloat));


        Double total = dbhelper.getSum();
        int totalFloat = total.intValue();


        Double difference = budget - total;
        int differenceFloat =  difference.intValue();
        float chartTotal = budgetFloat + totalFloat + differenceFloat;
        /*entries.add(new PieEntry((float) budgetFloat, "Budget"));
        entries.add(new PieEntry((float) totalFloat, "TotalExpense"));
        entries.add(new PieEntry((float) differenceFloat, "Balance"));
        ArrayList<Float> list = new ArrayList<Float>();
        list.add(budgetFloat/chartTotal);
        list.add(totalFloat/chartTotal);
        list.add(differenceFloat/chartTotal);
        for (int i = 0; i < 3 ; i++) {
            entries.add(new PieEntry((float) budgetFloat, "labels"));
        }

        IPieDataSet dataset = new PieDataSet(entries, "Balance");
        dataset.setValueTextColor(Color.BLUE);
        PieData data = new PieData();
        data.setDataSet(dataset);
        Description d = new Description();
        d.setText("Balance Chart");
        pieChart.setDescription(d);
        pieChart.setData(data);

        pieChart.animateY(5000);

        pieChart.saveToGallery("/sd/mychart.jpg", 85);
        // 85 is the quality of the image*/

        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request_Code) {
            if (resultCode == getActivity().RESULT_OK) {
                dbhelper = DBHelper.getInstance(getActivity());
                Cursor c = dbhelper.getAllBills();
                adapter.changeCursor(c);
                sum = dbhelper.getBudget() - dbhelper.getSum();
                balance.setText(Double.toString(sum));
            }
        }
    }



}
