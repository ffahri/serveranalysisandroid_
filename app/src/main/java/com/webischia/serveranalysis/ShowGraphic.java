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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
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
    Context mContext;
    String token;
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
        mContext = this;
        token = getIntent().getExtras().getString("token");
        if(k == null)
        {
            //getIntent().getExtras().getString("graphName");

        }
        else
        graphic = (Graphic)k.get(0);
        TextView temp = (TextView)findViewById(R.id.graphic_name_tv);
        temp.setText(graphic.getName());
        if(getIntent().getParcelableArrayListExtra("values") != null)
            yValues = getIntent().getParcelableArrayListExtra("values");
        editGraph(yValues);


    }
    private void editGraph(ArrayList yValues)
    {
        linechart1 = (LineChart) findViewById(R.id.linechart); //xml den java classına çağırdık
        linechart1.setDragEnabled(true);
        linechart1.setScaleEnabled(false);
        linechart1.getAxisRight().setEnabled(false);//sağ ekseni disable ettik
        linechart1.getAxisLeft().setEnabled(true);//sol ekseni disable ettik
        linechart1.getAxisRight().setDrawGridLines(true);
        linechart1.getAxisLeft().setDrawGridLines(true);
        //linechart1.getAxisRight()
        linechart1.setPinchZoom(true);
        linechart1.setDrawGridBackground(false);
        //linechart1.getAxisLeft().et
        XAxis xAxis = linechart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);

        xAxis.setDrawGridLines(true);



        LineDataSet set1 = new LineDataSet(yValues,graphic.getQuery());// sol altta yazan yazı
        //linechart1.setDescription();

        set1.setFillAlpha(1100);
        set1.setCircleRadius(10);
        set1.setValueTextSize(10);
        set1.setLineWidth(3);
        set1.setColor(Color.GREEN);// çizgi rengi

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);// çizginin oluştuğu kısım heralde tam kontrol etmedim

        LineData data = new LineData(dataSets);
        //linechart1.getXAxis().setValueFormatter;
        linechart1.setData(data); // programa ekliyor
        linechart1.invalidate();
    }
    public void refresh(View view)
    {

        ArrayList k = getIntent().getParcelableArrayListExtra("graphic");
        if(k == null)
        {
            //getIntent().getExtras().getString("graphName");
            Log.d("kkkk","K NULL");

        }
        else
            graphic = (Graphic)k.get(0);

        final ArrayList<Entry> yValues2 = new ArrayList<Entry>();

        try {
            RequestQueue queue = Volley.newRequestQueue(this);  // this = context

            String url = "https://"+getIntent().getExtras().getString("serverIP")+"/api/v1/metric/"+graphic.httpForm();
            Log.d("query_url",url);

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);


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
                                    //Date timestamp = new Date(js3.getLong(0)*1000L);
                                    //Float timestamp = Float.parseFloat(js3.getString(0));
                                    Float timestamp = (float)(new Double(js3.getDouble(0)).longValue()%1000000);

                                    Float x = Float.parseFloat(axes_x);
                                    // x = x/100000000;
                                    yValues2.add(new Entry(timestamp,x)); //x 0 y 60 olsun f de float f si
                                    Toast.makeText(ShowGraphic.this,"Refreshed",Toast.LENGTH_SHORT).show();

                                }

                                ////////////////// GRAFIK
                                editGraph(yValues2);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent graphDash = new Intent(this,Graph_Dashboard.class);
        graphDash.putExtra("token",getIntent().getExtras().getString("token"));
        graphDash.putExtra("username",getIntent().getExtras().getString("username"));
        graphDash.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        graphDash.putParcelableArrayListExtra("graphs",null);
        this.finish();
        startActivity(graphDash);
        //grafikleri yükleyemiyoruz todo todo todooooo
         //bu aktiviteyi kapat

    }

}
