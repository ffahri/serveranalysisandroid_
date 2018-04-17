package com.webischia.serveranalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAlarm extends AppCompatActivity {
    Button create_alarm;
    Intent i,j;
    EditText e_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        create_alarm= findViewById(R.id.c_alarm);
        e_text= findViewById(R.id.editText);
        create_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int limit = Integer.parseInt(e_text.getText().toString());
                i = new Intent(CreateAlarm.this,Dashboard.class);
                j = new Intent(CreateAlarm.this,AlarmChecker.class);
                j.putExtra("limit",limit);
                startActivity(i);
                finish();
            }
        });
    }
}