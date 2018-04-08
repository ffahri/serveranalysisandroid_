package com.webischia.serveranalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void showGraphs(View view){
        Intent graphDash = new Intent(this,Graph_Dashboard.class);
        //dashboardIntent.putExtra("token",token);
        startActivity(graphDash);
        finish(); //bu aktiviteyi kapat
    }
}
