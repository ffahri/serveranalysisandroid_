package com.webischia.serveranalysis.Service;

//Login control metotları bulunacak.

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginService {
    LoginControl loginControl = null;
    static Context mContext;

    public LoginService(LoginControl loginControl, Context mContext) {
        this.loginControl = loginControl;
        this.mContext = mContext;
    }

    public void loginCheck(final String username, final String password) {
        //yanlış şifre ise hata ver ve null dön

        //401 - 403 hatası alırsan hata ver diyalog çıkar

        //doğru ise http 200
        //token döndür

        try {
           //todo burada hata var

            final RequestQueue queue = Volley.newRequestQueue(mContext);//Volley.newRequestQueue(this);  // this = context
            String url = "http://10.0.2.2:8080/oauth/token"; // GLOBAL VARIABLE ILE ALACAĞIM
            final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            try {

                                JSONObject jsonObj = new JSONObject(response);
                                // Getting JSON Array node
//                                JSONArray token = jsonObj.getJSONArray("token");
                                String jti = jsonObj.getString("access_token");
                                if(loginControl != null)
                                    loginControl.successLogin(username,jti,mContext);
                              //  Toast.makeText(MainActivity.this, "Başarıyla giriş yaptınız", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e)
                            {
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            error.printStackTrace();
                            Log.d("Error.Response","error ");
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("grant_type", "password");
                    params.put("username", username);
                    params.put("password", password);

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Basic U3lzdGVtQW5hbHlzaXM6WFk3a216b056bDEwMA==");

                    return params;
                }
            };
            queue.add(postRequest);
        }
        catch(Exception e)
        {
        }

    }
}
