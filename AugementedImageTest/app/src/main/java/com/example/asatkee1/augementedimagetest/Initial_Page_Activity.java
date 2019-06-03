package com.example.asatkee1.augementedimagetest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Initial_Page_Activity extends AppCompatActivity {
private Button apply;
private Button general_info;
private String txt ="";
    private String result = "";
    private String title = "";
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
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            txt = urlParse();
                            result = pullInfo(txt);
                            Intent sendIntent = new Intent(Initial_Page_Activity.this, General_Information_Activity.class);
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra("KEY", result);
                            sendIntent.setType("text/plain");
                            Initial_Page_Activity.this.startActivity(sendIntent);
                            Log.w("Info", result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                Thread.interrupted();
            }
        });
    }

    public String urlParse() throws IOException {
        URL url = new URL("https://www.bellevuecollege.edu/housing"); // URL
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String input = "";
        String text = "";
        while ((input = in.readLine()) != null) {
            text += input + "\n";
        }
        return text;
    }


    public String pullInfo(String html) {
        Pattern pattern = Pattern.compile("<h2>Student Housing is in full swing at Bellevue College!</h2>\\s*<p>(.*)<strong>");
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            title = matcher.group(1);
        }
        return title;
    }
}
