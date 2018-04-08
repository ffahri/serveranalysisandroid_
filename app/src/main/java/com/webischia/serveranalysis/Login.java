package com.webischia.serveranalysis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.webischia.serveranalysis.Controls.LoginControl;
import com.webischia.serveranalysis.Service.LoginServiceImpl;
import com.webischia.serveranalysis.Service.LoginService;

public class Login extends AppCompatActivity implements LoginControl{

    LoginControl loginControl;
    LoginService control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginControl = new Login();
        control = new LoginServiceImpl(loginControl,this);
    }

    //Service kullanarak login kontrol
    public void loginCheck(View view){
        EditText txt_username,txt_password;
        txt_username=findViewById(R.id.username);
        txt_password=findViewById(R.id.password);
        control.loginCheck(txt_username.getText().toString(),txt_password.getText().toString());


    }

    @Override
    public void successLogin(String username, String token, Context context) {
            //Toast.makeText(getBaseContext(), "Login Success !", Toast.LENGTH_SHORT).show();
            //control.saveUser(username);
            Toast.makeText(context, "Login Success ! \nWelcome " + username, Toast.LENGTH_SHORT).show();
            Intent dashboardIntent = new Intent(context,Dashboard.class);
            dashboardIntent.putExtra("token",token);
            context.startActivity(dashboardIntent);//contexti ref göstererek başlattım.
            finish(); //bu aktiviteyi kapat


    }
}
