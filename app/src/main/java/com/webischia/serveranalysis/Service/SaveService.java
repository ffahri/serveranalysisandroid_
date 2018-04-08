package com.webischia.serveranalysis.Service;

import com.webischia.serveranalysis.Models.Graphic;

import java.util.List;

//Android hafızasına burada tanımladığımız metotlar ile yazacağız.
public interface SaveService {

    public void saveGraphics(Graphic graphObj,String username);

    public Graphic loadGraphics(String name);

    public void saveNames(String name,String username); //obje olarak değil txt olarak kaydediyoruz
    void loadNames(String username);
}
