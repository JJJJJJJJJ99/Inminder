package edu.brandeis.jjwang95.inminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.DialogInterface;
import android.os.Build;
import android.os.IBinder;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.util.Log;
import android.app.PendingIntent;
import android.annotation.TargetApi;
/**
 * Created by Sylar on 11/27/16.
 */

public class RingTonePlayingService extends Service {


    MediaPlayer media_song;
    Boolean isRunning;
    public IBinder onBind(Intent intent){
        Log.e("in Service", "OK");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public int onStartCommand(Intent intent, int flags, int startId){
        NotificationManager notify_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent_to_reminder = new Intent(RingTonePlayingService.this, ReminderDetail.class);
        intent_to_reminder.putExtras(intent.getExtras());
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent_to_reminder, 0);


        Notification notification_popup = new Notification.Builder(this)
                    .setContentTitle("ReminderAlarm")
                    .setContentText("Time's Up")
                    .setContentIntent(pIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();



        String state = intent.getExtras().getString("extra");
        String id = Integer.toString(intent.getExtras().getInt("id"));
        if (state.equals("on")) {
            media_song = MediaPlayer.create(this, R.raw.jack_sparrow);
            Log.e("Start Sound, ID: ", id);
            media_song.start();
            isRunning = true;
            media_song.setLooping(true);
            notify_manager.notify(0, notification_popup);
        }else if(state.equals("off")){
            Log.e("Stop Sound, ID: ", id);
            media_song.stop();
            isRunning = false;
            media_song.reset();
        }else{
            Log.e("Cancel Sound, ID: ", id);
            isRunning = false;
        }
        return START_NOT_STICKY;
    }

    public void onDestroy(){
        Toast.makeText(this, "On DEstroy", Toast.LENGTH_SHORT).show();
    }
}
