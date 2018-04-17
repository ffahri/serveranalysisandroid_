package com.webischia.serveranalysis;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.webischia.serveranalysis.R;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"ff");
        builder.setAutoCancel(true)
                //.setDefaults(Notification.DEFAULT_ALL)
                //.setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)//icon
                .setContentTitle("title")//başlık
                .setContentText("Alarm")//bilgi
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setDefaults(Notification.DEFAULT_LIGHTS)//sses ve görüntüyü getir
                .setContentInfo("Info");//sagdaki yazı
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    public Alarm(Context context, Intent intent) {
        onReceive(context,intent);
    }
}
