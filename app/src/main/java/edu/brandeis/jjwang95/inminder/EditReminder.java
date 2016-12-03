package edu.brandeis.jjwang95.inminder;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.content.Intent;

public class EditReminder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button setDate, save, cancel;
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

        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        setDate = (Button) findViewById(R.id.editRe_timeBtn);
        save = (Button) findViewById(R.id.editRe_save);
        cancel = (Button) findViewById(R.id.editRe_cancel);
        name = (EditText) findViewById(R.id.editRe_name);
        notes = (EditText) findViewById(R.id.editRe_notes);
        timeshow = (TextView) findViewById(R.id.editRe_timeshow);

        name.setText(b.get("name").toString());
        notes.setText(b.get("notes").toString());
        timeshow.setText(b.get("time").toString());
        id = (int)b.get("id");

        originalTime = b.get("time").toString();
        _year = 2000 + Integer.parseInt(originalTime.substring(6,8));
        _month = Integer.parseInt(originalTime.substring(0,2));
        _day = Integer.parseInt(originalTime.substring(3,5));
        _hour = Integer.parseInt(originalTime.substring(9,11));
        _minute = Integer.parseInt(originalTime.substring(12,14));

        setDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dialog = new DatePickerDialog(EditReminder.this, EditReminder.this, _year, _month-1, _day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                PendingIntent pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), id, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pending_intent);


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

        t_dialog = new TimePickerDialog(EditReminder.this, EditReminder.this, _hour, _minute, true);
        t_dialog.show();
    }

    public void onTimeSet(TimePicker timePicker, int i, int i2){
        _hour = i;
        _minute = i2;
        timeshow.setText((new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.US)).format(new Date(_year, _month, _day, _hour, _minute)));

    }
}
