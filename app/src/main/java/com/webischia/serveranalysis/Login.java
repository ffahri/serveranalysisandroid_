package com.webischia.serveranalysis;

import android.app.Application;
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
        EditText txt_username,txt_password,server_ip;
        txt_username=findViewById(R.id.username);
        txt_password=findViewById(R.id.password);
        server_ip=findViewById(R.id.server_ip);
        control.loginCheck(server_ip.getText().toString(),txt_username.getText().toString(),txt_password.getText().toString());
        isInternetControl();


    }

    @Override
    public void successLogin(String serverIP,String username, String token, Context context) {
            //Toast.makeText(getBaseContext(), "Login Success !", Toast.LENGTH_SHORT).show();
            //control.saveUser(username);
            Toast.makeText(context, "Login Success ! \nWelcome " + username, Toast.LENGTH_SHORT).show();
            Intent dashboardIntent = new Intent(context,Dashboard.class);
            dashboardIntent.putExtra("token",token);
            dashboardIntent.putExtra("serverIP",serverIP);
            dashboardIntent.putExtra("username",username);
            context.startActivity(dashboardIntent);//contexti ref göstererek başlattım.
            finish(); //bu aktiviteyi kapat


    }

    @Override
    public void deniedLogin(Context context) {
        Toast.makeText(context, "ACCESS DENIED ! \n" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        //a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);


    }
      //internet bağlantısı create yerine logine de eklenebilir
    public boolean isInternetControl()
    {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
          //  Toast.makeText(Login.this,"internet var",Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(Login.this,"Need Internet Connection",Toast.LENGTH_SHORT).show();
            return false;

        }
    }
}
