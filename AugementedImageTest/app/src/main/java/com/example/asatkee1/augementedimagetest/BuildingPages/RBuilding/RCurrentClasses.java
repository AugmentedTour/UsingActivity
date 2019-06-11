package com.example.asatkee1.augementedimagetest.BuildingPages.RBuilding;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asatkee1.augementedimagetest.BuildingPages.AppActivityBuilderMethods;
import com.example.asatkee1.augementedimagetest.R;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.IOUtils.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class RCurrentClasses extends AppActivityBuilderMethods {
    TextView class_list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setLayout(900,1200);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundwhite);

        // --- Toolbar stuff, don't forget to set the name ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("R Building Classes");
        setSupportActionBar(toolbar);

        // --- Gotta put these in the onCreate method ---
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.topLayout);
        LinearLayout bodyLayout = (LinearLayout) findViewById(R.id.bodyLayout);


        // --- topLayout ---
        subTitleBuilder("Current Classes in the R Building", topLayout);


        // --- bodyLayout ---


        class_list = textViewBuilder("Loading...", bodyLayout);
        // --- Async task ---
        final String[] deptlist = {"ABE", "ASL", "ASL&", "BUS", "BUS&", "CMST", "CMST&", "CS", "CES", "DANCE", "DEVED", "ECON", "ECON&",
        "ENGL", "ENGL&", "FRCH", "FRCH&", "HSC", "HD", "INTER", "INDES", "MKTG", "MATH", "MATH&", "MUSC", "MUSC&",
        "OLS", "PHIL", "PHIL&", "RECED", "PE", "POLS", "POLS&", "PSYC", "PSYC&", "SOC", "SOC&", "SPAN",
        "SPAN&", "TRANS"};
        GetInfoTask task = new GetInfoTask();
        task.setCourseTextView(class_list);
        task.setDepartmentList(deptlist);
        task.execute();
    }


    private class GetInfoTask extends AsyncTask<String, Void, String[]> {
        TextView class_view = null;
        Set<String> rtnval = new HashSet<String>();
        String[] course_prefix_list;
        public void setCourseTextView(TextView tv) { class_view = tv; }
        public void setDepartmentList(String[] slugs) {
            course_prefix_list = slugs;
        }
        public String[] getDepartmentList() { return course_prefix_list; };

        protected String[] doInBackground(String... urls) {


            try {
                String info = IOUtils.toString(new URI("https://www2.bellevuecollege.edu/classes/All/?format=json"), Charset.forName("UTF-8"));
                JSONObject obj = new JSONObject(info);
                obj = obj.getJSONObject("CurrentQuarter");
                String currQuarter = obj.get("FriendlyName").toString();
                currQuarter = currQuarter.replaceAll("\\s", "");
                String course_prefix_url = "https://www2.bellevuecollege.edu/classes/"+currQuarter;
                // Now get the class list
                for (int i =0; i < course_prefix_list.length; i++){
                    String course_prefix = course_prefix_list[i];
                    System.out.println("slug = "+course_prefix);
                    String subject_url = course_prefix_url+"/"+course_prefix+"/?format=json";
                    String classes_json = IOUtils.toString(new URI(subject_url), Charset.forName("UTF-8"));
                    JSONObject classes_obj = new JSONObject(classes_json);
                    JSONArray courses_array = classes_obj.getJSONArray("Courses");
                    boolean in_building = false;
                    for (int j = 0; j < courses_array.length(); j++ )
                    {
                        JSONObject course = courses_array.getJSONObject(j);
                        JSONArray sections = course.getJSONArray("Sections");
                        in_building = false;
                        for (int k =0; k < sections.length(); k++) {
                            JSONObject section = sections.getJSONObject(k);
                            String online = section.getString("IsOnline");
                            if (online.equals("false")) {
                                JSONArray offered_arr = section.getJSONArray("Offered");

                                for (int m = 0; m < offered_arr.length(); m++) {
                                    JSONObject offer = offered_arr.getJSONObject(m);

                                    String room = offer.getString("Room");
                                    if (room.charAt(0) == 'R') {
                                        in_building = true;
                                        break;
                                    }

                                }
                                if (in_building) {
                                    String subject = section.getString("CourseSubject");
                                    String number = section.getString("CourseNumber");
                                    String title = section.getString("CourseTitle");
                                    String entry = subject + number + ": " + title;

                                    rtnval.add(entry);

                                }
                            }
                        }
                    }
                };

                String[] rtnstr = new String[rtnval.size()];
                int j = 0;
                for (Iterator<String> it = rtnval.iterator(); it.hasNext(); ) {
                    String s = it.next();
                    rtnstr[j++] = s;
                }
                Arrays.sort(rtnstr);
                return rtnstr;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String[] result) {
            String text = "";
            for (int i = 0; i < result.length; i++ ) {
                text += result[i]+"\n";
                class_view.setText(text);
            }
        }



    }
}

