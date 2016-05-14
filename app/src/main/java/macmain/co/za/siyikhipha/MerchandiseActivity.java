package macmain.co.za.siyikhipha;

import android.content.SharedPreferences;
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

public class MerchandiseActivity extends AppCompatActivity {

    private static final String TAG = "MerchandiseActivity";
    private List<Merchandise> merchandiseList;
    private ProgressBar progressBar;
    private RecyclerView rvMerchandise;
    private RVMerchandiseAdapter merchandiseAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        rvMerchandise = (RecyclerView) findViewById(R.id.recylerViewMerchandise);

        rvMerchandise.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.progressBarMerchandise);
        //json_get_merchandise.php
        final String url= "http://10.0.2.2/webmacmain/json_get_merchandise.php";

        new MerchandiseTask().execute(url);
    }

    public class MerchandiseTask extends AsyncTask<String, Void, Integer> {

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
                merchandiseAdapter = new RVMerchandiseAdapter(MerchandiseActivity.this, merchandiseList);
                rvMerchandise.setAdapter(merchandiseAdapter);
            } else {
                Toast.makeText(MerchandiseActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("server_merchandise");
            merchandiseList= new ArrayList<Merchandise>();
            //server_merchandise
            //int id,String title,String area,String start,String end, double price, double vip, String image
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Merchandise merchandise= new Merchandise();
                merchandise.setTitle(post.optString("title"));
                merchandise.setId(Integer.parseInt(post.optString("id")));
                merchandise.setArea(post.getString("area"));
                merchandise.setCity(post.getString("city"));
                merchandise.setPrice(Double.parseDouble(post.getString("price")));
                merchandise.setPicture(post.getString("picture"));
                merchandise.setTown(post.getString("town"));
                merchandise.setContact(post.getString("contact"));

                merchandiseList.add(merchandise);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
