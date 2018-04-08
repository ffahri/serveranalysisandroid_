package com.webischia.serveranalysis.Global;

import android.app.Application;

/**
 Burada global değişkenleri tutacağız. Böylece kodun içerisine url vs girmeden
 GlobalClass.getUrl çağıracağız. Eğer url değişirse ayarlardan kolayca ulaşabileceğiz her yere
 */

public class GlobalClass extends Application {
    String serverURI;
    String username;
    public String getServerURI() {
        return serverURI;
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }
}
