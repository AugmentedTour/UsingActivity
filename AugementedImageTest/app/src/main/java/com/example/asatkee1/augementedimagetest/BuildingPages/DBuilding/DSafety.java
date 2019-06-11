package com.example.asatkee1.augementedimagetest.BuildingPages.DBuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;


public class DSafety extends AppActivityBuilderMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Public Safety");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Public Safety", topLayout);

        String info = "The Public Safety office is located in D171. \n\n" +
                "The Public Safety office provides personal safety, security, crime prevention, " +
                "preliminary investigations, and other services to the campus community 24 hours a day, 7 " +
                "days a week. The office provides parking permits, lost and found services, investigation " +
                "of collisions, offers assistance in event of lock outs, provides jump starts, and are " +
                "available for escort when needed for security. \n\n" +
                "Use the Public Safety Website to purchase parking passes, get information on lost and found, and see " +
                "other services offered by the office.";

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        phoneBuilder(" Public Safety", "(425) 564-2400", bodyLayout);
        linkButtonBuilder("Public Safety Website", "https://www.bellevuecollege.edu/publicsafety/", true, bodyLayout);

    }

}