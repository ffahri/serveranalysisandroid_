package com.webischia.serveranalysis.Controls;

import android.content.Context;

import java.util.List;

/**
 * Created by f on 08.04.2018.
 */

public interface SaveControl {
    void successSave(String name, Context context);

    void loadGraphs(List graphs,Context context);
}
