package com.webischia.serveranalysis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.webischia.serveranalysis.Controls.SaveControl;
import com.webischia.serveranalysis.Models.Graphic;
import com.webischia.serveranalysis.Service.SaveService;
import com.webischia.serveranalysis.Service.SaveServiceImpl;

import java.util.ArrayList;

public class RemoveAlarm extends AppCompatActivity implements SaveControl{
    SaveService saveService;
    SaveControl saveControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_alarm);
        setTitle("Remove Alarm");
        LinearLayout ll = findViewById(R.id.ra_ll);
        saveControl = new RemoveAlarm();
        saveService = new SaveServiceImpl(saveControl,this);

        ArrayList graphs = getIntent().getParcelableArrayListExtra("graphs");
        if(graphs!= null) {
            for (int i = 0; i < graphs.size(); i++) {
                final Graphic tmp = (Graphic) graphs.get(i);
                if (tmp.isAlarmStatus()) {
                    Button temp = new Button(this);
                    //temp.setLayoutParams(a.getLayoutParams());
                    temp.setText(tmp.getName());
                    temp.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            tmp.setAlarmStatus(false);
                            saveService.updateGraphic(tmp, getIntent().getExtras().getString("username"), getIntent().getExtras().getString("token"), getIntent().getExtras().getString("serverIP"));
                            finish();
                        }
                    });
                    ll.addView(temp);
                }
            }
        }
        else {
            //todo ana ekran uyarı ver
            Log.d("remove.alarm","graphs null");
        }

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
    public void successSave(String name, Context context,String username,String token,String serverIP,Graphic graphic) {
        Log.d("ALARM","REMOVED_SUCCESS");
        Toast.makeText(context, "ALARM REMOVED !" , Toast.LENGTH_SHORT).show();

        Intent graphDash = new Intent(context,AlarmDashboard.class);
        graphDash.putExtra("token",token);
        graphDash.putExtra("username",username);
        graphDash.putExtra("serverIP",serverIP);
        //graphDash.putParcelableArrayListExtra("graphs",null);
        context.startActivity(graphDash);
        finish();
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
