package com.example.asatkee1.augementedimagetest.BuildingPages.ABuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;


public class AIBIT extends AppActivityBuilderMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("IBIT");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("IBIT", topLayout);

        String info = "The Institute for Business & Information Technology is located in A254. \n\n" +
                "Our goal is to provide students with meaningful preparation to meet the challenges of " +
                "a global environment in the areas of business, information technology, and digital media.  ";

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        phoneBuilder(" IBIT Office ", "(425) 564-2311", bodyLayout);
        linkButtonBuilder("IBIT Website", "https://www.bellevuecollege.edu/ibit/", true, bodyLayout);

    }

}