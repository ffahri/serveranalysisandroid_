package com.webischia.serveranalysis.Service;

//Login kontrol metotları tanımlanacak.

import android.content.Context;

public interface LoginControl {
    void successLogin(String username, String token, Context context);//contexti göndermem gerekiyor
                                                                      //içeriden aktivite çağırıyorum.
}
