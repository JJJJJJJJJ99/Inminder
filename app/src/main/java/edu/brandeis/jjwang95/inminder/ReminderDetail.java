package edu.brandeis.jjwang95.inminder;

import android.database.sqlite.SQLiteDatabase;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.content.Context;
import android.widget.ToggleButton;
import java.util.Date;
import java.util.Calendar;

public class ReminderDetail extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    TextView name, notes, time;
    Button update, delete,cancel, search;
    EditText keywords;
    ReminderObject curr;
    int id, request_Code, _month, _day, _hour, _minute, _year;
    Context context;
    ToggleButton toggle;
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);

        this.context = this;

        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);

        name = (TextView) findViewById(R.id.nameDetail);
        notes = (TextView) findViewById(R.id.noteDetail);
        time = (TextView) findViewById(R.id.timeDetail);
        cancel = (Button) findViewById(R.id.re_detail_cancel);
        toggle = (ToggleButton) findViewById(R.id.reminder_toggleBtn);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b != null){
            id = b.getInt("id");
        }

        Log.e("check", Integer.toString(b.getInt("id")));
        curr = dbHelper.getReminder(id);

        name.setText(curr.getName());
        notes.setText(curr.getNote());
        time.setText(curr.getTime());

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle.isChecked()){
                    curr = dbHelper.getReminder(id);
                    String originalTime = curr.getTime();
                    _year = 2000 + Integer.parseInt(originalTime.substring(6,8));
                    _month = Integer.parseInt(originalTime.substring(0,2));
                    _day = Integer.parseInt(originalTime.substring(3,5));
                    _hour = Integer.parseInt(originalTime.substring(9,11));
                    _minute = Integer.parseInt(originalTime.substring(12,14));

                    Log.e("in Toggle TRUe", "a");
                    Intent myIntent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
                    Calendar alarmCal = Calendar.getInstance();
                    alarmCal.set(Calendar.YEAR, _year);
                    alarmCal.set(Calendar.MONTH, _month-1);
                    alarmCal.set(Calendar.DAY_OF_MONTH, _day);
                    alarmCal.set(Calendar.HOUR_OF_DAY, _hour);
                    alarmCal.set(Calendar.MINUTE, _minute);
                    alarmCal.set(Calendar.SECOND, 0);
                    Log.e(alarmCal.getTime().toString(), "check");

                    myIntent.putExtra("extra", "on");
                    myIntent.putExtra("id", id);
                    PendingIntent pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pending_intent);
                }else{
                    curr = dbHelper.getReminder(id);
                    String originalTime = curr.getTime();
                    _year = 100 + Integer.parseInt(originalTime.substring(6,8));
                    _month = Integer.parseInt(originalTime.substring(0,2)) - 1;
                    _day = Integer.parseInt(originalTime.substring(3,5));
                    _hour = Integer.parseInt(originalTime.substring(9,11));
                    _minute = Integer.parseInt(originalTime.substring(12,14));
                    Date thisTime = new Date(_year, _month, _day, _hour, _minute);
                    Intent myIntent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
                    Log.e("year check", Integer.toString(_year));
                    Log.e("time check", new Date().toString());
                    Log.e("time check2", thisTime.toString());


                    if (new Date().before(thisTime)){
                        myIntent.putExtra("extra", "pre-off");
                    }else{
                        myIntent.putExtra("extra", "off");
                    }
                    myIntent.putExtra("id", id);
                    PendingIntent pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    sendBroadcast(myIntent);

                    alarmManager.cancel(pending_intent);
                }
            }
        });

//        search.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent newIntent = new Intent("edu.brandeis.jjwang95.inminder.ReminderWebSearch");
//                String key = keywords.getText().toString().trim();
//                String webpage = "https://www.google.com/#q=";
//                for (String s : key.split(" ")){
//                    webpage = webpage + s + "+";
//                }
//                newIntent.putExtra("keyword",webpage);
//                startActivityForResult(newIntent,request_Code);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });

//        update.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent newIntent = new Intent("edu.brandeis.jjwang95.inminder.EditReminder");
//                newIntent.putExtra("name",curr.getName());
//                newIntent.putExtra("notes", curr.getNote());
//                newIntent.putExtra("time", curr.getTime());
//                newIntent.putExtra("id", id);
//                startActivityForResult(newIntent,request_Code);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });
//
//        delete.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                dbHelper.deleteReminder((long) id);
//                Intent myIntent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
//                myIntent.putExtra("extra", "cancel");
//                myIntent.putExtra("id", id);
//                PendingIntent pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                sendBroadcast(myIntent);
//
//                alarmManager.cancel(pending_intent);
//                Intent data = new Intent();
//                setResult(RESULT_OK,data);
//                finish();
//                overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
//            }
//        });

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
            }
        });

//        stop.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//
//            }
//        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater myInflater = getMenuInflater();
        myInflater.inflate(R.menu.reminder_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reminder_menu_search) {
            Intent newIntent = new Intent("edu.brandeis.jjwang95.inminder.ReminderWebSearch");
            startActivity(newIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        } else if (item.getItemId() == R.id.reminder_menu_update) {
            curr = dbHelper.getReminder(id);
            Intent newIntent = new Intent("edu.brandeis.jjwang95.inminder.EditReminder");
            newIntent.putExtra("name", curr.getName());
            newIntent.putExtra("notes", curr.getNote());
            newIntent.putExtra("time", curr.getTime());
            newIntent.putExtra("id", id);
            startActivityForResult(newIntent, request_Code);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        } else if (item.getItemId() == R.id.reminder_menu_delete){
            dbHelper.deleteReminder((long) id);
            Intent myIntent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
            myIntent.putExtra("extra", "cancel");
            myIntent.putExtra("id", id);
            PendingIntent pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            sendBroadcast(myIntent);

            ((AlarmManager) getSystemService(ALARM_SERVICE)).cancel(pending_intent);
            Intent data = new Intent();
            setResult(RESULT_OK,data);
            finish();
            overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == request_Code){
            if (resultCode == RESULT_OK) {
                dbHelper = DBHelper.getInstance(getApplicationContext());
                dbHelper.onOpen(db);
                curr = dbHelper.getReminder(id);
                name.setText(curr.getName());
                notes.setText(curr.getNote());
                time.setText(curr.getTime());
                toggle.setChecked(true);
            }
        }
    }


}