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

public class RemoveGraphic extends AppCompatActivity implements SaveControl {

    SaveControl saveControl;
    SaveService saveService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveControl = new RemoveGraphic();
        saveService = new SaveServiceImpl(saveControl,this);
        setContentView(R.layout.activity_remove_graphic);
        LinearLayout ll = findViewById(R.id.remove_graph_ll);
        ArrayList graphs = getIntent().getParcelableArrayListExtra("graphs");
        if(graphs!= null) {
            for (int i = 0; i < graphs.size(); i++) {
                Button temp = new Button(this);
                //temp.setLayoutParams(a.getLayoutParams());
                final Graphic tmp = (Graphic) graphs.get(i);
                temp.setText(tmp.getName());
                temp.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        saveService.removeGraphic(tmp, getIntent().getExtras().getString("username"), getIntent().getExtras().getString("token"), getIntent().getExtras().getString("serverIP"));
                        finish();
                    }
                });
                ll.addView(temp);
            }
        }
        else {
            //todo ana ekran uyarı ver
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent graphDash = new Intent(this,Graph_Dashboard.class);
        graphDash.putExtra("token",getIntent().getExtras().getString("token"));
        graphDash.putExtra("username",getIntent().getExtras().getString("username"));
        graphDash.putExtra("serverIP",getIntent().getExtras().getString("serverIP"));
        graphDash.putParcelableArrayListExtra("graphs",null);
        this.finish();
        startActivity(graphDash);
    }

    @Override
    public void successSave(String name, Context context, String username, String token, String serverIP) {


    }

    @Override
    public void saveError(Context context) {

    }

    @Override
    public void loadGraphs(ArrayList graphs, Context context, String username, String token, String serverIP) {

    }

    @Override
    public void successRemove(Context context, String username, String token, String serverIP) {

        Log.d("Remove","SUCCESS");
        Toast.makeText(context, "GRAPHIC AND ALARM REMOVED !" , Toast.LENGTH_SHORT).show();

        Intent graphDash = new Intent(context,Graph_Dashboard.class);
        graphDash.putExtra("token",token);
        graphDash.putExtra("username",username);
        graphDash.putExtra("serverIP",serverIP);
        //graphDash.putParcelableArrayListExtra("graphs",null);
        context.startActivity(graphDash);
        finish();


    }
}
