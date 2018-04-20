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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.webischia.serveranalysis.Controls.QueryControl;

import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.QueryService;
import com.webischia.serveranalysis.Service.QueryServiceImpl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
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
        ArrayList<Entry> xValues = null ;

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
        if(getIntent().getParcelableArrayListExtra("values") != null && getIntent().getParcelableArrayListExtra("xValues") != null)
            yValues = getIntent().getParcelableArrayListExtra("values");
            xValues = getIntent().getParcelableArrayListExtra("xValues");
            ArrayList<String> tmp =new ArrayList<String>();
            tmp.add(graphic.getQuery());
        editGraph(yValues,xValues,tmp);

        //utku
       // i.putExtra()



//max çizgisini intentten gönder

    }
    private void editGraph(ArrayList yValues,final ArrayList xValues,ArrayList label)
    {
        linechart1 = (LineChart) findViewById(R.id.linechart); //xml den java classına çağırdık
        linechart1.setDragEnabled(true);
        linechart1.setScaleEnabled(true);
        linechart1.getAxisRight().setEnabled(false);//sağ ekseni disable ettik
        linechart1.getAxisLeft().setEnabled(true);//sol ekseni disable ettik
        linechart1.getAxisRight().setDrawGridLines(true);
        linechart1.getAxisLeft().setDrawGridLines(true);
        //linechart1.getAxisRight()
        linechart1.setPinchZoom(true);
        linechart1.setDrawGridBackground(false);
        linechart1.setDescription(graphic.getQuery());
        //linechart1.getAxisLeft().et
        XAxis xAxis = linechart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);

//utku
        Intent utku=new Intent(this,AlarmChecker.class);
        if(graphic.getThreshold()!=null) {
            LimitLine upper_limit = new LimitLine(graphic.getThreshold(), "Danger");//todo graph threshold
            utku.getLongExtra("limit",graphic.getThreshold());
            upper_limit.setLineWidth(4f);
            upper_limit.enableDashedLine(10f, 10f, 0f);
            upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
            upper_limit.setTextSize(15f);
            YAxis leftAxis = linechart1.getAxisLeft();
            leftAxis.removeAllLimitLines();
            leftAxis.addLimitLine(upper_limit);
            leftAxis.enableGridDashedLine(10f, 10f, 0);
            leftAxis.setDrawLimitLinesBehindData(true);
        }
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        Log.d("y.values.size",""+yValues.size());
        for(int i = 0 ; i<yValues.size() ; i++) {
            LineDataSet set1 = new LineDataSet((ArrayList)yValues.get(i), "");// sol altta yazan yazı
            //linechart1.setDescription();
            set1.calcMinMax();
            //set1.la
            //   set1.setLabel(graphic.getQuery());
            set1.setFillAlpha(1100);
            set1.setCircleRadius(1);
            set1.setDrawCircles(false);
            set1.disableDashedLine();
            set1.setValueTextSize(0);
            set1.setLineWidth(3);
            if(i%2==0)
                set1.setColor(Color.GREEN);// çizgi rengi
            else
                set1.setColor(Color.RED);
            final ArrayList<String> temp = (ArrayList)xValues.get(i);
            xAxis.setValueFormatter(new AxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return (String)temp.get((int) value % temp.size());
                }

                @Override
                public int getDecimalDigits() {
                    return 0;
                }
            });

            dataSets.add(set1);// çizginin oluştuğu kısım heralde tam kontrol etmedim
        }
        LineData data = new LineData(dataSets);
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


        final ArrayList<ArrayList<Entry>> yList = new ArrayList<ArrayList<Entry>>();
        final ArrayList<ArrayList<String>> xList = new ArrayList<ArrayList<String>>();
        final ArrayList<String> metrNam = new ArrayList<>();

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
                                Log.d("json.arr.length",""+jsonArr.length());
                                for(int a = 0 ; a<jsonArr.length();a++) {
                                    JSONObject jsonArr2 = jsonArr.getJSONObject(a);
                                    ArrayList<Entry> yValues2 = new ArrayList<Entry>();
                                    ArrayList<String> xValues = new ArrayList<String>();
                                    metrNam.add(jsonArr2.getJSONObject("metric").getString("mode"));//todo mode sadece node_cpu için
                                    JSONArray jsonArr3 = jsonArr2.getJSONArray("values");
                                    for (int ii = 0; ii < jsonArr3.length(); ii++) {

                                        JSONArray js3 = jsonArr3.getJSONArray(ii);

                                        Log.d("js3", js3.toString());

                                        //Long axes_x = js3.getLong(1);
                                        Double timeDouble = js3.getDouble(0) * 1000;
                                        Long timestamp = (timeDouble.longValue());

                                        //Float x = Float.parseL(axes_x);
                                        if (graphic.getQuery().equals("node_load1")) {
                                            Double axes_x = js3.getDouble(1);
                                            yValues2.add(new Entry(ii, axes_x.floatValue())); //x 0 y 60 olsun f de float f si
                                        } else if (graphic.getQuery().equals("node_memory_MemFree") || graphic.getQuery().equals("node_memory_Cached") || graphic.getQuery().equals("node_memory_Active")) {
                                            Long axes_x = js3.getLong(1);
                                            axes_x = axes_x / 1000000;
                                            Log.d("if.qimp", "böldüm");
                                            yValues2.add(new Entry(ii, axes_x)); //x 0 y 60 olsun f de float f si

                                        } else {
                                            Long axes_x = js3.getLong(1);
                                            yValues2.add(new Entry(ii, (Long) axes_x)); //x 0 y 60 olsun f de float f si

                                        }
//                                    if(graphic.getQuery().equals("node_memory_MemFree") || graphic.getQuery().equals("node_memory_Cached") || graphic.getQuery().equals("node_memory_Active"))
//                                        axes_x = axes_x /1000000;// 1000000;
                                        Date date = new Date(timestamp);
                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                        //sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
                                        String formattedDate = sdf.format(date);

                                        //yValues2.add(new Entry(ii,axes_x)); //x 0 y 60 olsun f de float f si
                                        xValues.add(formattedDate);

                                    }
                                    yList.add(yValues2);
                                    xList.add(xValues);
                                }
                                Toast.makeText(ShowGraphic.this,"Refreshed",Toast.LENGTH_SHORT).show();

                                ////////////////// GRAFIK
                                editGraph(yList,xList,metrNam);
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

        i= new Intent(ShowGraphic.this,Alarm.class); //intent için class çağırdık
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
