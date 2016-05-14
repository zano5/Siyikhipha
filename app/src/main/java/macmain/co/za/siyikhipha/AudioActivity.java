package macmain.co.za.siyikhipha;

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

public class AudioActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAudio;
    private ProgressBar progressBarAudio;
    private static final String TAG = "AudioActivity";
    private RVAudioAdapter audioAdapter;
    private List<Audio> audioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewAudio = (RecyclerView) findViewById(R.id.recylerViewAudio);
        recyclerViewAudio.setLayoutManager(new LinearLayoutManager(this));
        progressBarAudio = (ProgressBar) findViewById(R.id.progressBarAudio);


        final String url= "http://10.0.2.2/webmacmain/json_get_audio.php";

        new AudioTask().execute(url);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu_back) {

            Intent intent = new Intent();
            String message = "You have returned to the Main screen";
            intent.putExtra(AppConstants.MESSAGE,message);
            setResult(RESULT_CANCELED,intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class AudioTask extends AsyncTask<String, Void, Integer> {

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
            progressBarAudio.setVisibility(View.GONE);

            if (result == 1) {
                audioAdapter = new RVAudioAdapter(AudioActivity.this,audioList);
                recyclerViewAudio.setAdapter(audioAdapter);
            } else {
                Toast.makeText(AudioActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("server_audio");
            audioList = new ArrayList<Audio>();

            //int id, String name, String lastest_single,int number_of_albums, String latest_album,String image
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Audio audio= new Audio();
                audio.setArtistName(post.optString("ArtistNaame"));
                audio.setId(Integer.parseInt(post.optString("id")));
                audio.setAudioName(post.optString("audioName"));
                audio.setSongName(post.optString("songName"));
                audio.setPicture(post.optString("picture"));


                audioList.add(audio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
