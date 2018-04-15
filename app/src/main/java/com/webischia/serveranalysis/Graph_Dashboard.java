package com.webischia.serveranalysis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.webischia.serveranalysis.Controls.QueryControl;
import com.webischia.serveranalysis.Controls.SaveControl;
import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.QueryService;
import com.webischia.serveranalysis.Service.QueryServiceImpl;
import com.webischia.serveranalysis.Service.SaveService;
import com.webischia.serveranalysis.Service.SaveServiceImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Graph_Dashboard extends AppCompatActivity implements SaveControl,QueryControl {
    SaveControl saveControl;
    SaveService saveService;
    QueryControl queryControl;
    QueryService queryService;
    LinearLayout ll;
    LinearLayout.LayoutParams lp;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph__dashboard);
        ll = findViewById(R.id.graph_dash_ll);
        Button a = findViewById(R.id.create_graphic);
        //  lp = lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
        saveControl = new Graph_Dashboard();
        saveService = new SaveServiceImpl(saveControl, this);
        queryControl = new Graph_Dashboard();
        queryService = new QueryServiceImpl(queryControl,this);
        ArrayList graphs = getIntent().getParcelableArrayListExtra("graphs");
        if(graphs == null) {
            saveService.loadNames(getIntent().getExtras().getString("username"), getIntent().getExtras().getString("token"),getIntent().getExtras().getString("serverIP"));
            Log.d("graphs","null");
        }
        if (graphs != null && ll != null) {
            Log.d("G_DASHB","null değil");
            for (int i = 0; i < graphs.size(); i++) {
                Button temp = new Button(this);
                temp.setLayoutParams(a.getLayoutParams());
                final Graphic tmp = (Graphic) graphs.get(i);
                temp.setText(tmp.getName());
                temp.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        queryService.doQuery(tmp,getIntent().getExtras().getString("token"),
                                getIntent().getExtras().getString("username"),getIntent().getExtras().getString("serverIP"));
                        finish();
                    }
                });
                ll.addView(temp);
            }
        }

    }

    public void createGraphicButton(View view) {
        Intent crtgrph = new Intent(this, CreateGraphic.class);
        crtgrph.putExtra("token", getIntent().getExtras().getString("token"));
        crtgrph.putExtra("username", getIntent().getExtras().getString("username"));
        crtgrph.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        startActivity(crtgrph);
        finish(); //bu aktiviteyi kapat
    }

    @Override
    public void successSave(String name, Context context,String username,String token,String serverIP) {

    }

    @Override
    public void loadGraphs(ArrayList graphs, Context context, String username, String token,String serverIP) {
        //gelen listedeki graph.getName() ile liste oluşturulacak button ile tıklanacak
        if (graphs.size() > 0) {
            Intent graphDash = new Intent(context, Graph_Dashboard.class);
            graphDash.putParcelableArrayListExtra("graphs", graphs);
            graphDash.putExtra("token", token);
            graphDash.putExtra("username", username);
            graphDash.putExtra("serverIP",serverIP);
            context.startActivity(graphDash);
            finish(); //bu aktiviteyi kapat
            //at least it works
        }

    }

    @Override
    public void successQuery(ArrayList list, Context context,Graphic tmp,String username,String token,String serverIP) {

        Intent showGraphic = new Intent(context, ShowGraphic.class);
        showGraphic.putParcelableArrayListExtra("values", list);
        ArrayList tempList = new ArrayList<Graphic>();//should use parcelableobject todo
        tempList.add(tmp);
        showGraphic.putParcelableArrayListExtra("graphic", tempList);
        showGraphic.putExtra("username",username);
        showGraphic.putExtra("serverIP",serverIP);
        showGraphic.putExtra("token",token);
        context.startActivity(showGraphic);
        ((Activity)getApplicationContext()).finish(); //bu aktiviteyi kapat
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String username = getIntent().getExtras().getString("username");
        String token = getIntent().getExtras().getString("token");
        Intent dashboardIntent = new Intent(this,Dashboard.class);
        dashboardIntent.putExtra("token",token);
        dashboardIntent.putExtra("username",username);
        dashboardIntent.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));

        startActivity(dashboardIntent);//contexti ref göstererek başlattım.
        finish(); //bu aktiviteyi kapat

    }
}
