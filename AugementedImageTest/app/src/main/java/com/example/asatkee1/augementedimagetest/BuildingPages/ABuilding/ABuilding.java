package com.example.asatkee1.augementedimagetest.BuildingPages.ABuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

public class ABuilding extends AppActivityBuilderMethods {
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
        toolbar.setTitle("A Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The A building is home to the IBIT division office, administrative services " +
                "offices, the BC Foundation, IT office, Institutional Advancement, and the college " +
                "relations office. While the G Building is under construction, the A Building also houses " +
                "the fitness center. \n\n" +
                "ITS service office is located in A109";

        // --- topLayout ---
        titleBuilder("A Building", topLayout);
        isAccessible(topLayout);
        hasHelp(topLayout);
        hasComputers(topLayout);


        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);

        phoneBuilder(" ITS service ", "(425) 564-4357", bodyLayout);
        activityButtonBuilder("BC Foundation", ABuilding.this, ABCFoundation.class, false, bodyLayout);
        activityButtonBuilder("IBIT", ABuilding.this, AIBIT.class, false, bodyLayout);
        activityButtonBuilder("Classes in Building", ABuilding.this, ACurrentClasses.class, false, bodyLayout);
    }

}