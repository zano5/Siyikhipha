package macmain.co.za.siyikhipha;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PromotionalActivity extends AppCompatActivity {

    private RecyclerView rvPromonational;
    private ProgressBar progressBarPromonational;
    private ViewFlipper vfPromotion;
    private ImageView ivPromotion1, ivPromotion2, ivPromotion3;
    private static final String baseUrlImage = "http://10.0.2.2/webmacmain/images/promotion/prom/";
    private List<Promotion> promotionList;
    private static final String TAG = "PromotionalActivity";
    private RVPromotionAdapter promotionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotional);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vfPromotion = (ViewFlipper) findViewById(R.id.vfPromotion);
        ivPromotion1 = (ImageView) findViewById(R.id.ivPromotional1);
        ivPromotion2 = (ImageView) findViewById(R.id.ivPromotional2);
        ivPromotion3 = (ImageView) findViewById(R.id.ivPromotional3);

        prom1();
        prom2();
        prom3();
        vfPromotion.setAutoStart(true);
        vfPromotion.setFlipInterval(4000);

        rvPromonational = (RecyclerView) findViewById(R.id.rvPromotional);

        rvPromonational.setLayoutManager(new LinearLayoutManager(this));


        progressBarPromonational = (ProgressBar) findViewById(R.id.progresBarPromotional);

        final String url= "http://10.0.2.2/webmacmain/json_get_promotion.php";

        new PromotionTask().execute(url);


    }

    public void prom1(){

        String urlImage= baseUrlImage + "prom1.jpg";
        new AsyncTask<String,Void,Bitmap>(){

            @Override
            protected Bitmap doInBackground(String... params) {

                //Download image

                String url = params[0];
                Bitmap icon = null;

                try {
                    InputStream in = new java.net.URL(url).openStream();
                    icon = BitmapFactory.decodeStream(in);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return icon;
            }


            @Override
            protected void onPostExecute(Bitmap bitmap) {


                Bitmap image = bitmap;
               // Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                //ivPromotion1.setBackground(d);

                ivPromotion1.setImageBitmap(bitmap);

            }
        }.execute(urlImage);
    }

    public void prom2(){

        String urlImage= baseUrlImage + "prom2.jpg";
        new AsyncTask<String,Void,Bitmap>(){

            @Override
            protected Bitmap doInBackground(String... params) {

                //Download image

                String url = params[0];
                Bitmap icon = null;

                try {
                    InputStream in = new java.net.URL(url).openStream();
                    icon = BitmapFactory.decodeStream(in);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return icon;
            }


            @Override
            protected void onPostExecute(Bitmap bitmap) {


                Bitmap image = bitmap;
                //Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                //ivPromotion2.setBackground(d);
                ivPromotion2.setImageBitmap(bitmap);



            }
        }.execute(urlImage);
    }

    public void prom3(){

        String urlImage= baseUrlImage + "prom3.jpg";
        new AsyncTask<String,Void,Bitmap>(){

            @Override
            protected Bitmap doInBackground(String... params) {

                //Download image

                String url = params[0];
                Bitmap icon = null;

                try {
                    InputStream in = new java.net.URL(url).openStream();
                    icon = BitmapFactory.decodeStream(in);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return icon;
            }


            @Override
            protected void onPostExecute(Bitmap bitmap) {


                Bitmap image = bitmap;
               // Drawable d = new BitmapDrawable(Resources.getSystem(),image);
               // ivPromotion3.setBackground(d);

                ivPromotion3.setImageBitmap(bitmap);



            }
        }.execute(urlImage);
    }

    public class PromotionTask extends AsyncTask<String, Void, Integer> {

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
            progressBarPromonational.setVisibility(View.GONE);

            if (result == 1) {



                promotionAdapter = new RVPromotionAdapter(PromotionalActivity.this,promotionList);
                rvPromonational.setAdapter(promotionAdapter);



            } else {
                Toast.makeText(PromotionalActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("server_promotion");
           promotionList = new ArrayList<Promotion>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);


                Promotion promotion = new Promotion();

                promotion.setId(Integer.parseInt(post.optString("id")));
                promotion.setName(post.optString("name"));
                promotion.setStart(post.optString("start"));
                promotion.setEnd(post.optString("end"));
                promotion.setPrice(Double.parseDouble(post.optString("price")));
                promotion.setPicture(post.optString("picture"));


                promotionList.add(promotion);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
