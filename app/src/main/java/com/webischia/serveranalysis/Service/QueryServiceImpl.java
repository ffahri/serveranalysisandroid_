package com.webischia.serveranalysis.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry;
import com.webischia.serveranalysis.Controls.QueryControl;
import com.webischia.serveranalysis.Models.Graphic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Query metotlarının yazıldığı ve güvenli bir şekilde servera iletilip rest cevabı
olan json dosyasının parse edildiği sınıf
 **/

public class QueryServiceImpl implements QueryService {
    QueryControl queryControl;
    Context context;

    public QueryServiceImpl(QueryControl queryControl, Context context) {
        this.queryControl = queryControl;
        this.context = context;
    }

    @Override
    public void doQuery(final Graphic graphic, final String token, final String username,final String serverIP) {
        RequestQueue mRequestQueue;
        try {


            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
// Set up the network to use HttpURLConnection as the HTTP client.
            Network network = new BasicNetwork(new HurlStack());
// Instantiate the RequestQueue with the cache and network.
            mRequestQueue = new RequestQueue(cache, network);
           // final RequestQueue queue = Volley.newRequestQueue(context);  // this = context
            mRequestQueue.start();

            String url = "https://"+serverIP+"/api/v1/metric/"+graphic.httpForm();
            Log.d("query_url",url);
            //Log.d("username",username);
           // Log.d("token",token);

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            ArrayList<Entry> yValues = new ArrayList<>();
                            ArrayList<String> xValues = new ArrayList<>();

                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                JSONObject jsonObj2 = jsonObj.getJSONObject("data");
                                JSONArray jsonArr = jsonObj2.getJSONArray("result");
                                JSONObject jsonArr2 = jsonArr.getJSONObject(0);
                                JSONArray jsonArr3 = jsonArr2.getJSONArray("values");
                                Log.d("id",""+jsonArr3.length());

                                for(int ii=0 ; ii<jsonArr3.length() ; ii++){
                                    JSONArray js3 = jsonArr3.getJSONArray(ii);

                                    Double timestamp = (new Double(js3.getDouble(0))*1000);
                                    Long timeLong = timestamp.longValue();//%1000000
                                    Date date = new Date(timeLong);
                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                    String formattedDate = sdf.format(date);
                                    if(graphic.getQuery().equals("node_load1")) {
                                        Double axes_x = js3.getDouble(1);
                                        yValues.add(new Entry(ii, axes_x.floatValue())); //x 0 y 60 olsun f de float f si
                                    }
                                    else if(graphic.getQuery().equals("node_memory_MemFree") || graphic.getQuery().equals("node_memory_Cached") || graphic.getQuery().equals("node_memory_Active")) {
                                        Long axes_x = js3.getLong(1);
                                        axes_x = axes_x / 1000000;
                                        Log.d("if.qimp","böldüm");
                                        yValues.add(new Entry(ii,axes_x)); //x 0 y 60 olsun f de float f si

                                    }
                                    else
                                    {
                                        Long axes_x = js3.getLong(1);
                                        yValues.add(new Entry(ii,axes_x)); //x 0 y 60 olsun f de float f si

                                    }
                                    xValues.add(formattedDate);
                                }
                                queryControl.successQuery(xValues,yValues,context,graphic,username,token,serverIP);
                                ////////////////// GRAFIK

                            }
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
        }

    }
}
