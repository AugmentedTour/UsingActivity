package com.example.asatkee1.augementedimagetest.BuildingPages.ABuilding;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;


public class ABCFoundation extends AppActivityBuilderMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bellevue College Foundation");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Bellevue College Foundation", topLayout);

        String info = "The Bellevue College Foundation is a non-profit, 501(c)3 organization aligned with Bellevue College. \n" +
                "The Foundation, through a unique blend of cutting-edge, innovative and exemplary programs, will enable " +
                "Bellevue College to attract, without financial barriers, the best and brightest students and faculty. We " +
                "strive to provide opportunities for all students to change their lives and become exceptional.";

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        phoneBuilder(" BC Foundation ", "(425) 564-2386", bodyLayout);
        linkButtonBuilder("BC Foundation Website", "https://www.bellevuecollege.edu/foundation/", true, bodyLayout);

    }

}