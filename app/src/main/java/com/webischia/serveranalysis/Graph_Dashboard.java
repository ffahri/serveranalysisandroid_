package com.webischia.serveranalysis;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.webischia.serveranalysis.Controls.SaveControl;

import java.util.List;

public class Graph_Dashboard extends AppCompatActivity implements SaveControl{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph__dashboard);
    }

    @Override
    public void successSave(String name, Context context) {

    }

    @Override
    public void loadGraphs(List graphs , Context context) {
        //gelen listedeki graph.getName() ile liste oluşturulacak button ile tıklanacak
    }
}
