package com.webischia.serveranalysis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.webischia.serveranalysis.Controls.SaveControl;
import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.SaveService;
import com.webischia.serveranalysis.Service.SaveServiceImpl;

import java.util.ArrayList;

public class CreateAlarm extends AppCompatActivity implements SaveControl {
    Button create_alarm;
    Intent i,j;
    EditText e_text;
    Graphic graphic;
    SaveControl saveControl;
    SaveService saveService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        saveControl = new CreateAlarm();
        saveService = new SaveServiceImpl(saveControl,this);
//        create_alarm= findViewById(R.id.c_alarm);
//        e_text= findViewById(R.id.editText);
//        create_alarm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int limit = Integer.parseInt(e_text.getText().toString());
//                i = new Intent(CreateAlarm.this,Dashboard.class);
//                j = new Intent(CreateAlarm.this,AlarmChecker.class);
//                j.putExtra("limit",limit);
//                startActivity(i);
//                finish();
//            }
//        });

        String[] timeSpinner = new String[] {
                "1m","5m","10m","30m","60m","300m"
        };
        Spinner s2 = (Spinner) findViewById(R.id.create_alarm_spinner1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,timeSpinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
        graphic = (Graphic) getIntent().getExtras().get("graphic");

        if(graphic!=null) {
            Log.d("Create.Alarm","k null değil");
            Button tmp = (Button)findViewById(R.id.ca_button);
            tmp.setText("UPDATE");
            TextView graph_name = (TextView)findViewById(R.id.ca_graph_name);
            graph_name.setText(graphic.getName());
            EditText threshold = (EditText) findViewById(R.id.ca_threshold);
            if(graphic.getThreshold()!=null)
            threshold.setText(graphic.getThreshold().toString());
            Spinner s3 = (Spinner) findViewById(R.id.create_alarm_spinner1);
            //todo string -> int kontrolü
            s3.setSelection(2);

        }

    }

    public void createOrUpdateAlarm(View view)
    {
        EditText threshold = (EditText) findViewById(R.id.ca_threshold);
        graphic.setThreshold(Long.parseLong(threshold.getText().toString()));

        saveService.updateGraphic(graphic,getIntent().getExtras().getString("username"),getIntent().getExtras().getString("token"),getIntent().getExtras().getString("serverIP"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String username = getIntent().getExtras().getString("username");
        String token = getIntent().getExtras().getString("token");
        Intent dashboardIntent = new Intent(this,AlarmDashboard.class);
        dashboardIntent.putExtra("token",token);
        dashboardIntent.putExtra("username",username);
        dashboardIntent.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));

        startActivity(dashboardIntent);//contexti ref göstererek başlattım.
        finish(); //bu aktiviteyi kapat

    }
    @Override
    public void successSave(String name, Context context, String username, String token, String serverIP) {
        Log.d("test","OK!");
        Toast.makeText(context, "Graphic Saved !", Toast.LENGTH_SHORT).show();
        Intent showGraphicIntent = new Intent(context,AlarmDashboard.class);
        showGraphicIntent.putExtra("username",username);
        showGraphicIntent.putExtra("token",token);
        showGraphicIntent.putExtra("serverIP",serverIP);
        context.startActivity(showGraphicIntent);//contexti ref göstererek baattım.
        finish(); //bu aktiviteyi kapatşl
    }

    @Override
    public void saveError(Context context) {

    }

    @Override
    public void loadGraphs(ArrayList graphs, Context context, String username, String token, String serverIP) {

    }

    @Override
    public void successRemove(Context context, String username, String token, String serverIP) {

    }
}