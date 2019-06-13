package com.example.asatkee1.augementedimagetest.BuildingPages.TBuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;


public class THealthAdvising extends AppActivityBuilderMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Health Sciences Advising");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Health Sciences Advising", topLayout);

        phoneBuilder(" health sciences ", "(425) 564-2012", bodyLayout);

        String info = "Each of our Programs in the Health Sciences, Education and Wellness " +
                "Institute handles advising differently." +
                " Please see the specific Program(s) site for more detailed  advising information.\n\n";

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        linkButtonBuilder("Website", "https://www.bellevuecollege.edu/health/advising/", true, bodyLayout);


    }
}