package com.example.asatkee1.augementedimagetest;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class L_building extends AppActivityBuilderMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(970,1400);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("L Building");
        setSupportActionBar(toolbar);

        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        String info = "The L building is home to the Science Department and Interior Design division." +
                " Periodically Bellevue College provide job/internship fairs in the lobby."; //will want to alter later

        // --- topLayout ---
        titleBuilder("L Building", topLayout);


        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);

        Button ScienceDevision = activityButtonBuilder ("ScienceDevision", L_building.this, ScienceDivisionActivity.class, true, bodyLayout);
        TextView SDAddress = textViewBuilder ("L200, 3000 Landerholm Circle SE", bodyLayout);
        TextView SDCall = phoneBuilder ("call to Science Division", "4255642321", bodyLayout);

        Button InteriorDesign = activityButtonBuilder(" Department of Interior Design", L_building.this, InteriorDesignActivity.class,  true, bodyLayout);
        TextView IDAddress = textViewBuilder("Office L115C, 3000 Landerholm Circle SE", bodyLayout);
        TextView IDCall = phoneBuilder("call to Department of Interior Design", "4255642624", bodyLayout);

        TextView Events = textViewBuilder("Events in the lobby:\n", bodyLayout);

    }

}
