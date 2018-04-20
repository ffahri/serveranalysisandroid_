package com.webischia.serveranalysis;

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

import com.webischia.serveranalysis.Models.Graphic;

import java.util.ArrayList;

public class CreateAlarm extends AppCompatActivity {
    Button create_alarm;
    Intent i,j;
    EditText e_text;
    Graphic graphic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
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
}