package com.webischia.serveranalysis;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graphic);
        queryControl = new ShowGraphic();
        queryService = new QueryServiceImpl(queryControl,this);
        //getIntent().getExtras().getString("username")

        ArrayList k = getIntent().getParcelableArrayListExtra("graphic");
        if(k == null)
        {
            //getIntent().getExtras().getString("graphName");

        }
        else
        graphic = (Graphic)k.get(0);
        //put extra içine graph koyup query yaptırırız burada
    }
    public void refresh(View view)
    {
        //queryService.refresh();
    }
    @Override
    public void successQuery(ArrayList list) {
        //diğer kısımlarda hata var deneyemedik UNIT TEST de yazmadım

        linechart1 = (LineChart) findViewById(R.id.linechart); //xml den java classına çağırdık

        //yValues.add(new Entry((float)timestamp.getMinutes(),x)); //x 0 y 60 olsun f de float f si
        LineDataSet set1 = new LineDataSet(list,"CPU KULLANIMI");// sol altta yazan yazı

        set1.setFillAlpha(110);
        set1.setColor(Color.RED);// çizgi rengi

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);// çizginin oluştuğu kısım heralde tam kontrol etmedim

        LineData data = new LineData(dataSets);
        linechart1.setData(data); // programa ekliyor

    }
}
