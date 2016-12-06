package edu.brandeis.jjwang95.inminder;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.content.Intent;

public class EditReminder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    DBHelper dbHelper;
    SQLiteDatabase db;
    ImageButton setDate;
    int _year, _month, _day, _hour, _minute, id;
    Calendar alarmCal = Calendar.getInstance();
    DatePickerDialog dialog;
    TimePickerDialog t_dialog;
    String time, originalTime;
    EditText name,notes;
    TextView timeshow;
    ReminderObject updated;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.actionBarTop);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        myToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Reminder");
        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        Typeface mycustomFont = Typeface.createFromAsset(getAssets(), "fonts/Nawabiat.ttf");
        setDate = (ImageButton) findViewById(R.id.editRe_timeBtn);
        name = (EditText) findViewById(R.id.editRe_name);
        notes = (EditText) findViewById(R.id.editRe_notes);
        timeshow = (TextView) findViewById(R.id.editRe_timeshow);

        timeshow.setTypeface(mycustomFont);
        timeshow.setTextSize(40);
        name.setTextSize(50);
        name.setTypeface(mycustomFont);
        notes.setTypeface(mycustomFont);
        notes.setTextSize(30);

        name.setText(b.get("name").toString());
        getSupportActionBar().setTitle(b.get("name").toString());
        notes.setText(b.get("notes").toString());
        timeshow.setText(b.get("time").toString());
        id = (int)b.get("id");

        originalTime = b.get("time").toString();
        _year = 2000 + Integer.parseInt(originalTime.substring(6,8));
        _month = Integer.parseInt(originalTime.substring(0,2))-1;
        _day = Integer.parseInt(originalTime.substring(3,5));
        _hour = Integer.parseInt(originalTime.substring(9,11));
        _minute = Integer.parseInt(originalTime.substring(12,14));

        setDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dialog = new DatePickerDialog(EditReminder.this, EditReminder.this, _year, _month, _day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });
    }

    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
        _year = i;
        _month = i1;
        _day = i2;

        t_dialog = new TimePickerDialog(EditReminder.this, EditReminder.this, _hour, _minute, true);
        t_dialog.show();
    }

    public void onTimeSet(TimePicker timePicker, int i, int i2){
        _hour = i;
        _minute = i2;
        timeshow.setText((new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.US)).format(new Date(_year, _month, _day, _hour, _minute)));

    }

    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myInflater = getMenuInflater();
        myInflater.inflate(R.menu.edit_reminder_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_re_done) {
            time = (new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.US)).format(new Date(_year, _month, _day, _hour, _minute));
            updated = new ReminderObject(time, name.getText().toString().trim(), notes.getText().toString().trim());
            updated.setId(id);
            dbHelper.updateReminder(updated);
            Log.e("time", time);
            Intent my_intent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
            alarmCal.set(Calendar.YEAR, _year);
            alarmCal.set(Calendar.MONTH, _month);
            alarmCal.set(Calendar.DAY_OF_MONTH, _day);
            alarmCal.set(Calendar.HOUR_OF_DAY, _hour);
            alarmCal.set(Calendar.MINUTE, _minute);
            alarmCal.set(Calendar.SECOND, 0);
            Log.e(alarmCal.getTime().toString(), "check");
            my_intent.putExtra("extra", "on");
            my_intent.putExtra("id", id);
            my_intent.putExtra("topic", name.getText().toString().trim());
            PendingIntent pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), id, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pending_intent);


            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
            overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
