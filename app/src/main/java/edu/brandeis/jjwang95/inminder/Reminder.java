package edu.brandeis.jjwang95.inminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Reminder extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private DBHelper dbHelper;
    private SimpleCursorAdapter reAdapter;
    private SQLiteDatabase db;
    ListView listview;
    ImageButton addBtn;
    int request_Code, _year, _month, _day, _hour, _minute;;
    Boolean currTimeCheck=false;
    static FragmentActivity _instance;
    DatePickerDialog dialog;
    TimePickerDialog t_dialog;
    TextView timeshow;
    EditText name, notes;


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
//                Intent intent = new Intent("edu.brandeis.jjwang95.inminder.AddReminder");
//                startActivityForResult(intent,request_Code);
//                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View textEntryView = factory.inflate(R.layout.activity_add_reminder, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                alert.setTitle("New Reminder");
                Typeface mycustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nawabiat.ttf");
                ImageButton setDate = (ImageButton) textEntryView.findViewById(R.id.DateBtn);
                name = (EditText) textEntryView.findViewById(R.id.reminder_addtopic);
                notes = (EditText) textEntryView.findViewById(R.id.reminder_addNotes);
                timeshow = (TextView) textEntryView.findViewById(R.id.reminder_timeshow);

                timeshow.setTypeface(mycustomFont);
                timeshow.setTextSize(40);
                name.setTextSize(50);
                name.setTypeface(mycustomFont);
                notes.setTypeface(mycustomFont);
                notes.setTextSize(30);

                setDate.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        if (currTimeCheck){
                            dialog = new DatePickerDialog(getActivity(), Reminder.this, _year, _month, _day);
                        }else{
                            dialog = new DatePickerDialog(getActivity(), Reminder.this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        }
                        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        dialog.show();
                    }
                });

                alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        currTimeCheck = false;
                        String time = (new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.US)).format(new Date(_year, _month, _day, _hour, _minute));
                        long id = dbHelper.createReminder(new ReminderObject(time, name.getText().toString().trim(), notes.getText().toString().trim()));

                        Calendar alarmCal = Calendar.getInstance();
                        alarmCal.set(Calendar.YEAR, _year);
                        alarmCal.set(Calendar.MONTH, _month);
                        alarmCal.set(Calendar.DAY_OF_MONTH, _day);
                        alarmCal.set(Calendar.HOUR_OF_DAY, _hour);
                        alarmCal.set(Calendar.MINUTE, _minute);
                        alarmCal.set(Calendar.SECOND, 0);
                        Intent my_intent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
                        my_intent.putExtra("extra", "on");
                        my_intent.putExtra("id", (int) id);
                        my_intent.putExtra("topic", name.getText().toString().trim());
                        PendingIntent pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), (int)id, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        ((AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pending_intent);


                        reAdapter.changeCursor(dbHelper.getAllReminders());
                        listview.setAdapter(reAdapter);
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        currTimeCheck = false;
                        dialog.dismiss();
                    }
                });
                alert.setView(textEntryView);
                alert.show();

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
                    }else{
                        name.setTextColor(Color.BLACK);
                        time.setTextColor(Color.BLACK);
                    }
                    return myView;
                }
        };
        listview.setAdapter(reAdapter);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), ReminderDetail.class);
                        intent.putExtra("id",(int)id);
                        startActivityForResult(intent,request_Code);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
//<<<<<<< HEAD
                }
        );

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
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
        _year = i;
        _month = i1;
        _day = i2;

        if (currTimeCheck){
            t_dialog = new TimePickerDialog(getActivity(), Reminder.this, _hour, _minute, true);
        }else {
            t_dialog = new TimePickerDialog(getActivity(), Reminder.this, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        }
        t_dialog.show();
    }

    public void onTimeSet(TimePicker timePicker, int i, int i2){
        _hour = i;
        _minute = i2;
        currTimeCheck = true;
        timeshow.setText((new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.US)).format(new Date(_year, _month, _day, _hour, _minute)));

    }

    public static FragmentActivity getInstance() {
        return _instance;
    }
}
