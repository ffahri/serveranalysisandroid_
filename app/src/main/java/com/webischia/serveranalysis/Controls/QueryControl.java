package com.webischia.serveranalysis.Controls;

import android.content.Context;

import com.webischia.serveranalysis.Models.Graphic;

import java.util.ArrayList;

/**
 Query kontrol metotları
 */

public interface QueryControl {
    void successQuery(ArrayList list, Context context, Graphic graphic);
}
