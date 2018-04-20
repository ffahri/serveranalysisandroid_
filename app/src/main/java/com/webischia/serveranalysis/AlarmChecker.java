package com.webischia.serveranalysis;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AlarmChecker extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.webischia.serveranalysis.action.FOO";
    private static final String ACTION_BAZ = "com.webischia.serveranalysis.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.webischia.serveranalysis.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.webischia.serveranalysis.extra.PARAM2";

    public AlarmChecker() {
        super("AlarmChecker");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, AlarmChecker.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, AlarmChecker.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {//servis başlıyor activity açık
        int i = 0;
        if (intent == null) {//uygulama kapandıgında bu ife giriyor
            while (i < 1) {

                Log.v("i", "activitye kapalı:"+i);

                if (i == 200000) {
//alarm çağırma
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.mipmap.ic_launcher) //Bildirim resmi
                                    .setContentTitle("Kapandı")   //Bildirimin başlığı
                                    .setColor(0)//bildirim resmi arka plan rengi argb(red green blue)// cinsinden
                                    .setContentText("Bildirim metni");   //Bildirim başlığı altındaki yazı

                    // Add as notification
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(0, builder.build());

                    break;
                }

            }
            Log.v("activitye kapalı", "activitye kapalı:");
            return;// retunr olması lazım
        }



        else
        //activtiy açıkken burası
        {
            while (i < 1) {
//Activity açıkken bıraya giriyor
                Log.v("activitye acık", "activitye acık:");
            }
            // Alarm a = new Alarm(context,intent);


        }
    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}