package com.example.asatkee1.augementedimagetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class General_Information_Activity extends AppCompatActivity {
TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_information);
        getWindow().setLayout(800,800);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        info = findViewById(R.id.information);
        info.setText("This is the general information about housing");



    }
}
