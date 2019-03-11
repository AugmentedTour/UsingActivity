package com.example.asatkee1.augementedimagetest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Initial_Page_Activity extends AppCompatActivity {
Button apply;
Button general_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_activity);
        getWindow().setLayout(800,800);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);
        apply = findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.bellevuecollege.edu/housing/apply/"));
                startActivity(browserIntent);
            }
        });

        general_info = findViewById(R.id.general_info);
        general_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Initial_Page_Activity.this, General_Information_Activity.class);
                Initial_Page_Activity.this.startActivity(myIntent);
            }
        });


    }
}
