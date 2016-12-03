package edu.brandeis.jjwang95.inminder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
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

public class AddReminder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button setDate, save, cancel;
    Calendar calendar = Calendar.getInstance();
    int _year, _month, _day, _hour, _minute;
    DatePickerDialog dialog;
    TimePickerDialog t_dialog;
    String time;
    EditText name,notes;
    TextView timeshow;
    Boolean currTimeCheck=false;
    @Override

        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);

        setDate = (Button) findViewById(R.id.DateBtn);
        save = (Button) findViewById(R.id.reminder_save);
        cancel = (Button) findViewById(R.id.reminder_cancel);
        name = (EditText) findViewById(R.id.reminder_addtopic);
        notes = (EditText) findViewById(R.id.reminder_addNotes);
        timeshow = (TextView) findViewById(R.id.reminder_timeshow);

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
                dbHelper.createReminder(new ReminderObject(time, name.getText().toString().trim(), notes.getText().toString().trim()));
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
