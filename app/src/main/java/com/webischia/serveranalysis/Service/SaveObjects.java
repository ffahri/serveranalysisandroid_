package com.webischia.serveranalysis.Service;

import com.webischia.serveranalysis.Models.Graphic;

//Android hafızasına burada tanımladığımız metotlar ile yazacağız.
public interface SaveObjects {

    public void saveGraphics();

    public Graphic loadGraphics(String name);
}
