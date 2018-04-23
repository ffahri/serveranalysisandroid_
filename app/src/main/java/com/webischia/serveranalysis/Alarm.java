package com.webischia.serveranalysis;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.R;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, AlarmChecker.class);
        String username = intent.getExtras().getString("username");
        String token = intent.getExtras().getString("token");
        String serverIP = intent.getExtras().getString("serverIP");
        Log.d("alarm",""+username+"\n"+token+"\n"+"name"+"\n"+serverIP);

        i.putExtra("graphic", (Graphic)intent.getExtras().get("graphic"));
        i.putExtra("username",username);
        i.putExtra("token",token);
        i.putExtra("serverIP",serverIP);

        context.startService(i);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)//icon
//                .setContentTitle("ALARM - SERVERANALYSIS")//başlık
//                .setContentText("ALARM")//bilgi
//                //.setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
//                .setContentInfo("Info");//sagdaki yazı
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1, builder.build());
//        Log.d("Alarm", "ALARM GELDİ ?");
//        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
    }
//
//    public Alarm(Context context, Intent intent) {
//        onReceive(context,intent);
//    }
}
