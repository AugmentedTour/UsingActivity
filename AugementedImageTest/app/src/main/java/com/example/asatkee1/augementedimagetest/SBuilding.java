package com.example.asatkee1.augementedimagetest;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

public class SBuilding extends AppActivityBuilderMethods {
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
        toolbar.setTitle("S Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The S building is home to the science division." +
                " There is a state of the art biology and chemistry equipment for classes and clubs." +
                " The science study center provides tutors for many science topics."; //will want to alter later

        // --- topLayout ---
        titleBuilder("S Building", topLayout);
        isAccessible(topLayout);
        hasHelp(topLayout);
        hasComputers(topLayout);

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        linkButtonBuilder("Science Division Website", "https://www.bellevuecollege.edu/science/", true, bodyLayout);
        linkButtonBuilder("Science Clubs", "https://www.bellevuecollege.edu/science/clubs/", true, bodyLayout);
        activityButtonBuilder("Departments", SBuilding.this, SScienceDepartments.class, false, bodyLayout);



    }

}