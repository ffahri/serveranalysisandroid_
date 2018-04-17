package com.webischia.serveranalysis.Service;

import com.webischia.serveranalysis.Models.Graphic;

import java.util.List;

//Android hafızasına burada tanımladığımız metotlar ile yazacağız.
public interface SaveService {

    public void saveGraphics(Graphic graphObj,String username,String token,String serverIP);

    public Graphic loadGraphics(String name);

    public void saveNames(String name,String username); //obje olarak değil txt olarak kaydediyoruz
    void loadNames(String username,String token,String serverIP);
    public void removeGraphic(Graphic graphObj,String username,String token,String serverIP);

}
