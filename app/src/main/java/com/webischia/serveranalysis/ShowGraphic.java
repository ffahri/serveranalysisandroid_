package com.webischia.serveranalysis;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.webischia.serveranalysis.Controls.QueryControl;
import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.QueryService;
import com.webischia.serveranalysis.Service.QueryServiceImpl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowGraphic extends AppCompatActivity{
    LineChart linechart1; //xml den grafik ekranını çıktı aldık
    Graphic graphic;
    Intent i;
    PendingIntent pen_i;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graphic);
        ArrayList<Entry> yValues = null ;
        ArrayList k = getIntent().getParcelableArrayListExtra("graphic");
        if(k == null)
        {
            //getIntent().getExtras().getString("graphName");

        }
        else
        graphic = (Graphic)k.get(0);

        if(getIntent().getParcelableArrayListExtra("values") != null)
            yValues = getIntent().getParcelableArrayListExtra("values");
        editGraph(yValues);


    }
    private void editGraph(ArrayList yValues)
    {
        linechart1 = (LineChart) findViewById(R.id.linechart); //xml den java classına çağırdık
        linechart1.setDragEnabled(true);
        linechart1.setScaleEnabled(false);

        LineDataSet set1 = new LineDataSet(yValues,"Data set y");// sol altta yazan yazı

        set1.setFillAlpha(110);

        set1.setColor(Color.RED);// çizgi rengi

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);// çizginin oluştuğu kısım heralde tam kontrol etmedim

        LineData data = new LineData(dataSets);
        linechart1.setData(data); // programa ekliyor
    }
    public void refresh(View view)
    {

        ArrayList k = this.getIntent().getParcelableArrayListExtra("graphic");
        if(k == null)
        {
            //getIntent().getExtras().getString("graphName");
            Log.d("kkkk","K NULL");

        }
        else
            graphic = (Graphic)k.get(0);
        final String token = getIntent().getExtras().getString("token");
        try {
            final RequestQueue queue = Volley.newRequestQueue(this);  // this = context

            String url = "https://java.webischia.com/api/v1/metric/"+graphic.httpForm();
            Log.d("query_url",url);
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            ArrayList<Entry> yValues = new ArrayList<>();


                            try {

                                JSONObject jsonObj = new JSONObject(response);
                                JSONObject jsonObj2 = jsonObj.getJSONObject("data");
                                JSONArray jsonArr = jsonObj2.getJSONArray("result");
                                JSONObject jsonArr2 = jsonArr.getJSONObject(0);
                                JSONArray jsonArr3 = jsonArr2.getJSONArray("values");
                                for(int ii = 0 ; ii < 12 ; ii++)
                                {
                                    JSONArray js3 = jsonArr3.getJSONArray(ii);
                                    Log.d("js3", js3.toString());

                                    String axes_x = js3.getString(1);
                                    Date timestamp = new Date(js3.getLong(0)*1000L);
                                    Float x = Float.parseFloat(axes_x);
                                    // x = x/100000000;
                                    yValues.add(new Entry((float)timestamp.getSeconds(),x)); //x 0 y 60 olsun f de float f si
                                    Toast.makeText(ShowGraphic.this,"Refreshed",Toast.LENGTH_SHORT).show();

                                }

                                ////////////////// GRAFIK
                                editGraph(yValues);
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
                            Log.d("Error.Response","error");


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
                    else
                        return null;
                }
            };
            queue.add(postRequest);
        }
        catch(Exception e)
        {
            Log.d("Error?",e.getMessage());

        }
    }
    //alarm
    private void startAlarm() {

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);//alarm servisini ekledik

        i= new Intent(ShowGraphic.this,alarm.class); //intent için class çağırdık
        pen_i = PendingIntent.getBroadcast(this,0,i,0);//pendingintente ekran çıktı almamızı sağlıyor

        manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,pen_i);//ekrana bildirimi göstermek için

    }

}
