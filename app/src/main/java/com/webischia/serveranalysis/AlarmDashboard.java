package com.webischia.serveranalysis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.webischia.serveranalysis.Controls.SaveControl;
import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.SaveService;
import com.webischia.serveranalysis.Service.SaveServiceImpl;

import java.util.ArrayList;

public class AlarmDashboard extends AppCompatActivity implements SaveControl{
        SaveService saveService;
        SaveControl saveControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_dashboard);
        setTitle("Alarm Dashboard");
        saveControl = new AlarmDashboard();
        saveService = new SaveServiceImpl(saveControl,this);
        LinearLayout ll = findViewById(R.id.alarm_dash_ll);
        final ArrayList graphs = getIntent().getParcelableArrayListExtra("graphs");
        if(graphs == null) {
            saveService.loadNames(getIntent().getExtras().getString("username"), getIntent().getExtras().getString("token"),getIntent().getExtras().getString("serverIP"));
            Log.d("graphs","null");
        }

            Boolean flag = (Boolean)getIntent().getExtras().get("flag");
        if(flag!=null && flag) {
           // Intent i = new Intent(this,AlarmChecker.class);

           // stopService(i);//stop
          //  startService(i);//starta
        }
        if(graphs!= null) {
            for (int i = 0; i < graphs.size(); i++) {
                final Graphic tmp = (Graphic) graphs.get(i);
                if (tmp.isAlarmStatus()) {
                    Button temp = new Button(this);
                    //temp.setLayoutParams(a.getLayoutParams());
                    temp.setText(tmp.getName());
                    temp.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent crtgrph = new Intent(AlarmDashboard.this, CreateAlarm.class);
                            crtgrph.putExtra("token", getIntent().getExtras().getString("token"));
                            crtgrph.putExtra("username", getIntent().getExtras().getString("username"));
                            crtgrph.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
                            crtgrph.putExtra("graphic",tmp);
                            crtgrph.putParcelableArrayListExtra("graphs",graphs);
                            startActivity(crtgrph);
                            finish(); //bu aktiviteyi kapat
                            //        create_alarm parcel(graphic)                finish();
                        }
                    });
                    ll.addView(temp);
                }
            }
        }
        else {
            //todo ana ekran uyarı ver
        }

    }
    public void createAlarm(View view)
    {
        Intent crtgrph = new Intent(this, CreateAlarm.class);
        crtgrph.putExtra("token", getIntent().getExtras().getString("token"));
        crtgrph.putExtra("username", getIntent().getExtras().getString("username"));
        crtgrph.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        startActivity(crtgrph);
        finish(); //bu aktiviteyi kapat
    }
    public void removeAlarm(View view)
    {
        Intent crtgrph = new Intent(this, RemoveAlarm.class);
        crtgrph.putExtra("token", getIntent().getExtras().getString("token"));
        crtgrph.putExtra("username", getIntent().getExtras().getString("username"));
        crtgrph.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        crtgrph.putParcelableArrayListExtra("graphs",getIntent().getParcelableArrayListExtra("graphs"));
        startActivity(crtgrph);
        finish(); //bu aktiviteyi kapat
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


    @Override
    public void successSave(String name, Context context, String username, String token, String serverIP) {

    }

    @Override
    public void saveError(Context context) {

    }

    @Override
    public void loadGraphs(ArrayList graphs, Context context, String username, String token, String serverIP) {
        if (graphs.size() > 0) {
            //todo grafiklistesini sonraya at kontrol et
            Intent graphDash = new Intent(context, AlarmDashboard.class);
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
    public void successRemove(Context context, String username, String token, String serverIP) {

    }
}
