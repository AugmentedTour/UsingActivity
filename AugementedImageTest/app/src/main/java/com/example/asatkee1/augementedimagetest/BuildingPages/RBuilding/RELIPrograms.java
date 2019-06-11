package com.example.asatkee1.augementedimagetest.BuildingPages.RBuilding;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class RELIPrograms extends AppActivityBuilderMethods {

    // Put in the URL this activity will be parsing from
    private final String THIS_ONES_URL = "https://www.bellevuecollege.edu/eli/";

    // Views I'll be editing
    TextView info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ELI Programs");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);

        // --- topLayout ---
        subTitleBuilder("ELI Programs", topLayout);

        // --- bodyLayout ---

        // --- Styling ---

        // --- Async tasks ---
        new RELIPrograms.ParseProgramsWebpageTask().execute("Programs");


    }

    //Does the program buttons
    private class ParseProgramsWebpageTask extends AsyncTask<String, Void, ArrayList> {
        protected ArrayList<String> doInBackground(String... titles) {
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
                linkButtonBuilder(result.get(i).toString(), result.get(i + 1).toString(), false, bodyLayout);
            }
        }

        //Grab all the data in here and put it into an ArrayList<String>, first the name then the URL
        private ArrayList<String> grabData() throws IOException {
            Document doc = Jsoup.connect(THIS_ONES_URL).get();
            Elements start = doc.getElementsByClass("wp-widget wp-widget-global widget_nav_menu");
            ArrayList<String> myStrings = new ArrayList<String>();
            Elements two = start.first().getElementsByTag("a");
            for (Element twoo : two) {
                myStrings.add(twoo.text());
                myStrings.add(twoo.attr("abs:href"));
            }

            return myStrings;
        }

    }

}