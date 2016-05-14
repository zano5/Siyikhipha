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

public class ClotheringActivity extends AppCompatActivity {

    private List<Clothering> clotheringList;
    private RVClotheringAdapter clotheringAdapter;

    private RecyclerView rvClothering;
    private ProgressBar progressBarClothering;
    private  static final String TAG = "ClotheringActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothering);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        rvClothering = (RecyclerView) findViewById(R.id.rvClothering);

        rvClothering.setLayoutManager(new LinearLayoutManager(this));

        progressBarClothering = (ProgressBar) findViewById(R.id.progressBarClotherng);

        final String url= "http://10.0.2.2/webmacmain/json_get_clothering.php";

        new ClotheringTask().execute(url);


    }

    public class ClotheringTask extends AsyncTask<String, Void, Integer> {

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
            progressBarClothering.setVisibility(View.GONE);

            if (result == 1) {
                clotheringAdapter = new RVClotheringAdapter(ClotheringActivity.this, clotheringList);
                rvClothering.setAdapter(clotheringAdapter);
            } else {
                Toast.makeText(ClotheringActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("server");

            clotheringList = new ArrayList<Clothering>();



            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);

                Clothering cloth = new Clothering();

                cloth.setId(Integer.parseInt(post.optString("id")));
                cloth.setProducer(post.optString("producer"));
                cloth.setLine(post.optString("line"));
                cloth.setContact(post.optString("contact"));
                cloth.setPrice(Double.parseDouble(post.optString("price")));
                cloth.setPicture(post.optString("picture"));


                clotheringList.add(cloth);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
