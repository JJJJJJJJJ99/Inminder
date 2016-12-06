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
    public IBinder onBind(Intent intent){
        Log.e("in Service", "OK");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public int onStartCommand(Intent intent, int flags, int startId){
        String state = intent.getExtras().getString("extra");
        int id = intent.getExtras().getInt("id");
        if (state.equals("on")) {
            NotificationManager notify_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent intent_to_reminder = new Intent(RingTonePlayingService.this, ReminderDetail.class);
            intent_to_reminder.putExtra("id", id);
            PendingIntent pIntent = PendingIntent.getActivity(this, id, intent_to_reminder, 0);
            Notification notification_popup = new Notification.Builder(this)
                    .setContentTitle("InMinder Alarm")
                    .setContentText("Time's Up! Click For Details!")
                    .setContentIntent(pIntent)
                    .setSmallIcon(R.mipmap.ic_alarm_white_24dp)
                    .build();
            media_song = MediaPlayer.create(this, R.raw.jack_sparrow);
//            Log.e("Start Sound, ID: ", id);
            media_song.start();
            media_song.setLooping(true);
            notify_manager.notify(0, notification_popup);
        }else if(state.equals("off")){
//            Log.e("Stop Sound, ID: ", id);
            media_song.stop();
            media_song.reset();
        }else{
//            Log.e("Cancel Sound, ID: ", id);
        }
        return START_NOT_STICKY;
    }

    public void onDestroy(){
        Toast.makeText(this, "On DEstroy", Toast.LENGTH_SHORT).show();
    }
}
