package com.example.asatkee1.augementedimagetest.BuildingPages.DBuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;


public class DASC extends AppActivityBuilderMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ASC");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Academic Success Center", topLayout);

        String info = "The Academic Success Center is located in D204. \n\n" +
                "Services include a math lab, writing lab, reading lab, and tutoring on many subjects. " +
                "Text books, including many math solutions manuals, are available to borrow for use in " +
                "the center. Tutors are available in the labs during open hours. Additional tutors are " +
                "available for drop in hours and also online. See the ASC website for details and hours.";

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        phoneBuilder(" Academic Success Center ", "(425) 564-2200", bodyLayout);
        linkButtonBuilder("ASC Website", "https://www.bellevuecollege.edu/asc/", true, bodyLayout);

    }

}