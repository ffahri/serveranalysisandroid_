package com.webischia.serveranalysis;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.github.mikephil.charting.data.Entry;
import com.webischia.serveranalysis.Global.GlobalClass;
import com.webischia.serveranalysis.Models.Graphic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by f on 23.04.2018.
 */

public class JobServiceAlarm extends JobService {

    ArrayList<Long> xList;

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d("JobSchedule","SCHEDULE");
        Bundle bundle = job.getExtras();
        final String tag,serverIP,query;
        String threshold;
        tag = job.getTag();
        serverIP = bundle.getString("serverIP");
        final String token = bundle.getString("token");
        query = bundle.getString("query");
        threshold = bundle.getString("threshold");
        final Long thr;
        xList = new ArrayList<>();
        if(threshold!=null) {
                thr = Long.parseLong(threshold);
        }
        else
            thr=0L;
        Log.d("JobSchedule",tag+"\n"+serverIP+"\n"+token+"\n"+query+"\n"+threshold);
        final String level = bundle.getString("level");
//      //  ArrayList<Graphic> tmp = (ArrayList)(job.getExtras().getParcelableArrayList("graphic"));
//        if(tmp!=null && tmp.size()>0) {
//            Graphic graphic = tmp.get(0);
//            Log.d("JobSchedule", graphic.getName());
//        }

///////////////////////////////////

        RequestQueue mRequestQueue;

        try {


            Cache cache = new DiskBasedCache(this.getCacheDir(), 1024 * 1024); // 1MB cap
// Set up the network to use HttpURLConnection as the HTTP client.
            Network network = new BasicNetwork(new HurlStack());
// Instantiate the RequestQueue with the cache and network.
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
            String url = "https://"+serverIP+"/api/v1/metric/"+query;
            Log.d("query_url",url);

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObj = new JSONObject(response);
                                JSONObject jsonObj2 = jsonObj.getJSONObject("data");
                                JSONArray jsonArr = jsonObj2.getJSONArray("result");
                                    JSONObject jsonArr2 = jsonArr.getJSONObject(0);
                                    JSONArray js3 = jsonArr2.getJSONArray("value");
//                                    JSONArray js3 = jsonArr3.getJSONArray(0);
                                    Log.d("js3", js3.toString());
                                    Long axes_x = js3.getLong(1);
                                    if(query.matches("node_memory_MemFree|node_memory_Cached|" +
                                            "node_memory_Active|node_memory_Active_anon|node_memory_Active_files|" +
                                            "node_memory_Buffers|node_memory_Inactive|node_memory_SwapFree")) {

                                        axes_x = axes_x / 1000000;
                                    }
                                    else if(query.matches("|node_network_receive_bytes|node_network_transmit_bytes"))
                                    {
                                        axes_x = axes_x / 1000;
                                    }
                                    else
                                    Log.d("axes.x",""+axes_x);
                                    xList.add(axes_x);
                                Long time= System.currentTimeMillis();

                                if(level.equals("0")) {
                                        if (axes_x > thr) {
                                            //Toast.makeText(JobServiceAlarm.this, "???", Toast.LENGTH_SHORT).show();
                                            Log.d("OK?", "K?");


                                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);

                                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
                                            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);//default ses

                                            Notification notification = new Notification.Builder(JobServiceAlarm.this)

                                                    .setContentTitle("Server Analysis - Above")
                                                    .setContentText("Alarm for " + tag + "  Alarm Value : " + axes_x)
                                                    //.setContentIntent(pendingIntent)
                                                    .setSound(alarmSound)//ses
                                                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})//titreşim
                                                    //.addAction(android.R.drawable., "Chat", pendingIntent)
                                                    .setLights(GlobalClass.color_user,1000,1000)

                                                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                                                    .build();

                                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                            notificationManager.notify(time.intValue(), notification);


                                        }
                                    }
                                else
                                    {
                                        if (axes_x < thr) {
                                            //Toast.makeText(JobServiceAlarm.this, "???", Toast.LENGTH_SHORT).show();
                                            Log.d("OK?", "K?");


                                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);

                                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
                                            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);//default ses

                                            Notification notification = new Notification.Builder(JobServiceAlarm.this)
                                                    .setContentTitle("Server Analysis - Below")
                                                    .setContentText("Alarm for " + tag + "  Alarm Value : " + axes_x)
                                                    .setSound(alarmSound)//ses
                                                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})//titreşim
                                                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                                                    .setLights(GlobalClass.color_user,1000,1000)
                                                    .build();

                                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                            notificationManager.notify(time.intValue(), notification);


                                        }
                                    }

                            }

                            catch (Exception e)
                            {
                                Log.d("Query_ERROR",""+e.getMessage());

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response",""+error.getMessage());


                        }
                    }
            ) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    if(token != null) {
                        params.put("Authorization", "Bearer "+token);

                        return params;
                    }
                    else {
                        Log.d("volley.headers","token null");
                        return null;
                    }
                }
            };
            mRequestQueue.add(postRequest);
//            if(xList.size()>0 && xList.get(0)!=null) {
//                if (xList.get(0) > thr) {
//                    Log.d("ALARM?","ALARMMM");
//                    Toast.makeText(this, "???", Toast.LENGTH_SHORT).show();
//
//                }
//                else
//                    Log.d("ALARM?","GEREK YOK");
//            }
//            else
//                Log.d("xList","null"+xList.size());


        }
        catch(Exception e)
        {
            Log.d("HATA.ALARMCHECKER",e.getMessage());
        }

        ////////////////////////////////


        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

}
