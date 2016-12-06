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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class Bill extends Fragment {
    public SQLiteDatabase db;
    public DBHelper dbhelper;
    public ProgressBar progress;
    public double sum = 0;
    public int request_Code;
    View rootView;
    public int budget = 800;
    public int expense = 100;
    public int left = 700;
    public PieChart chart;
    List<PieEntry> entries;
    PieDataSet set;
    PieData data;
    PieEntry ex ;
    PieEntry lf;
    public Bill(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_bill, container, false);
        Button add_expense = (Button) rootView.findViewById(R.id.add_expense);
        add_expense.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(new Intent("edu.brandeis.jjwang95.inminder.BillAdd" ));
                Bill.this.startActivityForResult(myIntent, 2);
            }
        });

        PieChart chart = (PieChart) rootView.findViewById(R.id.chart);
        entries = new ArrayList<>();
        ex = new PieEntry(expense, "Expense");
        lf = new PieEntry(left, "Left");
        entries.add(ex);
        entries.add(lf);


        set = new PieDataSet(entries, "Your Bill");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        data = new PieData(set);

        chart.setData(data);
        chart.invalidate(); // refresh

        return rootView;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data1){

        super.onActivityResult(requestCode, resultCode, data1);
        if(requestCode==2){
            if(resultCode == getActivity().RESULT_OK){
                //if there were no last conversion, then we will have 0 as result here.
                expense = expense + Integer.parseInt(data1.getData().toString());
                set.removeEntry(ex);
                ex = new PieEntry(expense, "Expense");
                set.addEntry(ex);
                left = left - Integer.parseInt(data1.getData().toString());
                set.removeEntry(lf);
                lf = new PieEntry(left, "Left");
                set.addEntry(lf);
                 // let the data know a dataSet changed
                data.notifyDataChanged();
                chart = (PieChart) rootView.findViewById(R.id.chart);
                chart.notifyDataSetChanged(); // let the chart know it's data changed
                chart.invalidate(); // refresh

            }
        }

    }

}
