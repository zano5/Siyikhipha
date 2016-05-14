package macmain.co.za.siyikhipha;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewFlipper viewFlipper;
    private static final String baseUrlImage = "http://10.0.2.2/webmacmain/images/adverts/";
    private ImageView ivAdvert1,ivAdvert2,ivAdvert3;
    private static String TAG = "MainActivity";
    private String pref = "MyPrefName";
    private SharedPreferences shared;

    
    private TextView tvUserUsername, tvUserEmail;
    private User user;
    private ImageView ivUserContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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











        tvUserEmail = (TextView) findViewById(R.id.tvUserEmail);
        tvUserUsername = (TextView) findViewById(R.id.tvUserUsername);

            ivAdvert1 = (ImageView) findViewById(R.id.ivAdvert1);
            ivAdvert2 = (ImageView) findViewById(R.id.ivAdvert2);
            ivAdvert3 = (ImageView) findViewById(R.id.ivAdvert3);

            advertImage1();
            advertImage2();
            advertImage3();

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(4000);







        /*(ImageSelectionActivity.picture !=null){

            String image = prefs.getString("Image",null);

            ivUserContact.setImageURI(Uri.parse(image));

        }*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void advertImage1(){

        String urlImage= baseUrlImage + "advert1.jpg";
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
                Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                ivAdvert1.setBackground(d);



            }
        }.execute(urlImage);

    }

    public void advertImage2(){

        String urlImage= baseUrlImage + "advert2.jpg";
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
                Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                ivAdvert2.setBackground(d);



            }
        }.execute(urlImage);

    }
    public void advertImage3(){

        String urlImage= baseUrlImage + "advert3.jpg";
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
                Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                ivAdvert3.setBackground(d);



            }
        }.execute(urlImage);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

            Intent intent = new Intent(MainActivity.this,PreferenceActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if(id == R.id.event){

            final String[] items = {"View Exclusive Events", "Add an Event", "View an Upcoming Event"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    String choice = items[item];

                    if(choice == items[0]){

                        Intent intent = new Intent(MainActivity.this,EventActivity.class);
                        startActivityForResult(intent,300);


                    }else if(choice==items[1]){

                        Intent intent = new Intent(MainActivity.this,UploadEventActivity.class);
                        startActivity(intent);

                    }else if(choice== items[2]){

                        Intent intent = new Intent(MainActivity.this,OpenEventActivity.class);
                        startActivity(intent);


                    }


                }
            });

            AlertDialog alert = builder.create();
            alert.show();





        }else if(id == R.id.merchandise){

            //Intent intent = new Intent(MainActivity.this,MerchandiseActivity.class);
            //startActivity(intent);

            final String[] items = {"Upload Cultural Item","View Cultural Items","Upload Clothing","View Clothing lines"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    String choice = items[item];

                    if(choice == items[0]){

                        Intent intent = new Intent(MainActivity.this,UploadCulturalActivity.class);
                        startActivity(intent);


                    }else if(choice == items[1]){
                        Intent intent = new Intent(MainActivity.this,MerchandiseActivity.class);
                        startActivity(intent);
                    }else if(choice == items[2]){

                        Intent intent = new Intent(MainActivity.this,UploadClotheringActivity.class);
                        startActivity(intent);
                    }else if(choice == items[3]){

                        Intent intent = new Intent(MainActivity.this,ClotheringActivity.class);
                        startActivity(intent);
                    }

                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }else if(id == R.id.artist){

            Intent intent = new Intent(MainActivity.this,ArtistActivity.class);
            startActivity(intent);

        }else if(id == R.id.model){

            Intent intent = new Intent(MainActivity.this,ModellingActivity.class);
            startActivity(intent);
        }else if(id == R.id.music){

            Intent intent = new Intent(MainActivity.this,AudioActivity.class);
            startActivityForResult(intent,301);

        }else if(id== R.id.swaziBrewary){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final String[] items = {"Promotions","Bulletin","Real Beer Drinker Game"};

            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    String choice = items[item];

                    if(choice == items[0]){

                            Intent intent = new Intent(MainActivity.this,PromotionalActivity.class);
                            startActivityForResult(intent,304);
                    }

                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data !=null && resultCode == RESULT_CANCELED && requestCode == 300 ){

           String message = data.getStringExtra(AppConstants.MESSAGE);

            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();




        }else if( data!= null && requestCode == RESULT_CANCELED && requestCode == 301 ){

            String message = data.getStringExtra(AppConstants.MESSAGE);
            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
        }


    }



}
