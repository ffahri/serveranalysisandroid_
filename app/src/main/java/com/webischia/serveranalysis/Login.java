package com.webischia.serveranalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //Service kullanarak login kontrol
    public void loginCheck(View view){
        Toast.makeText(Login.this, "Login Success !", Toast.LENGTH_SHORT).show();
        Intent dashboardIntent = new Intent(Login.this,Dashboard.class);
        //i.putExtra();
        startActivity(dashboardIntent);
        finish(); //bu aktiviteyi kapat
    }
}
