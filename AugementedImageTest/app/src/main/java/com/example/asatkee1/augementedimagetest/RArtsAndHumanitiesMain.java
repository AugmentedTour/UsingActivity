package com.example.asatkee1.augementedimagetest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class RArtsAndHumanitiesMain extends AppActivityBuilderMethods {

    // Put in the URL this activity will be parsing from
    private final String THIS_ONES_URL = "https://www.bellevuecollege.edu/artshum/";

    //These are the views we'll be altering or parsing for
    TextView mainInfo;
    TextView officeHours;
    TextView importantLinks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Arts and Humanities");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Arts and Humanities Division", topLayout);

        // --- bodyLayout ---
        mainInfo = textViewBuilder("Loading...", bodyLayout);
        officeHours = textViewBuilder("Loading...", bodyLayout);
        linkButtonBuilder("Website", "https://www.bellevuecollege.edu/artshum/", true, bodyLayout);
        activityButtonBuilder("Departments", RArtsAndHumanitiesMain.this, RArtsAndHumanitiesDepartments.class, true, bodyLayout);
        importantLinks = textViewBuilder("Important Links:", bodyLayout);
        linkButtonBuilder("Facebook", "https://www.facebook.com/pages/Arts-Humanities-Bellevue-College/193444107342452", false, bodyLayout);
        linkButtonBuilder("Faculty Resources", "https://www.bellevuecollege.edu/artshum/faculty-resources/", false, bodyLayout);
        linkButtonBuilder("Travel Study Abroad", "http://www.bellevuecollege.edu/studyabroad/", false, bodyLayout);

        // --- Styling ---
        officeHours.setTextSize(15);
        importantLinks.setGravity(Gravity.CENTER);

        // --- Async task ---
        new ParseWebpageTask().execute(THIS_ONES_URL);


    }

    //This is used to parse the webpage. Just due to how different each page's parsing will be,
    //We'll probably need a custom one of these for every activity.
    //Following something similar to this here though should cover that.
    private class ParseWebpageTask extends AsyncTask<String, Void, String[]> {
        protected String[] doInBackground(String... urls) { //this is set up for one url but technically it could be easily changed to accommodate several
            try {
                return grabData(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Use this to set all of the text things
        protected void onPostExecute(String[] result) {
            mainInfo.setText(result[0]);
            officeHours.setText(result[1]);
        }

        //Grab all the data in here and put it into a String[]
        public String[] grabData(String url) throws IOException {
            Document doc = Jsoup.connect(url).get();
            Elements para = doc.getElementsByTag("p");
            Elements hours = doc.getElementsByClass("well");
            String[] strings = {para.first().text(), hours.first().text()};
            return strings;
        }

    }
}
