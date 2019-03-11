package com.example.asatkee1.augementedimagetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class InteriorDesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(950,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);
    }
}
