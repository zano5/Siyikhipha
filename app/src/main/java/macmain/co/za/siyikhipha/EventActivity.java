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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private String jsonEvent;
    private RecyclerView rvEvent;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<Event> eventList;
    private RVEventAdapter eAdapter;
    private Event event;
    private ProgressBar progressBar;
    private static final String TAG = "EventRecyclerView";
    private String jsonTicket;
    private List<Ticket> ticketList;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        context = getApplicationContext();

        ticketList = new ArrayList<Ticket>();

        rvEvent = (RecyclerView) findViewById(R.id.recylerViewEvent);

        rvEvent.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        final String ticketUrl = "http://10.0.2.2/webmacmain/json_get_ticket.php";

        new TicketTask().execute(ticketUrl);



        final String url = "http://10.0.2.2/webmacmain/json_get_event.php";

        new EventTask().execute(url);


        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);

        animation.setDuration(50);
                set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        animation.setDuration(100);
                set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.5f);

        rvEvent.setLayoutAnimation(controller);

        rvEvent.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click

                       // Toast.makeText(EventActivity.this, "Clicked me", Toast.LENGTH_LONG).show();




                        Event event = eventList.get(position);
                        Ticket ticket = ticketList.get(position);



                       if (event.getId() == ticket.getFkId()) {

                           Intent intent = new Intent(EventActivity.this, TicketActivity.class);
                           intent.putExtra(AppConstants.TICKET, ticket);
                           startActivityForResult(intent, 100);

                        }else{
                            Toast.makeText(EventActivity.this, "Clicked me", Toast.LENGTH_LONG).show();

                        }
                    }
                })
        );


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu) {

            Intent intent = new Intent();
            String message = "You have returned to the Main screen";
            intent.putExtra(AppConstants.MESSAGE,message);
            setResult(RESULT_CANCELED,intent);
           finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class EventTask extends AsyncTask<String, Void, Integer> {

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
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                eAdapter = new RVEventAdapter(EventActivity.this, eventList);
                rvEvent.setAdapter(eAdapter);
            } else {
                Toast.makeText(EventActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("server_event");
            eventList = new ArrayList<Event>();

            //int id,String title,String area,String start,String end, double price, double vip, String image
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Event event = new Event();
                event.setTitle(post.optString("title"));
                event.setId(Integer.parseInt(post.optString("id")));
                event.setArea(post.getString("area"));
                event.setEnd(post.getString("end"));
                event.setStart(post.getString("start"));
                event.setPrice(Double.parseDouble(post.getString("normal_price")));
                event.setVip(Double.parseDouble(post.getString("vip_price")));
                event.setImage(post.getString("picture"));
                event.setDate(post.getString("date"));

                eventList.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class TicketTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {

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


            if (result !=1) {
                Toast.makeText(EventActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResultTicket(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("server_ticket");
            ticketList = new ArrayList<Ticket>();


            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Ticket ticket = new Ticket();

                ticket.setFkId(Integer.parseInt(post.optString("fkId")));
                ticket.setArea(post.optString("area"));
                ticket.setNumberOfTickets(post.optString("number_of_tickets"));


                ticketList.add(ticket);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
