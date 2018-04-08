package com.webischia.serveranalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.webischia.serveranalysis.Service.LoginControl;
import com.webischia.serveranalysis.Service.LoginControlImpl;

public class Login extends AppCompatActivity implements LoginControl{

    LoginControl loginControl;
    LoginControlImpl control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginControl = new Login();
        control = new LoginControlImpl(loginControl,this);
    }

    //Service kullanarak login kontrol
    public void loginCheck(View view){
        EditText txt_username,txt_password;
        txt_username=findViewById(R.id.username);
        txt_password=findViewById(R.id.password);
        control.loginCheck(txt_username.getText().toString(),txt_password.getText().toString());


    }

    @Override
    public void successLogin(String username, String token) {
        Toast.makeText(this, "Login Success !", Toast.LENGTH_SHORT).show();
        if(token != null) {
            Toast.makeText(Login.this, "Login Success !" + token, Toast.LENGTH_SHORT).show();
            Intent dashboardIntent = new Intent(Login.this,Dashboard.class);
            dashboardIntent.putExtra("token",token);
            startActivity(dashboardIntent);
            finish(); //bu aktiviteyi kapat
        }

    }
}
