package com.webischia.serveranalysis.Models;

import java.io.Serializable;

/**
 * Created by f on 07.04.2018.
 */

public class Graphic implements Serializable {
    String query;
    String mode;
    String time_range;
//http://192.168.122.160:9090/api/v1/query?query=node_cpu{mode="idle"}[1m]
    public String httpForm()
    {
        return "query="+query+"{mode=\""+mode+"\"}"+"["+time_range+"]"; //bu hardcoded oldu todo düzenle
    }
}
