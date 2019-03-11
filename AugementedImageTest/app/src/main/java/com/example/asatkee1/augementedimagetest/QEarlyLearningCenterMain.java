package com.example.asatkee1.augementedimagetest;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

public class QEarlyLearningCenterMain extends AppActivityBuilderMethods {

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
        toolbar.setTitle("Q Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The Early Learning Center has space for 190 children between the ages of 6 months and 6 years." +
                " Priority is given to BC/EWU students, faculty, staff and Costco employees."; //will want to alter later

        // --- topLayout ---
        titleBuilder("Early Learning Center", topLayout);
        hasAllGendersBathroom(topLayout);
        hasComputers(topLayout);

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        phoneBuilder("Early Learing Center", "(425)-564 2240", bodyLayout);
        linkButtonBuilder("Join Waitlist", "https://www.myprocare.com/Login/EnterEmail", true, bodyLayout);


    }

}