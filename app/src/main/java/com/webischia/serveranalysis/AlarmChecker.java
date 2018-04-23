package com.webischia.serveranalysis;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
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
import com.github.mikephil.charting.data.Entry;
import com.webischia.serveranalysis.Models.Graphic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AlarmChecker extends IntentService {

    String username;
    String token;
    String serverIP;
    Graphic graphic;

    public AlarmChecker() {
        super("AlarmChecker");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);

    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        super.onStartCommand(intent, flags, startId);
//
//        return START_STICKY;
//    }

    @Override
    protected void onHandleIntent(Intent intent) {//servis başlıyor activity açık
        int i = 0;
        Log.d("Alarm.Checker","SERVIS CHECK");
        RequestQueue mRequestQueue;

        //Log.d("Alarm.Checker","onStart");
        username = intent.getExtras().getString("username");
        token = intent.getExtras().getString("token");
        serverIP = intent.getExtras().getString("serverIP");
        Log.d("Alarm.Checker",intent.getExtras().getString("graphName"));
        graphic = (Graphic)intent.getExtras().get("graphic");

        Log.d("alarm",""+username+"\n"+token+"\n"+serverIP);
        if(graphic==null)
        Log.d("Alarm.Checker","null");
        final ArrayList<Long> xList = new ArrayList<Long>();
        try {


            Cache cache = new DiskBasedCache(this.getCacheDir(), 1024 * 1024); // 1MB cap
// Set up the network to use HttpURLConnection as the HTTP client.
            Network network = new BasicNetwork(new HurlStack());
// Instantiate the RequestQueue with the cache and network.
            mRequestQueue = new RequestQueue(cache, network);

            // final RequestQueue queue = Volley.newRequestQueue(context);  // this = context
            mRequestQueue.start();

            String url = "https://"+serverIP+"/api/v1/metric/"+graphic.getQuery();
            Log.d("query_url",url);
            //Log.d("username",username);
            // Log.d("token",token);

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
//                            ArrayList<Entry> yValues = new ArrayList<>();
//                            ArrayList<String> xValues = new ArrayList<>();
                            try {

                                JSONObject jsonObj = new JSONObject(response);
                                JSONObject jsonObj2 = jsonObj.getJSONObject("data");
                                JSONArray jsonArr = jsonObj2.getJSONArray("result");
                                for(int a = 0 ; a<jsonArr.length();a++) {
                                    JSONObject jsonArr2 = jsonArr.getJSONObject(a);
                                    JSONArray jsonArr3 = jsonArr2.getJSONArray("value");
                                        JSONArray js3 = jsonArr3.getJSONArray(0);
                                        Log.d("js3", js3.toString());
                                            Long axes_x = js3.getLong(1);
                                            axes_x = axes_x / 1000000;
                                    if(axes_x > graphic.getThreshold())
                                    {
                                        xList.add(axes_x);
                                    }



                                }

                                ////////////////// GRAFIK

                            }

//                            try {
//                                JSONObject jsonObj = new JSONObject(response);
//                                JSONObject jsonObj2 = jsonObj.getJSONObject("data");
//                                JSONArray jsonArr = jsonObj2.getJSONArray("result");
//                                JSONObject jsonArr2 = jsonArr.getJSONObject(0);
//                                JSONArray jsonArr3 = jsonArr2.getJSONArray("values");
//                                Log.d("id",""+jsonArr3.length());
//
//                                for(int ii=0 ; ii<jsonArr3.length() ; ii++){
//                                    JSONArray js3 = jsonArr3.getJSONArray(ii);
//
//                                    Double timestamp = (new Double(js3.getDouble(0))*1000);
//                                    Long timeLong = timestamp.longValue();//%1000000
//                                    Date date = new Date(timeLong);
//                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//                                    String formattedDate = sdf.format(date);
//                                    if(graphic.getQuery().equals("node_load1")) {
//                                        Double axes_x = js3.getDouble(1);
//                                        yValues.add(new Entry(ii, axes_x.floatValue())); //x 0 y 60 olsun f de float f si
//                                    }
//                                    else if(graphic.getQuery().equals("node_memory_MemFree") || graphic.getQuery().equals("node_memory_Cached") || graphic.getQuery().equals("node_memory_Active")) {
//                                        Long axes_x = js3.getLong(1);
//                                        axes_x = axes_x / 1000000;
//                                        Log.d("if.qimp","böldüm");
//                                        yValues.add(new Entry(ii,axes_x)); //x 0 y 60 olsun f de float f si
//
//                                    }
//                                    else
//                                    {
//                                        Long axes_x = js3.getLong(1);
//                                        yValues.add(new Entry(ii,(Long)axes_x)); //x 0 y 60 olsun f de float f si
//
//                                    }
//                                    xValues.add(formattedDate);
//                                }
//                                queryControl.successQuery(xValues,yValues,context,graphic,username,token,serverIP);
//                                ////////////////// GRAFIK
//
//                            }
                            catch (Exception e)
                            {
                                Log.d("Query_ERROR", e.getMessage());

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response",error.getMessage());


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

        }
        catch(Exception e)
        {
            Log.d("HATA.ALARMCHECKER",e.getMessage());
        }


        if(xList.size()>0 && xList.get(0)!=null) {
            if (xList.get(0) > graphic.getThreshold()) {
                Log.d("BULDUM","BULDUM");
                Toast.makeText(this, "???", Toast.LENGTH_SHORT).show();

            }
        }
        else
        {
            Log.d("Alarm.checker","HATA");
        }
    }


}/*
    if (intent == null) {//uygulama kapandıgında bu ife giriyor

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


            }
            Log.d("Alarm.checker",""+intent.getExtras().get("test"));
            return;// retunr olması lazım
        }



        else
        //activtiy açıkken burası
        {
//Activity açıkken bıraya giriyor
                Log.v("activitye acık", "activitye acık:");

            // Alarm a = new Alarm(context,intent);


        }
    */