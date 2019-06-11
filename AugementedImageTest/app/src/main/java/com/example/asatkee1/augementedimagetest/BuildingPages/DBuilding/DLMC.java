package com.example.asatkee1.augementedimagetest.BuildingPages.DBuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;


public class DLMC extends AppActivityBuilderMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Library & Media Center");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Library & Media Center", topLayout);

        String info = "The Library & Media Center is located in D126. \n\n" +
                "There are books to borrow including textbooks for checkout or in library use. The library is set up " +
                "with study rooms, and computers. This is also the location of the XR Lab.";

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        phoneBuilder("Circulation Desk", "(425) 564-2252", bodyLayout);
        linkButtonBuilder("LMC Website", "https://www.bellevuecollege.edu/lmc/", true, bodyLayout);

    }

}