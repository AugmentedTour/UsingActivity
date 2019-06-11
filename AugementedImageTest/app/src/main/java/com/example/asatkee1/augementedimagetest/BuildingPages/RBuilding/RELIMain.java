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

public class RELIMain extends AppActivityBuilderMethods {

    // Put in the URL this activity will be parsing from
    private final String THIS_ONES_URL = "https://www.bellevuecollege.edu/eli/";

    // Views I'll be editing
    TextView info;
    TextView officeHours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("English Language Institute");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("English Language Institute (ELI)", topLayout);

        // --- bodyLayout ---
        info = textViewBuilder("Loading...", bodyLayout);
        officeHours = textViewBuilder("Loading...", bodyLayout);
        linkButtonBuilder("Apply for Admission", "https://international.bellevuecollege.edu/", true, bodyLayout);
        activityButtonBuilder("Programs", RELIMain.this, RELIPrograms.class, true, bodyLayout);

        // --- Styling ---

        // --- Async tasks ---
        new RELIMain.ParseLinksWebpageTask().execute("Important Links");
        new RELIMain.ParseInfoTask().execute();


    }

    //Does the program buttons
    private class ParseProgramsWebpageTask extends AsyncTask<String, Void, ArrayList> {
        protected ArrayList<String> doInBackground(String... titles) {
            LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);
            int counter = 0; //I know this is bad code but this is how many times it goes forward in the linked list and I'm right before my finals
                for (String title : titles) {
                    TextView text = textViewBuilder(title, bodyLayout);
                    text.setGravity(Gravity.CENTER);
                    try {
                        return grabData(counter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        private ArrayList<String> grabData(int counter) throws IOException {
            Document doc = Jsoup.connect(THIS_ONES_URL).get();
            Elements start = doc.getElementsByClass("wp-widget wp-widget-global widget_nav_menu");
            ArrayList<String> myStrings = new ArrayList<String>();
            Element it = start.first();
            for (int i = 0; i < counter; ++i) {
                it = it.nextElementSibling();
            }
            Elements two = it.getElementsByTag("a");
            for (Element twoo : two) {
                myStrings.add(twoo.text());
                myStrings.add(twoo.attr("abs:href"));
            }

            return myStrings;
        }

    }

    //Does the program buttons
    private class ParseLinksWebpageTask extends AsyncTask<String, Void, ArrayList> {
        protected ArrayList<String> doInBackground(String... titles) {
            LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);
            int counter = 1; //I know this is bad code but this is how many times it goes forward in the linked list and I'm right before my finals
            for (String title : titles) {
                TextView text = textViewBuilder(title, bodyLayout);
                text.setGravity(Gravity.CENTER);
                try {
                    return grabData(counter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        private ArrayList<String> grabData(int counter) throws IOException {
            Document doc = Jsoup.connect(THIS_ONES_URL).get();
            Elements start = doc.getElementsByClass("wp-widget wp-widget-global widget_nav_menu");
            ArrayList<String> myStrings = new ArrayList<String>();
            Element it = start.first();
            for (int i = 0; i < counter; ++i) {
                it = it.nextElementSibling();
            }
            Elements two = it.getElementsByTag("a");
            for (Element twoo : two) {
                myStrings.add(twoo.text());
                myStrings.add(twoo.attr("abs:href"));
            }

            return myStrings;
        }

    }

    private class ParseInfoTask extends AsyncTask<String, Void, String[]> {
        protected String[] doInBackground(String... urls) {
            try {
                return grabData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Use this to set all of the text things
        protected void onPostExecute(String[] result) {
            info.setText(result[0]);
            officeHours.setText(result[1]);
        }

        //Grab all the data in here and put it into a String[]
        public String[] grabData() throws IOException {
            Document doc = Jsoup.connect(THIS_ONES_URL).get();
//            Elements content = doc.getElementsByClass("content-padding");
//            List<String> finder = content.eachText();
//            return finder.get(0);
            Elements content = doc.getElementsByClass("content-padding");
            Element fullFirst = content.next().next().first();
            Elements splitFirst = fullFirst.getElementsByTag("p");

            String[] strings = {splitFirst.first().text(), splitFirst.next().next().next().next().next().first().text()};

            return strings;
        }

    }

}
