package com.example.asatkee1.augementedimagetest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScienceDivisionActivity extends AppActivityBuilderMethods {
    private class DownloadDepartmentsTask extends AsyncTask<URL, Integer, Long> {
        List<String> departments = new ArrayList<>();

        String url = "https://www.bellevuecollege.edu/science/departments/";
        public String html;
        public Matcher matcher;
        public LinearLayout topLayout = (LinearLayout)findViewById(R.id.topLayout);
        public LinearLayout bodyLayout = (LinearLayout)findViewById(R.id.bodyLayout);

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
                Pattern pattern1 = Pattern.compile("<h2>([^<>]+)</h2>");
                matcher = pattern1.matcher(html);
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

        public void onPostExecute(Long result) {
//            LinearLayout dep_ll = (LinearLayout)findViewById(R.id.departments_list);
            LinearLayout bodyLayout = (LinearLayout)findViewById(R.id.bodyLayout);
//            int counter = 0;

            for (String department: departments) {
                linkButtonBuilder (department, "https://www.bellevuecollege.edu/", true, bodyLayout);
//                Button btn = new Button(ScienceDivisionActivity.this);
//                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                btn.setId(counter);
//                btn.setText(department);
//                counter++;
//                dep_ll.addView(btn);
            }
        }


        Button siteSaMI = linkButtonBuilder ("SCIENCE AND MATH INSTITUTE", "https://www.bellevuecollege.edu/sami/", true, bodyLayout);
        Button scienceAdvising = linkButtonBuilder ("SCIENCE ADVISING", "https://www.bellevuecollege.edu/science/advising/", true, bodyLayout);

    }

//    public void siteSaMI (View view){
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bellevuecollege.edu/sami/"));
//        startActivity(browserIntent);
//    }

//    public void scienceAdvising (View view){
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bellevuecollege.edu/science/advising/"));
//        startActivity(browserIntent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //thread.start();
        getWindow().setLayout(970,1400);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Science Division");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Science Division", topLayout);

        // --- Toolbar stuff, don't forget to set the name ---
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Science Division");
//        setSupportActionBar(toolbar);

        new DownloadDepartmentsTask().execute();
    }
}
