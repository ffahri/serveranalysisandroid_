package com.webischia.serveranalysis;
//ALARIM SERVİS KISMI
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.webischia.serveranalysis.Models.Alarm;

import java.util.ArrayList;

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
    public void onDestroy() {
        super.onDestroy();
        Log.i("Kapandı","program kapandı");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("program açıldı","açık");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    //Arka plan için
    @Override
    protected void onHandleIntent(Intent intent) {//intent servisten gelen metod
        //activtiy kapalı olduğunda bu kısım çalışıyor
        int A_gelen = intent.getIntExtra("limit",100000);//Alarm limitten gelen// belki yeni bir intent tanımlamak lazım olabilir
        Bundle b=intent.getExtras();
      //  ArrayList<String> k_deger = b.getStringArrayList("data");// niye string geldi,long ? or int -> kontrol limitle karşılaşstır

        if (intent == null) {
            //if( karşılastır) alarm cagır control et
            Log.v("activitye kapalı", "activitye kapalı:"+A_gelen);
            return;
        //     Alarm a = new Alarm,getApplicationContext());
        }

        else
        //activtiy açıkken burası
        {
            Log.v("activitye kapalı", "activitye kapalı:"+A_gelen);
            // Alarm a = new Alarm(context,intent);

            return;
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