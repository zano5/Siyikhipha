package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OpenEventActivity extends AppCompatActivity {

    private RecyclerView rvOpenEvent;
    private ProgressBar progressBarOpenEvent;
    private static final String TAG = "OpenEventActivity";
    private List<Event> openEventList;
    private RVOpenEventAdapter openEventAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        rvOpenEvent = (RecyclerView) findViewById(R.id.rvOpenEvent);

        rvOpenEvent.setLayoutManager(new LinearLayoutManager(this));

        progressBarOpenEvent = (ProgressBar) findViewById(R.id.progresBarOpenEvent);

        final String url= "http://10.0.2.2/webmacmain/json_get_open_event.php";

        new OpenEventTask().execute(url);


        rvOpenEvent.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click


                    Intent intent = new Intent(OpenEventActivity.this,OpenEventViewActivity.class);

                       /* String start = openEventList.get(position).getStart();
                        String end = openEventList.get(position).getEnd();
                        String area = openEventList.get(position).getArea();
                        double general = openEventList.get(position).getPrice();
                        double vip = openEventList.get(position).getVip();
                        double door = openEventList.get(position).getDoor();

                        intent.putExtra(AppConstants.AREA,String.valueOf(area));
                        intent.putExtra(AppConstants.END,String.valueOf(end));
                        intent.putExtra(AppConstants.START,String.valueOf(start));
                        intent.putExtra(AppConstants.VIP,String.valueOf(vip));
                        intent.putExtra(AppConstants.GENERAL,String.valueOf(general));
                        intent.putExtra(AppConstants.DOOR,String.valueOf(door));*/


                       int id = RVOpenEventAdapter.openEventList.get(position).getId();

                    intent.putExtra(AppConstants.ID,String.valueOf(id));



                        startActivityForResult(intent,303);








                    }
                })
        );




    }

    public class OpenEventTask extends AsyncTask<String, Void, Integer> {

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
                    parseResult(response.toString());
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
            progressBarOpenEvent.setVisibility(View.GONE);

            if (result == 1) {


               openEventAdapter = new RVOpenEventAdapter(OpenEventActivity.this,openEventList);
                rvOpenEvent.setAdapter(openEventAdapter);
            } else {
                Toast.makeText(OpenEventActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("server_open_event");

            openEventList = new ArrayList<Event>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);

                Event event = new Event();


                event.setId(Integer.parseInt(post.optString("id")));
                event.setTitle(post.optString("title"));
                event.setArea(post.optString("area"));
                event.setStart(post.optString("start"));
                event.setEnd(post.optString("end"));
                event.setPrice(Double.parseDouble(post.optString("general_price")));
                event.setVip(Double.parseDouble(post.optString("vip_price")));
                event.setDoor(Double.parseDouble(post.optString("door_price")));
                event.setDate(post.optString("date"));
                event.setRecommandation(Integer.parseInt(post.optString("recommandation")));
                event.setImage(post.optString("picture"));


                openEventList.add(event);
               // merchandiseList.add(merchandise);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
