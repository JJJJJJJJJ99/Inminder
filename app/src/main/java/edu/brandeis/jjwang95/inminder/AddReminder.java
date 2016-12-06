package edu.brandeis.jjwang95.inminder;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.content.Intent;
import android.database.Cursor;


public class AddReminder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button setDate, save, cancel;
    Calendar calendar = Calendar.getInstance();
    Calendar alarmCal = Calendar.getInstance();
    int _year, _month, _day, _hour, _minute;
    DatePickerDialog dialog;
    TimePickerDialog t_dialog;
    String time;
    EditText name,notes;
    TextView timeshow;
    Boolean currTimeCheck=false;

    AlarmManager alarm_manager;
    PendingIntent pending_intent;
    Intent my_intent;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.actionBarTop);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        myToolbar.setTitleTextColor(Color.WHITE);

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);

        Typeface mycustomFont = Typeface.createFromAsset(getAssets(), "fonts/Nawabiat.ttf");
        setDate = (Button) findViewById(R.id.DateBtn);
        save = (Button) findViewById(R.id.reminder_save);
        cancel = (Button) findViewById(R.id.reminder_cancel);
        name = (EditText) findViewById(R.id.reminder_addtopic);
        notes = (EditText) findViewById(R.id.reminder_addNotes);
        timeshow = (TextView) findViewById(R.id.reminder_timeshow);

        timeshow.setTypeface(mycustomFont);
        timeshow.setTextSize(40);
        name.setTextSize(50);
        name.setTypeface(mycustomFont);
        notes.setTypeface(mycustomFont);
        notes.setTextSize(30);
        my_intent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
        setDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (currTimeCheck){
                    dialog = new DatePickerDialog(AddReminder.this, AddReminder.this, _year, _month, _day);
                }else{
                    dialog = new DatePickerDialog(AddReminder.this, AddReminder.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                }
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                time = (new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.US)).format(new Date(_year, _month, _day, _hour, _minute));
                long id = dbHelper.createReminder(new ReminderObject(time, name.getText().toString().trim(), notes.getText().toString().trim()));

                alarmCal.set(Calendar.YEAR, _year);
                alarmCal.set(Calendar.MONTH, _month);
                alarmCal.set(Calendar.DAY_OF_MONTH, _day);
                alarmCal.set(Calendar.HOUR_OF_DAY, _hour);
                alarmCal.set(Calendar.MINUTE, _minute);
                alarmCal.set(Calendar.SECOND, 0);
                my_intent.putExtra("extra", "on");
                my_intent.putExtra("id", (int) id);
                pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), (int)id, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pending_intent);

                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
            }
        });
    }

    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
        _year = i;
        _month = i1;
        _day = i2;

        if (currTimeCheck){
            t_dialog = new TimePickerDialog(AddReminder.this, AddReminder.this, _hour, _minute, true);
        }else {
            t_dialog = new TimePickerDialog(AddReminder.this, AddReminder.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        }
        t_dialog.show();
    }

    public void onTimeSet(TimePicker timePicker, int i, int i2){
        _hour = i;
        _minute = i2;
        currTimeCheck = true;
        timeshow.setText((new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.US)).format(new Date(_year, _month, _day, _hour, _minute)));

    }
}
