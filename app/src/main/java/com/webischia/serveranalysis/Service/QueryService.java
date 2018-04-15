package com.webischia.serveranalysis.Service;

import com.github.mikephil.charting.data.Entry;
import com.webischia.serveranalysis.Models.Graphic;


import java.util.ArrayList;
import java.util.List;

//Server ile android arasındaki querylerin çağırılacağı metotların belirtilidiği yer
public interface QueryService {

    void doQuery(Graphic graphic, String token,String username,String serverIP);

}
