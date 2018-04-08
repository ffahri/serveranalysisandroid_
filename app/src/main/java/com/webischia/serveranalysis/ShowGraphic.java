package com.webischia.serveranalysis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.webischia.serveranalysis.Controls.QueryControl;

import java.util.ArrayList;

public class ShowGraphic extends AppCompatActivity implements QueryControl{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graphic);
    }

    @Override
    public void successQuery(ArrayList list) {

    }
}
