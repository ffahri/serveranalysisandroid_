package com.webischia.serveranalysis;

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

import com.webischia.serveranalysis.Controls.SaveControl;
import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.SaveService;
import com.webischia.serveranalysis.Service.SaveServiceImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Graph_Dashboard extends AppCompatActivity implements SaveControl {
    SaveControl saveControl;
    SaveService saveService;
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
        ArrayList graphs = getIntent().getParcelableArrayListExtra("graphs");
        saveService = new SaveServiceImpl(saveControl, this);
        if(graphs == null)
            saveService.loadNames(getIntent().getExtras().getString("username"), getIntent().getExtras().getString("token"));
        if (graphs != null && ll != null) {
            context = getApplicationContext();
            for (int i = 0; i < graphs.size(); i++) {
                Button temp = new Button(this);
                temp.setLayoutParams(a.getLayoutParams());
                final Graphic tmp = (Graphic) graphs.get(i);
                temp.setText(tmp.getName());
                temp.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent showGraphic = new Intent(context, ShowGraphic.class);
                        showGraphic.putExtra("token", getIntent().getExtras().getString("token"));
                        showGraphic.putExtra("username", getIntent().getExtras().getString("username"));
                        ArrayList tempList = new ArrayList<Graphic>();//should use parcelableobject todo
                        tempList.add(tmp);
                        showGraphic.putParcelableArrayListExtra("graphic", tempList);
                        context.startActivity(showGraphic);
                        finish(); //bu aktiviteyi kapat
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
        startActivity(crtgrph);
        finish(); //bu aktiviteyi kapat
    }

    @Override
    public void successSave(String name, Context context) {

    }

    @Override
    public void loadGraphs(ArrayList graphs, Context context, String username, String token) {
        //gelen listedeki graph.getName() ile liste oluşturulacak button ile tıklanacak
        if (graphs.size() > 0) {
            Intent graphDash = new Intent(context, Graph_Dashboard.class);
            graphDash.putParcelableArrayListExtra("graphs", graphs);
            graphDash.putExtra("token", token);
            graphDash.putExtra("username", username);
            context.startActivity(graphDash);
            finish(); //bu aktiviteyi kapat
            //at least it works
        }

    }
}
