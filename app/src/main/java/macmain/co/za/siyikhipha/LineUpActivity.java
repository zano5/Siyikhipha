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

public class LineUpActivity extends AppCompatActivity {

    private RecyclerView rvLineUp;
    private ProgressBar progresBarLineUp;
    private static final String TAG = "LineUpActivity";

    private List<LineUp> lineUpList;
    private List<Line> lineList;
    private RVLineUpAdapter lineUpAdapter;
    private int id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_up);
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

        Intent intent = getIntent();

        String num = intent.getStringExtra(AppConstants.ID);
        id = Integer.parseInt(num);

        lineList = new ArrayList<Line>();

        rvLineUp = (RecyclerView) findViewById(R.id.rvLineUp);


        rvLineUp.setLayoutManager(new LinearLayoutManager(this));

        progresBarLineUp = (ProgressBar) findViewById(R.id.progresBarLineUp);

        final String url= "http://10.0.2.2/webmacmain/json_get_line_up.php";

        new LineUpTask().execute(url);














    }



    public class LineUpTask extends AsyncTask<String, Void, Integer> {

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
            progresBarLineUp.setVisibility(View.GONE);

            if (result == 1) {
                //  merchandiseAdapter = new RVMerchandiseAdapter(MerchandiseActivity.this, merchandiseList);
                //  rvMerchandise.setAdapter(merchandiseAdapter);

                //  lineUpAdapter = new RVLineUpAdapter(LineUpActivity.this,lineUpList);
                // rvLineUp.setAdapter(lineUpAdapter);

                for(int a= 0; a < lineUpList.size(); a ++){

                    if(id == lineUpList.get(a).getFkEventID()){

                        lineList.add(new Line(lineUpList.get(a).getArtistName(),lineUpList.get(a).getStart(),lineUpList.get(a).getEnd(),lineUpList.get(a).getPicture()));
                    }


                }


                lineUpAdapter = new RVLineUpAdapter(LineUpActivity.this,lineList);

                rvLineUp.setAdapter(lineUpAdapter);




            }else{
                Toast.makeText(LineUpActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();

            }


        }

        private void parseResult(String result) {
            try {
                JSONObject response = new JSONObject(result);
                JSONArray posts = response.optJSONArray("server_line");
                // merchandiseList= new ArrayList<Merchandise>();

                lineUpList = new ArrayList<LineUp>();
                //server_merchandise
                //int id,String title,String area,String start,String end, double price, double vip, String image
                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.optJSONObject(i);


                    LineUp lineup = new LineUp();


                    lineup.setId(Integer.parseInt(post.optString("id")));
                    lineup.setFkEventID(Integer.parseInt(post.optString("fkEventId")));
                    lineup.setArtistName(post.optString("artistName"));
                    lineup.setStart(post.optString("start"));
                    lineup.setEnd(post.optString("end"));
                    lineup.setPicture(post.optString("picture"));

                    lineUpList.add(lineup);
                    //merchandiseList.add(merchandise);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
