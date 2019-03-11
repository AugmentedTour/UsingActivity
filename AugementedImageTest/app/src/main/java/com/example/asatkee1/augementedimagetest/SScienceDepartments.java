package com.example.asatkee1.augementedimagetest;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;


public class SScienceDepartments extends AppActivityBuilderMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Science Departments");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Science Departments", topLayout);

        String info = "  \nAstronomy \n" +
                "Biology \n" +
                "Botany \n" +
                "Chemistry \n" +
                "ComGen \n" +
                "Computer Science \n" +
                "Computer Science Transfer \n" +
                "Engineering \n" +
                "Environmental Science \n"+
                "Geology \n" +
                "Mathematics \n" +
                "Meteorology \n" +
                "Molecular Biosciences \n" +
                "Molecular Sciences \n" +
                "Nutrition \n" +
                "Oceanography \n" +
                "Physics \n";

        // --- bodyLayout ---
        textViewBuilder(info, bodyLayout);
        linkButtonBuilder("Website", "https://www.bellevuecollege.edu/science/departments/", true, bodyLayout);

    }

}