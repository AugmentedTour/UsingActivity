package com.example.asatkee1.augementedimagetest.BuildingPages.RBuilding;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class RABEMain extends AppActivityBuilderMethods {

    // Put in the URL this activity will be parsing from
    private final String THIS_ONES_URL = "https://www.bellevuecollege.edu/abe/";

    // Views I'll be editing
    TextView info;
    TextView subtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ABE");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("ABE", topLayout);

        // --- bodyLayout ---
        info = textViewBuilder("Loading...", bodyLayout);
        linkButtonBuilder("Apply for Admission", "https://international.bellevuecollege.edu/", true, bodyLayout);
        phoneBuilder("ABE Intake Facilitator", "425-564-2067", bodyLayout);
        textViewBuilder("Or email them at ABE@bellevuecollege.edu", bodyLayout);
        linkButtonBuilder("Schedule an Intake Appointment", "https://abeandged.as.me/schedule.php", false, bodyLayout);
        subtitle = textViewBuilder("Information", bodyLayout);
        linkButtonBuilder("Program Flier", "https://s.bellevuecollege.edu/wp/sites/160/2018/11/ABE.GED_.HS21-Flyer-2018-Winter-POST11132018.pdf", false, bodyLayout);
        linkButtonBuilder("GED Testing Center", "http://www.bellevuecollege.edu/testing/ged/", false, bodyLayout);
        linkButtonBuilder("GED Ready", "https://ged.com/", false, bodyLayout);
        linkButtonBuilder("Fall 2018 orientation evaluation survey", "https://bellevuecollege.evaluationkit.com/Respondent/Survey?id=O4Yg9tG0%2bmitjd0BNBGC3ytlBB0OLWcnPhIs3NX8xEi2b6rbg%2b9Lf0DOJYvLMGW%2bGlD4RokrO2Omn3pCzK%2fCyg%3d%3d", true, bodyLayout);


        // --- Styling ---
        subtitle.setGravity(Gravity.CENTER);

        // --- Async tasks ---
        new RABEMain.ParseProgramsWebpageTask().execute("Important Links");
        new RABEMain.ParseInfoTask().execute();


    }

    //Does the program buttons
    private class ParseProgramsWebpageTask extends AsyncTask<String, Void, ArrayList> {
        protected ArrayList<String> doInBackground(String... title) {
            LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);
            TextView text = textViewBuilder(title[0], bodyLayout);
            text.setGravity(Gravity.CENTER);
            try {
                return grabData();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        //Use this to set all of the text things
        protected void onPostExecute(ArrayList result) {
            LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);
            for (int i = 0; i < result.size(); i = i + 2) {
                linkButtonBuilder(result.get(i).toString(), result.get(i+1).toString(),false,bodyLayout);
            }
        }

        //Grab all the data in here and put it into an ArrayList<String>, first the name then the URL
        public ArrayList<String> grabData() throws IOException {
            Document doc = Jsoup.connect(THIS_ONES_URL).get();
            Elements start = doc.getElementsByClass("wp-widget wp-widget-global widget_nav_menu");
            ArrayList<String> myStrings = new ArrayList<String>();
            Elements two = start.next().first().getElementsByTag("a");
            for (Element twoo : two) {
                myStrings.add(twoo.text());
                myStrings.add(twoo.attr("abs:href"));
            }

            return myStrings;
        }

    }

    private class ParseInfoTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            try {
                return grabData(THIS_ONES_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Use this to set all of the text things
        protected void onPostExecute(String result) {
            info.setText(result);
        }

        //Grab all the data in here and put it into a String[]
        public String grabData(String url) throws IOException {
            Document doc = Jsoup.connect(url).get();
            Elements content = doc.getElementsByClass("content-padding");
            Element fullFirst = content.first();
            Elements splitFirst = fullFirst.getElementsByTag("p");
            return splitFirst.first().text();
        }

    }

}
