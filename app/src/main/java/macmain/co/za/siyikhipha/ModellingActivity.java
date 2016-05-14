package macmain.co.za.siyikhipha;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.annotation.SuppressLint;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class ModellingActivity extends AppCompatActivity {

    private ImageView ivFrontModel1, ivFrontModel2, ivFrontModel3, ivLeadingCountry;

    private Button btnAmateur, btnProfessional;
    private ViewGroup container;
    private static final String baseUrlImage = "http://10.0.2.2/webmacmain/images/amateurmodel/queenbees/";
    private static final String baseCountry ="http://10.0.2.2/webmacmain/images/amateurmodel/leadingcountry/" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_modelling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        btnAmateur = (Button) findViewById(R.id.btnAmateur);
        ivLeadingCountry = (ImageView) findViewById(R.id.ivLeadingCountry);
        btnProfessional =(Button) findViewById(R.id.btnProfessional);
        ivFrontModel1 = (ImageView) findViewById(R.id.ivFrontModell);
        ivFrontModel2 = (ImageView) findViewById(R.id.ivFrontModel2);
        ivFrontModel3 = (ImageView) findViewById(R.id.ivFrontModel3);
        container = (ViewGroup) findViewById(R.id.container);


        model1();
        model2();
        model3();
        country();


        ivFrontModel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ModellingActivity.this);

                LayoutInflater factory = LayoutInflater.from(ModellingActivity.this);

                View view = factory.inflate(R.layout.custom_model_queen,null);

                ImageView ivModelQueen = (ImageView) view.findViewById(R.id.ivModelQueen);

                Drawable d  = ivFrontModel1.getDrawable();

                ivModelQueen.setImageDrawable(d);


                builder.setView(view).setCancelable(true).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();



            }
        });




        btnAmateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ModellingActivity.this, AmateurActivity.class);
                startActivityForResult(intent,200);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null && requestCode == 200 && resultCode == RESULT_OK){

            String message = data.getStringExtra(AppConstants.MESSAGE);

            Toast.makeText(ModellingActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    }

    public void model1() {

        String urlImage = baseUrlImage + "model1.jpg";
        new AsyncTask<String, Void, Bitmap>() {

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

                ivFrontModel1.setImageBitmap(bitmap);

            }
        }.execute(urlImage);
    }

    public void model2() {

        String urlImage = baseUrlImage + "model2.jpg";
        new AsyncTask<String, Void, Bitmap>() {

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

                ivFrontModel2.setImageBitmap(bitmap);

            }
        }.execute(urlImage);
    }

    public void model3() {

        String urlImage = baseUrlImage + "model3.jpg";
        new AsyncTask<String, Void, Bitmap>() {

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

                ivFrontModel3.setImageBitmap(bitmap);

            }
        }.execute(urlImage);
    }

    public void country() {

        String urlImage = baseCountry + "country.jpg";
        new AsyncTask<String, Void, Bitmap>() {

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
                Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                //ivPromotion1.setBackground(d);

                ivLeadingCountry.setBackground(d);

            }
        }.execute(urlImage);
    }



}
