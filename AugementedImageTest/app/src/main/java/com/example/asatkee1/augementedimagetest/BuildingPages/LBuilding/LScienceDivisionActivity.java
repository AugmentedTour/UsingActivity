package com.example.asatkee1.augementedimagetest.BuildingPages.LBuilding;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LScienceDivisionActivity extends AppActivityBuilderMethods {
    private class DownloadDepartmentsTask extends AsyncTask<URL, Integer, Long> {
        List<String> departments = new ArrayList<>();

        String url = "https://www.bellevuecollege.edu/science/departments/";
        public String html;
        public Matcher matcher;

        public String readUrl(String urlString) throws IOException {
            URL url = new URL(urlString); // URL

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String input;
            StringBuilder sb = new StringBuilder();
            while ((input = in.readLine()) != null) {
                sb.append(input);
                sb.append("\n");
            }
            String text = sb.toString();
            // special handling for buggy &nbsp; encoding
            text = text.replaceAll("Â ", " ");
            return text;
        }

        protected Long doInBackground(URL... urls) {
            try {
//                Log.d("Jeka", "Here");

                html = readUrl(url);
                Pattern pattern = Pattern.compile("<h2>([^<>]+)</h2>");
                matcher = pattern.matcher(html);
//                Log.d("Jeka", "Read url " + html.length());

                while (matcher.find()) {
                    String subjectName = matcher.group(1);
                    departments.add(subjectName);
                }
            }catch(Exception e) {
//                Log.d("Jeka", e.toString());
            }

            return 0L;
        }

//        protected void onProgressUpdate(Integer... progress) {
//        }

        protected void onPostExecute(Long result) {
            LinearLayout dep_ll = (LinearLayout)findViewById(R.id.departments_list);
            int counter = 0;

            for (String department: departments) {
                Button btn = new Button(LScienceDivisionActivity.this);
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                btn.setId(counter);
                btn.setText(department);
                counter++;
                dep_ll.addView(btn);
            }
        }
    }

    public void siteSaMI (View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bellevuecollege.edu/sami/"));
        startActivity(browserIntent);
    }

    public void scienceAdvising (View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bellevuecollege.edu/science/advising/"));
        startActivity(browserIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_division);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);
        //thread.start();
        new DownloadDepartmentsTask().execute();

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Science Division");
        setSupportActionBar(toolbar);
    }
}
