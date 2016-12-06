package edu.brandeis.jjwang95.inminder;

import android.database.sqlite.SQLiteDatabase;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.content.Context;
import android.widget.ToggleButton;
import android.graphics.Typeface;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Build;
import android.annotation.TargetApi;
import android.annotation.SuppressLint;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")

public class ReminderDetail extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    TextView name, notes, time, counter, daysLeft;
    Button update, delete, cancel, search;
    EditText keywords;
    ReminderObject curr;
    int id, request_Code, _month, _day, _hour, _minute, _year;
    Context context;
    ToggleButton toggle;
    AlarmManager alarmManager;
    CounterClass timer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.actionBarTop);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        myToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context = this;

        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);
        Typeface mycustomFont = Typeface.createFromAsset(getAssets(), "fonts/Nawabiat.ttf");
        name = (TextView) findViewById(R.id.nameDetail);
        notes = (TextView) findViewById(R.id.noteDetail);
        time = (TextView) findViewById(R.id.timeDetail);
        counter = (TextView) findViewById(R.id.CountdownTest);
        daysLeft = (TextView) findViewById(R.id.daysLeft);
//        cancel = (Button) findViewById(R.id.re_detail_cancel);
        toggle = (ToggleButton) findViewById(R.id.reminder_toggleBtn);

        name.setTypeface(mycustomFont);
        name.setTextSize(50);
        notes.setTypeface(mycustomFont);
        notes.setTextSize(30);
        time.setTypeface(mycustomFont);
        time.setTextSize(40);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            id = b.getInt("id");
        }

        Log.e("check", Integer.toString(b.getInt("id")));
        curr = dbHelper.getReminder(id);
        String thisTime = curr.getTime();
        countdown(thisTime);

        counter.setTypeface(mycustomFont, Typeface.BOLD);
        counter.setTextSize(50);
        name.setText(curr.getName());
        getSupportActionBar().setTitle(curr.getName());
        notes.setText(curr.getNote());
        time.setText(curr.getTime());

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        ViewGroup.LayoutParams params = toggle.getLayoutParams();
        params.width = 180;
        params.height = 180;
        toggle.setLayoutParams(params);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle.isChecked()) {
                    curr = dbHelper.getReminder(id);
                    String originalTime = curr.getTime();
                    _year = 2000 + Integer.parseInt(originalTime.substring(6, 8));
                    _month = Integer.parseInt(originalTime.substring(0, 2));
                    _day = Integer.parseInt(originalTime.substring(3, 5));
                    _hour = Integer.parseInt(originalTime.substring(9, 11));
                    _minute = Integer.parseInt(originalTime.substring(12, 14));

                    Log.e("in Toggle TRUe", "a");
                    Intent myIntent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
                    Calendar alarmCal = Calendar.getInstance();
                    alarmCal.set(Calendar.YEAR, _year);
                    alarmCal.set(Calendar.MONTH, _month - 1);
                    alarmCal.set(Calendar.DAY_OF_MONTH, _day);
                    alarmCal.set(Calendar.HOUR_OF_DAY, _hour);
                    alarmCal.set(Calendar.MINUTE, _minute);
                    alarmCal.set(Calendar.SECOND, 0);
                    Log.e(alarmCal.getTime().toString(), "check");

                    myIntent.putExtra("extra", "on");
                    myIntent.putExtra("id", id);
                    PendingIntent pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pending_intent);
                } else {
                    curr = dbHelper.getReminder(id);
                    String originalTime = curr.getTime();
                    _year = 100 + Integer.parseInt(originalTime.substring(6, 8));
                    _month = Integer.parseInt(originalTime.substring(0, 2)) - 1;
                    _day = Integer.parseInt(originalTime.substring(3, 5));
                    _hour = Integer.parseInt(originalTime.substring(9, 11));
                    _minute = Integer.parseInt(originalTime.substring(12, 14));
                    Date thisTime = new Date(_year, _month, _day, _hour, _minute);
                    Intent myIntent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
                    Log.e("year check", Integer.toString(_year));
                    Log.e("time check", new Date().toString());
                    Log.e("time check2", thisTime.toString());


                    if (new Date().before(thisTime)) {
                        myIntent.putExtra("extra", "pre-off");
                    } else {
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

//        cancel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent data = new Intent();
//                setResult(RESULT_OK, data);
//                finish();
//                overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
//            }
//        });

//        stop.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//
//            }
//        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myInflater = getMenuInflater();
        myInflater.inflate(R.menu.reminder_menu, menu);
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
        } else if (item.getItemId() == R.id.reminder_menu_delete) {
            dbHelper.deleteReminder((long) id);
            Intent myIntent = new Intent(Reminder.getInstance(), Alarm_Receiver.class);
            myIntent.putExtra("extra", "cancel");
            myIntent.putExtra("id", id);
            PendingIntent pending_intent = PendingIntent.getBroadcast(Reminder.getInstance(), id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            sendBroadcast(myIntent);

            ((AlarmManager) getSystemService(ALARM_SERVICE)).cancel(pending_intent);
            timer.cancel();
            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
            overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
            return true;
        } else if (item.getItemId() == R.id.reminder_menu_send) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText email = new EditText(context);
            alert.setMessage("Enter email");
            alert.setTitle("Enter email");
            alert.setView(email);
            alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String sendTo = email.getText().toString();
                    String[] to = sendTo.split(";");
                    String subject = "A Message From InMinder";
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "TEST");
                    emailIntent.setType("message/rfc822");
                    startActivity(Intent.createChooser(emailIntent, "Email"));
                    dialog.dismiss();
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });

            alert.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public boolean onSupportNavigateUp(){
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
        overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request_Code) {
            if (resultCode == RESULT_OK) {
                dbHelper = DBHelper.getInstance(getApplicationContext());
                dbHelper.onOpen(db);
                curr = dbHelper.getReminder(id);
                name.setText(curr.getName());
                getSupportActionBar().setTitle(curr.getName());
                notes.setText(curr.getNote());
                time.setText(curr.getTime());
                timer.cancel();
                countdown(curr.getTime());
                toggle.setChecked(true);
            }
        }
    }

    public void countdown(String thisTime){

        _year = 100 + Integer.parseInt(thisTime.substring(6, 8));
        _month = Integer.parseInt(thisTime.substring(0, 2)) - 1;
        _day = Integer.parseInt(thisTime.substring(3, 5));
        _hour = Integer.parseInt(thisTime.substring(9, 11));
        _minute = Integer.parseInt(thisTime.substring(12, 14));

        Log.e("scheduled time", (new Date(_year,_month,_day,_hour,_minute)).toString());
        Log.e("curr time", (new Date()).toString());
        int millis = (int)((new Date(_year,_month,_day,_hour,_minute)).getTime() - (new Date()).getTime());
        if (millis>=0) {
            long diffSeconds = millis / 1000 % 60;
            long diffMinutes = millis / (60 * 1000) % 60;
            long diffHours = millis / (60 * 60 * 1000) % 24;
            long diffDays = millis / (24 * 60 * 60 * 1000);
            daysLeft.setText(Long.toString(diffDays)+" Days ");
            Typeface mycustomFont = Typeface.createFromAsset(getAssets(), "fonts/Nawabiat.ttf");
            daysLeft.setTypeface(mycustomFont, Typeface.BOLD);
            daysLeft.setTextSize(60);
            String currUntilFuture = diffHours + ":" + diffMinutes + ":" + diffSeconds;
            if (diffDays==0){
                counter.setTextColor(Color.RED);
            }else{
                counter.setTextColor(Color.BLACK);
            }
            counter.setText(currUntilFuture);
            timer = new CounterClass(millis, 1000);
            timer.start();
        }else{
            counter.setText("Time's Up");
        }
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public void onTick(long millisUntilFinished){
            long millis = millisUntilFinished;
            String hms = String.format(Locale.US,"%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            counter.setText(hms);

        }

        public void onFinish(){
            counter.setText("Time's Up");
        }

    }



}