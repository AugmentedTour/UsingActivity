package com.example.asatkee1.augementedimagetest;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GBuilding extends AppActivityBuilderMethods {
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
        toolbar.setTitle("G Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The G Building is the Bellevue College gymnasium." +
                " This building is used for BC physical education classes and is used by other local " +
                "schools for sporting events, science fairs, and graduations. " +
                "The locker rooms and fitness center are currently under construction. " +
                "The fitness center has moved to A-265 during the remodel." +
                "The wellness center is currently closed."; //will want to alter later

        // --- topLayout ---
        titleBuilder("G Building", topLayout);
        isAccessible(topLayout);


        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        linkButtonBuilder("Health and Physical Education Website", "https://www.bellevuecollege.edu/pe/", true, bodyLayout);
    }
}