package com.example.asatkee1.augementedimagetest;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

public class RBuilding extends AppActivityBuilderMethods {

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
        toolbar.setTitle("R Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The R building is home to the arts and humanities division." +
                " ESL classes can also be found here, and there's a dance studio downstairs." +
                " A cafe sells coffee on the first floor."; //will want to alter later

        // --- topLayout ---
        titleBuilder("R Building", topLayout);
        hasAllGendersBathroom(topLayout);
        hasComputers(topLayout);

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        activityButtonBuilder("Arts and Humanities", RBuilding.this, RArtsAndHumanitiesMain.class, false, bodyLayout);
        //activityButtonBuilder("Dance Studio", RBuilding.this, RDanceMain.class, false, bodyLayout);
        //activityButtonBuilder("ELI", RBuilding.this, RELIMain.class, true, bodyLayout);
        textViewBuilder("Human Resources (HR): Location R130(425) | Fax 564-3173", bodyLayout);
        phoneBuilder("HR", "564-2274(425)", bodyLayout);
        //activityButtonBuilder("ABE", RBuilding.this, RABEMain.class, true, bodyLayout);


    }

}
