package com.webischia.serveranalysis.Service;

//Login control metotları bulunacak.

public class LoginControlImpl implements LoginControl {
    @Override
    public String loginCheck(String username, String password) {
        //yanlış şifre ise hata ver ve null dön

        //401 - 403 hatası alırsan hata ver diyalog çıkar

        //doğru ise http 200
        //token döndür
        return null;
    }
}
