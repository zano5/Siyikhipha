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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
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

public class AmateurViewActivity extends AppCompatActivity {

    private RecyclerView rvAmateurModel;
    private ProgressBar progressBarAmateurModel;
    private List<Amateur> amateurList;
    private RVAmateurModelAdapter modelAdapter;
    private static final String TAG = "AmateurViewActivity";
    private Context context;
    private List<Amateur> amateurs;
   //private static final String TAG ="ArtistActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amateur_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        amateurs = new ArrayList<Amateur>();
        rvAmateurModel = (RecyclerView) findViewById(R.id.recylerViewAmateurModel);
        rvAmateurModel.setLayoutManager(new LinearLayoutManager(this));
        progressBarAmateurModel = (ProgressBar) findViewById(R.id.progressbarAmateurModel);



        final String url= "http://10.0.2.2/webmacmain/json_get_amateur_model.php";
        //  final String url= "http://10.0.2.2/webmacmain/json_get_artist.php";

        new AmateurViewTask().execute(url);


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

        rvAmateurModel.setLayoutAnimation(controller);



      rvAmateurModel.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click




                        int id= amateurs.get(position).getId();

                        String num = String.valueOf(id);

                        Intent trans = new Intent(AmateurViewActivity.this, ModelRatingActivity.class);

                         trans.putExtra(AppConstants.ID,num);
                        startActivity(trans);





                    }
                })
        );


    }

    public class AmateurViewTask extends AsyncTask<String, Void, Integer> {

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
            progressBarAmateurModel.setVisibility(View.GONE);

            if (result == 1) {
                modelAdapter = new RVAmateurModelAdapter(AmateurViewActivity.this,amateurList);
                rvAmateurModel.setAdapter(modelAdapter);
            } else {
                Toast.makeText(AmateurViewActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("server_amateur");
            amateurList = new ArrayList<Amateur>();

            //int id, String name, String lastest_single,int number_of_albums, String latest_album,String image
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                Amateur amateur = new Amateur();
                amateur.setName(post.optString("name"));
                amateur.setId(Integer.parseInt(post.optString("id")));
                amateur.setView(Integer.parseInt(post.getString("view")));
                amateur.setRating(Integer.parseInt(post.getString("rating")));
                amateur.setLike(Integer.parseInt(post.getString("like")));
                amateur.setPicture(post.getString("picture"));


                amateurList.add(amateur);
                amateurs.add(amateur);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
