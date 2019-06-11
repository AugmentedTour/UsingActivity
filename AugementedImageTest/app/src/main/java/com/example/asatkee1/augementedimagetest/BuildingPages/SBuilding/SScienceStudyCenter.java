package com.example.asatkee1.augementedimagetest.BuildingPages.SBuilding;

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

public class SScienceStudyCenter extends AppActivityBuilderMethods {

    // Put in the URL this activity will be parsing from
    private final String THIS_ONES_URL = "http://scidiv.bellevuecollege.edu/ssc/Hours-as-List.html";

    //These are the views we'll be altering or parsing for

    TextView tutorHours;
    TextView overView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Science Study Center");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Science Study Center", topLayout);





        // --- bodyLayout ---

        String info = "The study center is located in S114.\n\n" +
                "Available resources include computers, printers, biology slides, and skeleton models." +
                "Tutors are available during study center hours. See the schedule for when a subject is offered. \n";

        textViewBuilder(info, bodyLayout);


        linkButtonBuilder("Website", "http://scidiv.bellevuecollege.edu/ssc/", true, bodyLayout);

        tutorHours = textViewBuilder("Loading...", bodyLayout);
        tutorHours.setTextSize(15);
        tutorHours.setMaxLines(150); // needed to enable text wrap
        tutorHours.setHorizontallyScrolling(false);

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
            String alltext = "";
            for (int i = 0; i < result.length; i++) {
                alltext += result[i]+"\n";
            }
            tutorHours.setText(alltext);
        }

        //Grab all the data in here and put it into a String[]
        public String[] grabData(String url) throws IOException {
            ArrayList<String> s = new ArrayList<String>();
            Document doc = Jsoup.connect(url).get();
            Elements paras = doc.body().getElementsByTag("p");
            paras.remove(0); //top of page
            paras.remove(0); // null paragraph
 // First para is just text.
            Elements uls = doc.body().getElementsByTag("ul");

            // First paragraph is the title "Tutor Office Hourse"
            int headidx = 0;
            s.add(paras.get(headidx++).text());
            for (int i = 0; i < uls.size(); i++) {
                s.add("");
                s.add(paras.get(headidx++).text());
                Elements lis = uls.get(i).children();
                for (Element item: lis){
                    s.add("\t"+item.text());
                }
            }

            String[] strings = new String[s.size()];
            for (int i = 0; i < s.size(); i++)
            {
                strings[i] = s.get(i);
            }
            return strings;
            //System.out.println();
        }
    }

}

