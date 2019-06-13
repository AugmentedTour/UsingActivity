package com.example.asatkee1.augementedimagetest.BuildingPages.CBuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

public class CBuilding extends AppActivityBuilderMethods {
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
        toolbar.setTitle("C Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The C building is home to the cafeteria, Student Union, PALS center, " +
                "and the campus business center. There is also a Mother's Room. \n\n " +
                "The Peer Assisted Leadership Through Service Center (PALS) has many resources including student " +
                "ID cards, locker rentals, and a drop in computer lab. Peer to Peer Volunteer and Mentoring Program " +
                "and an Office of Sustainability Transportation Representative are also located in the PALS Center. \n\n" +
                "The main cafeteria is only one of many dining options on campus. See the cafeteria website for " +
                "additional locations and menus.";

        // --- topLayout ---
        titleBuilder("C Building", topLayout);
        isAccessible(topLayout);
        hasHelp(topLayout);
        hasComputers(topLayout);


        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        phoneBuilder(" PALS Center ", "(425) 564-2297", bodyLayout);
        linkButtonBuilder("PALS Website", "https://www.bellevuecollege.edu/pals/", true, bodyLayout);
        linkButtonBuilder("Cafeteria Website", "https://www.bellevuecollege.edu/dining/", true, bodyLayout);
        activityButtonBuilder("Classes in Building", CBuilding.this, CCurrentClasses.class, false, bodyLayout);
    }

}