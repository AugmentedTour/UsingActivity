package com.example.asatkee1.augementedimagetest.BuildingPages.LBuilding;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;


public class LInteriorDesignActivity extends AppActivityBuilderMethods {

    public void associatedegree (View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bellevuecollege.edu/interiordesign/programs/aa/"));
        startActivity(browserIntent);
    }
    public void bachelordegree (View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bellevuecollege.edu/interiordesign/programs/baa/"));
        startActivity(browserIntent);
    }

    public void advising (View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bellevuecollege.edu/interiordesign/advising/advisors/"));
        startActivity(browserIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interior_design);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Interior Design");
        setSupportActionBar(toolbar);
    }
}
