package com.example.asatkee1.augementedimagetest.BuildingPages.BBuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

public class BBuilding extends AppActivityBuilderMethods {
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
        toolbar.setTitle("B Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The B building is home to Student Central, the bookstore, registration, financial aid, " +
                "the planetarium, academic advising, the Center for Career Connections, the counseling center, " +
                "and the International Education and Global Initiative. \n\n " +
                "Student Central is the best place to start with all questions. They can direct to " +
                "the correct resources as needed. ";

        // --- topLayout ---
        titleBuilder("B Building", topLayout);
        isAccessible(topLayout);
        hasHelp(topLayout);
        hasComputers(topLayout);


        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);

        phoneBuilder(" Student Central", "(425) 564-1000", bodyLayout);
        linkButtonBuilder("Student Central Website", "https://www.bellevuecollege.edu/studentcentral/", true, bodyLayout);
        activityButtonBuilder("Classes in Building", BBuilding.this, BCurrentClasses.class, false, bodyLayout);
    }

}