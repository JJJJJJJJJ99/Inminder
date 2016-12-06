package edu.brandeis.jjwang95.inminder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import org.w3c.dom.Text;

import java.util.Date;


public class Reminder extends Fragment {
    private DBHelper dbHelper;
    private SimpleCursorAdapter reAdapter;
    private SQLiteDatabase db;
    ListView listview;
    ImageButton addBtn;
    int request_Code;
    static FragmentActivity _instance;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reminder);
//        _instance = this;
//        dbHelper = DBHelper.getInstance(getApplicationContext());
//=======
    View rootView;
    public Reminder() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _instance = getActivity();
        rootView = inflater.inflate(R.layout.activity_reminder, container, false);
        listview = (ListView) rootView.findViewById(R.id.reminder_list);
        addBtn = (ImageButton) rootView.findViewById(R.id.addBtn);



        dbHelper = DBHelper.getInstance(getActivity());
//>>>>>>> zhengyr
        dbHelper.onOpen(db);
        Cursor cursor = dbHelper.getAllReminders();

        String[] keys = new String[]{"name","date"};
        int[] boundTo = new int[]{R.id.nameShow, R.id.timeShow};

        addBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent("edu.brandeis.jjwang95.inminder.AddReminder");
                startActivityForResult(intent,request_Code);
//<<<<<<< HEAD
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//
//            }
//        });
//

//=======
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        reAdapter = new SimpleCursorAdapter(getActivity(), R.layout.reminder_entry, cursor, keys, boundTo, 0){
                public View getView(final int position, View view, ViewGroup parent) {
                    View myView = super.getView(position, view, parent);
                    Cursor curr = (Cursor)getItem(position);
                    int id = curr.getInt(curr.getColumnIndex("_id"));
                    ReminderObject thisEntry = dbHelper.getReminder(id);
                    String originalTime = thisEntry.getTime();
                    int _year = 100 + Integer.parseInt(originalTime.substring(6, 8));
                    int _month = Integer.parseInt(originalTime.substring(0, 2)) - 1;
                    int _day = Integer.parseInt(originalTime.substring(3, 5));
                    int _hour = Integer.parseInt(originalTime.substring(9, 11));
                    int _minute = Integer.parseInt(originalTime.substring(12, 14));
                    Typeface mycustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nawabiat.ttf");
                    TextView name = (TextView) myView.findViewById(R.id.nameShow);
                    TextView time = (TextView) myView.findViewById(R.id.timeShow);
                    name.setTypeface(mycustomFont, Typeface.BOLD);
                    name.setTextSize(50);
                    time.setTypeface(mycustomFont);
                    time.setTextSize(30);
                    Date current = new Date();
                    Date thisDate = new Date(_year,_month,_day,_hour,_minute);
                    if (thisDate.before(current)){
                        name.setTextColor(Color.GRAY);
                        time.setTextColor(Color.GRAY);
                    }else if (((int)(thisDate.getTime() - current.getTime()))/(24 * 60 * 60 * 1000)==0){
                        name.setTextColor(Color.RED);
                        time.setTextColor(Color.RED);
                    }
                    return myView;
                }
        };
        listview.setAdapter(reAdapter);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Cursor curr = (Cursor)getItem(position);
//                        int id = curr.getInt(curr.getColumnIndex("_id"));
                        Intent intent = new Intent(getActivity(), ReminderDetail.class);
                        intent.putExtra("id",(int)id);
                        startActivityForResult(intent,request_Code);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        reAdapter = new SimpleCursorAdapter(getActivity(), R.layout.reminder_entry, cursor, keys, boundTo, 0) {
//            public View getView(final int position, View view, ViewGroup parent) {
//                View myView = super.getView(position, view, parent);
//                detailBtn = (Button) myView.findViewById(R.id.detailBtn);
//                detailBtn.setOnClickListener(new View.OnClickListener(){
//                    public void onClick(View v){
//                        Cursor curr = (Cursor)getItem(position);
//                        int id = curr.getInt(curr.getColumnIndex("_id"));
//                        Intent intent = new Intent(getActivity(), ReminderDetail.class);
//                        intent.putExtra("id",id);
//>>>>>>> zhengyr

                    }
//<<<<<<< HEAD
                }
        );
//        {
//            public View getView(final int position, View view, ViewGroup parent) {
//                View myView = super.getView(position, view, parent);
//                detailBtn = (Button) myView.findViewById(R.id.detailBtn);
//                detailBtn.setOnClickListener(new View.OnClickListener(){
//                    public void onClick(View v){
//                        Cursor curr = (Cursor)getItem(position);
//                        int id = curr.getInt(curr.getColumnIndex("_id"));
//                        Intent intent = new Intent(Reminder.this, ReminderDetail.class);
//                        intent.putExtra("id",id);
//                        startActivityForResult(intent,request_Code);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    }
//                });
//                return myView;
//            }
//        };
//        listview.setAdapter(reAdapter);
//=======
//                });
//                return myView;
//            }
//        };
//        listview.setAdapter(reAdapter);
        return rootView;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//>>>>>>> zhengyr
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

    public static FragmentActivity getInstance() {
        return _instance;
    }
}
