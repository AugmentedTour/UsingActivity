package com.example.asatkee1.augementedimagetest.BuildingPages.TBuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

public class TBuilding extends AppActivityBuilderMethods {
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
        toolbar.setTitle("T Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The T building is home to the Health Sciences, Education, and " +
                " Wellness Institute. The institute is located in T208. " +
                "This building is where the medical field classes and clubs meet.";

        // --- topLayout ---
        titleBuilder("T Building", topLayout);
        isAccessible(topLayout);
        hasHelp(topLayout);
        hasComputers(topLayout);

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        phoneBuilder(" Health Sciences ", "(425) 564-2012", bodyLayout);
        linkButtonBuilder("Health Sciences Website", "https://www.bellevuecollege.edu/health/", true, bodyLayout);
        activityButtonBuilder("Advising", TBuilding.this, THealthAdvising.class, false, bodyLayout);
        activityButtonBuilder("Classes in Building", TBuilding.this, TCurrentClasses.class, false, bodyLayout);
    }

}