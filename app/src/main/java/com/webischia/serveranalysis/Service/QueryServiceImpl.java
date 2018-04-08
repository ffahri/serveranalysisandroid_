package com.webischia.serveranalysis.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry;
import com.webischia.serveranalysis.Controls.QueryControl;
import com.webischia.serveranalysis.Models.Graphic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    public void doQuery(Graphic graphic, final String token) {
        try {
            final RequestQueue queue = Volley.newRequestQueue(context);  // this = context

            String url = "http://192.168.122.160:9090/api/v1/query"+graphic.httpForm();
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);

                            try {
                                ArrayList<Entry> yValues = new ArrayList<>();
                                Intent i;
                                JSONObject jsonObj = new JSONObject(response);
                                JSONObject jsonObj2 = jsonObj.getJSONObject("data");
                                JSONArray jsonArr = jsonObj2.getJSONArray("result");
                                JSONObject jsonArr2 = jsonArr.getJSONObject(0);
                                JSONArray jsonArr3 = jsonArr2.getJSONArray("values");
                                for(int ii = 0 ; ii < 5 ; ii++)
                                {
                                    JSONArray js3 = jsonArr3.getJSONArray(ii);
                                    Log.d("js3", js3.toString());

                                    String axes_x = js3.getString(1);
                                    Date timestamp = new Date(js3.getLong(0)*1000L);
                                    Float x = Float.parseFloat(axes_x);
                                    // x = x/100000000;
                                    yValues.add(new Entry((float)timestamp.getSeconds(),x)); //x 0 y 60 olsun f de float f si

                                }
//



                                ////////////////// GRAFIK

                            }
                            catch (Exception e)
                            {
                                Log.d("hata", e.getMessage());

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response","error");


                        }
                    }
            ) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    if(token != null) {
                        params.put("Authorization", "Basic VXNlcjoxMjM0");

                        return params;
                    }
                    else
                        return null;
                }
            };
            queue.add(postRequest);
        }
        catch(Exception e)
        {
        }
    }
}
