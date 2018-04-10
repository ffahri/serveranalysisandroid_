package com.webischia.serveranalysis;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.webischia.serveranalysis.Controls.QueryControl;
import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.QueryService;
import com.webischia.serveranalysis.Service.QueryServiceImpl;

import java.util.ArrayList;

public class ShowGraphic extends AppCompatActivity implements QueryControl{
    QueryControl queryControl;
    QueryService queryService;
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
        queryControl = new ShowGraphic();
        queryService = new QueryServiceImpl(queryControl,this);
        linechart1 = (LineChart) findViewById(R.id.linechart); //xml den java classına çağırdık

        //getIntent().getExtras().getString("username")
        startAlarim();

        ArrayList k = getIntent().getParcelableArrayListExtra("graphic");
        if(k == null)
        {
            //getIntent().getExtras().getString("graphName");

        }
        else
        graphic = (Graphic)k.get(0);
        //graifk utku
        //put extra içine graph koyup query yaptırırız burada
        linechart1.setDragEnabled(true);
        linechart1.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>(); // y değerleri
        yValues.add(new Entry(0,60f)); //x 0 y 60 olsun f de float f si
        yValues.add(new Entry(1,100f));
        yValues.add(new Entry(2,40f));
        yValues.add(new Entry(3,72f)); // x3 y 30 olsun


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
        //queryService.refresh();
    }
    //alarm
    private void startAlarim() {

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);//alarm servisini ekledik

        i= new Intent(ShowGraphic.this,alarm.class); //intent için class çağırdık
        pen_i = PendingIntent.getBroadcast(this,0,i,0);//pendingintente ekran çıktı almamızı sağlıyor

        manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,pen_i);//ekrana bildirimi göstermek için

    }

    @Override
    public void successQuery(ArrayList list) {
        //diğer kısımlarda hata var deneyemedik UNIT TEST de yazmadım
/*

        //yValues.add(new Entry((float)timestamp.getMinutes(),x)); //x 0 y 60 olsun f de float f si
        LineDataSet set1 = new LineDataSet(list,"CPU KULLANIMI");// sol altta yazan yazı

        set1.setFillAlpha(110);
        set1.setColor(Color.RED);// çizgi rengi

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);// çizginin oluştuğu kısım heralde tam kontrol etmedim

        LineData data = new LineData(dataSets);
        linechart1.setData(data); // programa ekliyor
        */
        //utku



    }
}
