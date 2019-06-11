package com.example.asatkee1.augementedimagetest.BuildingPages.NBuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

public class NBuilding extends AppActivityBuilderMethods {
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
        toolbar.setTitle("N Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The N building is home to the main computer lab in N201. There are also conference and lecture " +
                "halls, the worker retraining program, and the Puget Sound Regional Archives. \n\n " +
                "Computer lab hours Fall - Spring quarter: \n" +
                "Monday - Thursday 7:00am - 9:30pm \n" +
                "Friday            7:00am - 7:00pm \n" +
                "Saturday          9:00am - 6:00pm \n" +
                "Sunday            9:00am - 6:00pm \n\n" +
                "Computer lab Summer Quarter hours: \n" +
                "Monday - Thursday 8:00am - 8:00pm \n" +
                "Friday - Saturday Closed \n" +
                "Sunday            9:00am - 6:00pm";

        // --- topLayout ---
        titleBuilder("N Building", topLayout);
        isAccessible(topLayout);
        hasHelp(topLayout);
        hasComputers(topLayout);


        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        phoneBuilder(" ITS Service Desk ", "(425) 564-4357", bodyLayout);
        linkButtonBuilder("Computer Labs Website", "https://bellevuecollege.teamdynamix.com/TDClient/KB/ArticleDet?ID=24665", true, bodyLayout);
        activityButtonBuilder("Classes in Building", NBuilding.this, NCurrentClasses.class, false, bodyLayout);
    }

}