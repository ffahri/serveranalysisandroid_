package com.webischia.serveranalysis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.webischia.serveranalysis.Global.GlobalClass;

public class UserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        String[] level = new String[] {
                "Red","Green","Blue","Cyan","Yellow","Purple","Orange"
        };
        Spinner s3 = (Spinner) findViewById(R.id.user_settings_spinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,level);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s3.setAdapter(adapter3);
    }

    public void colorChange(View view){
        Spinner s3 = (Spinner) findViewById(R.id.user_settings_spinner);

        int[] values = new int[] {
                Color.RED,Color.GREEN,Color.BLUE,Color.CYAN,Color.YELLOW,Color.rgb(255,0,255),Color.rgb(255,165,0)
        };
        GlobalClass.color_user= values[s3.getSelectedItemPosition()];
        Toast.makeText(UserSettings.this, "LED Color Changed !", Toast.LENGTH_SHORT).show();

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
}
