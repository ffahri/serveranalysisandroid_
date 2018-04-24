package com.webischia.serveranalysis.Global;

import android.app.Application;
import android.graphics.Color;

/**
 Burada global değişkenleri tutacağız. Böylece kodun içerisine url vs girmeden
 GlobalClass.getUrl çağıracağız. Eğer url değişirse ayarlardan kolayca ulaşabileceğiz her yere
 */

public class GlobalClass extends Application {
    public static String serverURI;
    public static String username;
    public static String password;
    public static int color_user = Color.CYAN;
}
