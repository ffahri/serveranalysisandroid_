package com.webischia.serveranalysis;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //ekranın yatay dönmesini engelleme
        setTitle("Dashboard");


        // i = new Intent(this,AlarmChecker.class);// servis çağımra
        //startService(i);
    }

    public void showGraphs(View view){
        Intent graphDash = new Intent(this,Graph_Dashboard.class);
        graphDash.putExtra("token",getIntent().getExtras().getString("token"));
        graphDash.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        graphDash.putExtra("username",getIntent().getExtras().getString("username"));
        startActivity(graphDash);
        finish(); //bu aktiviteyi kapat
    }
    public void showAlarms(View view){
        Intent showAlarmDash = new Intent(this,AlarmDashboard.class);
        showAlarmDash.putExtra("token",getIntent().getExtras().getString("token"));
        showAlarmDash.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        showAlarmDash.putExtra("username",getIntent().getExtras().getString("username"));
        startActivity(showAlarmDash);
        finish(); //bu aktiviteyi kapat
    }
    public void showAbout(View view){
        Intent about = new Intent(this,About.class);
        about.putExtra("token",getIntent().getExtras().getString("token"));
        about.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        about.putExtra("username",getIntent().getExtras().getString("username"));
        startActivity(about);
        finish(); //bu aktiviteyi kapat
    }
    public void showSettings(View view){
        Intent userSettings = new Intent(this,UserSettings.class);
        userSettings.putExtra("token",getIntent().getExtras().getString("token"));
        userSettings.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        userSettings.putExtra("username",getIntent().getExtras().getString("username"));
        startActivity(userSettings);
        finish(); //bu aktiviteyi kapat
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String username = getIntent().getExtras().getString("username");
        String token = getIntent().getExtras().getString("token");
        Intent dashboardIntent = new Intent(this,Login.class);
        dashboardIntent.putExtra("token",token);
        dashboardIntent.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        dashboardIntent.putExtra("username",username);
        startActivity(dashboardIntent);//contexti ref göstererek başlattım.
        finish(); //bu aktiviteyi kapat

    }
}
