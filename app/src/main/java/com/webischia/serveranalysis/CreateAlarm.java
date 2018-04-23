package com.webischia.serveranalysis;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
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
    Intent i,j,gonder;
    EditText e_text;
    Graphic graphic;
    SaveControl saveControl;
    SaveService saveService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        setTitle("Create Alarm");

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
            graphic.setControlTime(s3.getSelectedItemPosition());

        }

    }

    public void createOrUpdateAlarm(View view)
    {
        EditText threshold = (EditText) findViewById(R.id.ca_threshold);
        graphic.setThreshold(Long.parseLong(threshold.getText().toString()));
        graphic.setAlarmStatus(true);//d'uh???
        ///Alarm MANAGER
        String username = getIntent().getExtras().getString("username");
        String token = getIntent().getExtras().getString("token");
        String serverIP = getIntent().getExtras().getString("serverIP");
        Log.d("crate.alarm",""+username+"\n"+token+"\n"+"name"+"\n"+serverIP);


        Intent intent = new Intent(this, Alarm.class);
        intent.putExtra("graphic",graphic);
        intent.putExtra("username",username);
        intent.putExtra("token",token);
        intent.putExtra("serverIP",serverIP);


        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                20000, pIntent);
        int minutes[] = {60000,300000,600000,1800000,3600000};
        //manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+minutes[graphic.getControlTime()],minutes[graphic.getControlTime()],pendingIntent);

        ///*********
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
    public void cancel(View view)
    {
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
        Log.d("test","OK!");
        Toast.makeText(context, "Graphic Saved !", Toast.LENGTH_SHORT).show();
        Intent showGraphicIntent = new Intent(context,AlarmDashboard.class);
        showGraphicIntent.putExtra("username",username);
        showGraphicIntent.putExtra("token",token);
        showGraphicIntent.putExtra("serverIP",serverIP);
        showGraphicIntent.putExtra("flag",true);

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