package com.example.asatkee1.augementedimagetest;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KBuilding extends AppActivityBuilderMethods {
    // Put in the URL this activity will be parsing from.
    private final String THIS_ONES_URL = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("K Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The K building is home to Bellevue College campus operations." +
                " This building also houses the campus mailroom."; //will want to alter later

        // --- topLayout ---
        titleBuilder("K Building", topLayout);
        isAccessible(topLayout);


        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);


    }

}