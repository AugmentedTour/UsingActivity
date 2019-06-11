package com.example.asatkee1.augementedimagetest.BuildingPages.LBuilding;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

public class LBuilding extends AppActivityBuilderMethods {

    public Button ScienceDevision;
    public Button SDCall;
    public Button IDCall;
    public Button InteriorDesign;

    private void init() {
        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("L Building");
        setSupportActionBar(toolbar);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);


        ScienceDevision = (Button)findViewById(R.id.science_division);
        SDCall = (Button)findViewById(R.id.SD_phone);
        IDCall = (Button)findViewById(R.id.ID_phone);
        InteriorDesign = (Button)findViewById(R.id.interior_design);



        ScienceDevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startScienceDivisionActivity = new Intent(LBuilding.this, LScienceDivisionActivity.class);
                startActivity(startScienceDivisionActivity);
            }
        });

        InteriorDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startInteriorDesignActivity = new Intent(LBuilding.this, LInteriorDesignActivity.class);
                startActivity(startInteriorDesignActivity);
            }
        });


        SDCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:4255642321"));
                startActivity(callIntent);
            }
        });

        IDCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:4255642624"));
                startActivity(callIntent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_building);
        init();



    }

}
