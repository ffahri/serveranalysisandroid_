package com.webischia.serveranalysis.Controls;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by f on 08.04.2018.
 */

public interface SaveControl {
    void successSave(String name, Context context,String username,String token,String serverIP);
    void saveError(Context context);

    public void loadGraphs(ArrayList graphs , Context context,String username ,String token,String serverIP);
}
