package com.webischia.serveranalysis.Service;

import android.content.Context;

import com.webischia.serveranalysis.Controls.QueryControl;

/**Query metotlarının yazıldığı ve güvenli bir şekilde servera iletilip rest cevabı
olan json dosyasının parse edildiği sınıf
 **/

public class QueryServiceImpl implements QueryService {
    QueryControl queryControl;
    Context context;

    public QueryServiceImpl(QueryControl queryControl, Context context) {
        this.queryControl = queryControl;
        this.context = context;
    }
}
