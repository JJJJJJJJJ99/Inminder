package edu.brandeis.jjwang95.inminder;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
/**
 * Created by Sylar on 11/27/16.
 */

public class Alarm_Receiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){
        String state = intent.getExtras().getString("extra");
        int id = intent.getExtras().getInt("id");
        Log.e("We are in the receiver", "DOne");
        Intent service_intent = new Intent(context, RingTonePlayingService.class);
        service_intent.putExtra("extra", state);
        service_intent.putExtra("id",id);
        context.startService(service_intent);
    }
}
