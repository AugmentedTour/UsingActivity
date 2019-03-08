package com.example.asatkee1.augementedimagetest;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EBuilding extends AppActivityBuilderMethods {
    // Put in the URL this activity will be parsing from.
    private final String THIS_ONES_URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("E Building");
        setSupportActionBar(toolbar);

        // --- Layouts ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- Variables ---
        String info = "The E Building houses The Carlson Theatre and The Stopgap Theatre." +
                " This building  is used by the theatre arts department for dance, drama, and music performances."; //will want to alter later

        // --- topLayout ---
        titleBuilder("E Building", topLayout);
        isAccessible(topLayout);


        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        linkButtonBuilder("Carlson Theatre History", "https://www.bellevuecollege.edu/theatrearts/carlson-theatre/", true, bodyLayout);
    }
}