package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

public class Reminder extends Fragment {
    private DBHelper dbHelper;
    private SimpleCursorAdapter reAdapter;
    private SQLiteDatabase db;
    ListView listview;
    Button detailBtn, addBtn;
    int request_Code;
    View rootView;
    public Reminder() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_reminder, container, false);
        listview = (ListView) rootView.findViewById(R.id.reminder_list);
        addBtn = (Button) rootView.findViewById(R.id.addBtn);



        dbHelper = DBHelper.getInstance(getActivity());
        dbHelper.onOpen(db);
        Cursor cursor = dbHelper.getAllReminders();

        String[] keys = new String[]{"name","date"};
        int[] boundTo = new int[]{R.id.nameShow, R.id.timeShow};

        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent("edu.brandeis.jjwang95.inminder.AddReminder");
                startActivityForResult(intent,request_Code);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        reAdapter = new SimpleCursorAdapter(getActivity(), R.layout.reminder_entry, cursor, keys, boundTo, 0) {
            public View getView(final int position, View view, ViewGroup parent) {
                View myView = super.getView(position, view, parent);
                detailBtn = (Button) myView.findViewById(R.id.detailBtn);
                detailBtn.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Cursor curr = (Cursor)getItem(position);
                        int id = curr.getInt(curr.getColumnIndex("_id"));
                        Intent intent = new Intent(getActivity(), ReminderDetail.class);
                        intent.putExtra("id",id);
                        startActivityForResult(intent,request_Code);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
                return myView;
            }
        };
        listview.setAdapter(reAdapter);




        return rootView;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == request_Code){
            if (resultCode == getActivity().RESULT_OK) {
                dbHelper = DBHelper.getInstance(getActivity());
                dbHelper.onOpen(db);
                reAdapter.changeCursor(dbHelper.getAllReminders());
            }
        }
    }
}
