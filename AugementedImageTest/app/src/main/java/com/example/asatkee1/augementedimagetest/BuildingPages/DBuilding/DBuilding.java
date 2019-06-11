package com.example.asatkee1.augementedimagetest.BuildingPages.DBuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

public class DBuilding extends AppActivityBuilderMethods {
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
        toolbar.setTitle("D Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The D building is home to the Academic Success Center, the Faculty Resource Center, " +
                "Public Safety, the library & media center,and much more.";

        // --- topLayout ---
        titleBuilder("D Building", topLayout);
        isAccessible(topLayout);
        hasHelp(topLayout);
        hasComputers(topLayout);


        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);

        activityButtonBuilder("Academic Success Center", DBuilding.this, DASC.class, false, bodyLayout);
        activityButtonBuilder("Public Safety", DBuilding.this, DSafety.class, false, bodyLayout);
        activityButtonBuilder("Library & Media Center", DBuilding.this, DLMC.class, false, bodyLayout);
        activityButtonBuilder("Classes in Building", DBuilding.this, DCurrentClasses.class, false, bodyLayout);
    }

}