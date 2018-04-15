package com.webischia.serveranalysis.Controls;

//Login kontrol metotları tanımlanacak.

import android.content.Context;

public interface LoginControl {
    void successLogin(String serverIP,String username, String token, Context context);//contexti göndermem gerekiyor
                                                                      //içeriden aktivite çağırıyorum.
    void deniedLogin(Context context);
}
