package com.webischia.serveranalysis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.webischia.serveranalysis.Controls.SaveControl;
import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.SaveService;
import com.webischia.serveranalysis.Service.SaveServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class CreateGraphic extends AppCompatActivity implements SaveControl {

    SaveControl saveControl;
    SaveService saveService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_graphic);
        saveControl = new CreateGraphic();
        saveService = new SaveServiceImpl(saveControl,this);
    }
    public void createGraph(View view){


        EditText name = (EditText) findViewById(R.id.editText_graphicname);
        EditText metric = (EditText) findViewById(R.id.editText_metricname);
        EditText mode = (EditText) findViewById(R.id.editText_mode);
        EditText time = (EditText) findViewById(R.id.editText_time);
        Graphic newGraph = new Graphic(metric.getText().toString(),mode.getText().toString(),time.getText().toString(),name.getText().toString());
        saveService.saveGraphics(newGraph,getIntent().getExtras().getString("username"));


    }

    @Override
    public void successSave(String name,Context context) {
        Log.d("test","OK!");
        Toast.makeText(context, "Graphic Saved !", Toast.LENGTH_SHORT).show();
        Intent showGraphicIntent = new Intent(context,ShowGraphic.class);
        showGraphicIntent.putExtra("graphName",name);
        context.startActivity(showGraphicIntent);//contexti ref göstererek başlattım.
        finish(); //bu aktiviteyi kapat

    }

    @Override
    public void loadGraphs(ArrayList graphs , Context context,String username ,String token){
    }
}
