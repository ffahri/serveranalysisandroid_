package com.webischia.serveranalysis.Service;

import com.webischia.serveranalysis.Models.Graphic;

//Server ile android arasındaki querylerin çağırılacağı metotların belirtilidiği yer
public interface QueryService {

    void doQuery(Graphic graphic, String token);

}
