package macmain.co.za.siyikhipha;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EventScheduleActivity extends AppCompatActivity {

    private List<EventSchedule> eventScheduleList;
    private static  final String TAG = "EventScheduleActivity";
    private ProgressBar progressBarSchedule;
    private RecyclerView rvSchedule;
    private RVEventScheduleAdapter eventScheduleAdapter;
    private List<EventSchedule> lstTester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        rvSchedule = (RecyclerView) findViewById(R.id.rvSchedule);


        rvSchedule.setLayoutManager(new LinearLayoutManager(this));

        progressBarSchedule = (ProgressBar) findViewById(R.id.progressBarSchedule);

        final String url= "http://10.0.2.2/webmacmain/json_get_event_schedule.php";

        new EventScheduleTask().execute(url);



    }



    public class EventScheduleTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {

            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResultTicket(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            progressBarSchedule.setVisibility(View.GONE);

            if (result == 1) {

                int id = 1;





                        String token = eventScheduleList.get(1).getArtist();
                        String [] artists = token.split("#");

                        for(int y= 0; y < artists.length; y++){

                            lstTester.add(new EventSchedule(artists[y]));


                        }



                eventScheduleAdapter= new RVEventScheduleAdapter(EventScheduleActivity.this,lstTester);
                rvSchedule.setAdapter(eventScheduleAdapter);

               //eventScheduleAdapter= new RVEventScheduleAdapter(EventScheduleActivity.this, eventScheduleList);
               //rvSchedule.setAdapter(eventScheduleAdapter);
            } else {
                Toast.makeText(EventScheduleActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResultTicket(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("server_schedule");

            eventScheduleList = new ArrayList<EventSchedule>();


            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);


                EventSchedule eventSchedule = new EventSchedule();

                eventSchedule.setFkEventID(Integer.parseInt(post.optString("fkEventID")));
                eventSchedule.setEventName(post.optString("eventName"));
                eventSchedule.setArtist(post.getString("artist"));
               eventSchedule.setEnd(post.optString("end"));
                eventSchedule.setStart(post.optString("start"));



                eventScheduleList.add(eventSchedule);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
