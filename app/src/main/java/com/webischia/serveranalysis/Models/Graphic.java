package com.webischia.serveranalysis.Models;

import java.io.Serializable;

/**
 * Created by f on 07.04.2018.
 */

public class Graphic implements Serializable {
    String query;
    String mode;
    String time_range;
    String name;

    public Graphic(String query, String time_range, String name) {
        this.query = query;
        if(query=="node_network_receive_packets" || query=="node_network_transmit_packets")
        {
            this.mode="{device=\"eth0\"}";
        }
        else
        this.mode = "";
        this.time_range = time_range;
        this.name = name;
    }

    //http://192.168.122.160:9090/api/v1/query?query=node_cpu{mode="idle"}[1m]
    public String httpForm()
    {
            return query+mode+"["+time_range+"]"; //bu hardcoded oldu todo düzenle

    }
    public String getName(){
        return name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTime_range() {
        return time_range;
    }

    public void setTime_range(String time_range) {
        this.time_range = time_range;
    }

    public void setName(String name) {
        this.name = name;
    }
}
