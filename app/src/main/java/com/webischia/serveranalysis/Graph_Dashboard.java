package com.webischia.serveranalysis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.webischia.serveranalysis.Controls.SaveControl;
import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.SaveService;
import com.webischia.serveranalysis.Service.SaveServiceImpl;

import java.util.List;

public class Graph_Dashboard extends AppCompatActivity implements SaveControl{
    SaveControl saveControl;
    SaveService saveService;
    LinearLayout ll;
    LinearLayout.LayoutParams lp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph__dashboard);
        ll = findViewById(R.id.graph_dash_ll);
      //  lp = lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
        saveControl = new Graph_Dashboard();
        saveService = new SaveServiceImpl(saveControl,this);
        saveService.loadNames(getIntent().getExtras().getString("username"));

    }
    public void createGraphicButton(View view)
    {
        Intent crtgrph = new Intent(this,CreateGraphic.class);
        crtgrph.putExtra("token",getIntent().getExtras().getString("token"));
        crtgrph.putExtra("username",getIntent().getExtras().getString("username"));
        startActivity(crtgrph);
        finish(); //bu aktiviteyi kapat
    }

    @Override
    public void successSave(String name, Context context) {

    }

    @Override
    public void loadGraphs(List graphs , Context context) {
        //gelen listedeki graph.getName() ile liste oluşturulacak button ile tıklanacak
        try {
            //added LInearLayout
            //added LayoutParams

            if (graphs.size() > 0 && ll != null) {
                for (int i = 0; i < graphs.size(); i++) {
                    Button temp = new Button(context);
                    temp.setText("a");
                    temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    Log.d("is null",""+temp.getText());
                    ll.addView(temp);
                }
            }
        }
        catch (Exception a)
        {
            a.printStackTrace();
        }

    }
}
