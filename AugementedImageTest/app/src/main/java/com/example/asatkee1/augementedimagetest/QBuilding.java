package com.example.asatkee1.augementedimagetest;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

public class QBuilding extends AppActivityBuilderMethods {

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
        String info = "The Q building is home to the Early Learning Center." +
                " This is where Early Learning students get in the field experience working with kids." +
                " Childcare is available for kids between the ages of 6 months and 6 years. In addition to full time childcare, the " +
                "center also offers summer programs, part time care, and classes for families."; //will want to alter later

        // --- topLayout ---
        titleBuilder("Q Building", topLayout);
        isAccessible(topLayout);

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        activityButtonBuilder("Early Learning & Teacher Education", QBuilding.this, QEarlyLearningTeacherEdMain.class, false, bodyLayout);
        activityButtonBuilder("Early Learning Center", QBuilding.this, QEarlyLearningCenterMain.class, false, bodyLayout);



    }

}