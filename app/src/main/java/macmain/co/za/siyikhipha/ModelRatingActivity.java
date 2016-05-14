package macmain.co.za.siyikhipha;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ModelRatingActivity extends AppCompatActivity {

    private ImageView ivRatingModel;
    private RatingBar ratingBarModel;
    private ImageButton ibModelLike;
    private Button btnRatingModel;
    private float ratingNum;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ivRatingModel = (ImageView) findViewById(R.id.ivRatingModel);
        ratingBarModel = (RatingBar) findViewById(R.id.ratingBarModel);
        ibModelLike = (ImageButton) findViewById(R.id.ibModelLike);
        btnRatingModel = (Button) findViewById(R.id.btnRatingModel);

        Intent intent = getIntent();

        String id = (intent.getStringExtra(AppConstants.ID));
         num = Integer.parseInt(id);

    // Amateur amateur = (Amateur) intent.getSerializableExtra(AppConstants.AMATEURMODEL);




        for(int x= 0; x< RVAmateurModelAdapter.amateurList.size(); x++) {

            if ( RVAmateurModelAdapter.amateurList.get(x).getId() == num) {
                //Drawable d = new BitmapDrawable(getResources(), RVAmateurModelAdapter.amateurList.get(x).getBitImage());
                //ivRatingModel.setBackground(d);
                ivRatingModel.setImageBitmap(RVAmateurModelAdapter.amateurList.get(x).getBitImage());
            }
        }


        ratingBarModel.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                ratingNum = rating;

            }
        });

        btnRatingModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RatingTask rating = new RatingTask(ModelRatingActivity.this);
                String method = "rating";


                rating.execute(method,String.valueOf(num),String.valueOf(ratingNum));

            }
        });





    }

    public class RatingTask extends AsyncTask<String,Void,String> {

        Context context;



        RatingTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(ModelRatingActivity.this,result, Toast.LENGTH_SHORT).show();

            btnRatingModel.setEnabled(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected String doInBackground(String... params) {
            String rating_url = "http://10.0.2.2/webmacmain/rating.php";

            String method = params[0];
            //name,surname,username,email,sex,province,password
            if (method.equals("rating")) {

                String id = params[1];
                String rating = params[2];


                try {
                    URL url = new URL(rating_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                            URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(rating, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream is = httpURLConnection.getInputStream();
                    is.close();
                    httpURLConnection.disconnect();
                    return "Picture rating successful...";

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
                    return  null;
        }

    }
}
