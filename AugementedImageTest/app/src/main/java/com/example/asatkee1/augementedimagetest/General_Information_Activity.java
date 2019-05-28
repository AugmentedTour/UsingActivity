package com.example.asatkee1.augementedimagetest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class General_Information_Activity extends AppCompatActivity {
private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_information);
        getWindow().setLayout(800,800);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        info = findViewById(R.id.information);
        info.setTextSize(18);
        info.setPadding(10,10,10,10);
        info.setTypeface(Typeface.SERIF, Typeface.NORMAL);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                info.setText(intent.getStringExtra("KEY")); // Handle text being sent
            }

        }



    }


}
