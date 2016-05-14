package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class UploadEventActivity extends AppCompatActivity {

    private EditText etOpentEventArea, etOpenEVentTitle, etOpenGeneralPrice, etOpenVipPrice,etOpenDoorPrice, etOpenEventDate;
    private TimePicker startPicker, endPicker;
    private Button btnUploadOpenEvent;
    private ImageView ivOpenEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        etOpenEVentTitle = (EditText) findViewById(R.id.etOpenEventTitle);
        etOpenGeneralPrice = (EditText) findViewById(R.id.etOpenGeneralPrice);
        etOpentEventArea = (EditText) findViewById(R.id.etOpenEventArea);
        etOpenVipPrice = (EditText) findViewById(R.id.etOpenVipPrice);
        etOpenDoorPrice = (EditText) findViewById(R.id.etOpenDoorPrice);
        etOpenEventDate = (EditText) findViewById(R.id.etOpenEventDate);
        ivOpenEvent = (ImageView) findViewById(R.id.ivOpenEvent);

        startPicker = (TimePicker) findViewById(R.id.startPicker);
        endPicker = (TimePicker) findViewById(R.id.endPicker);
        btnUploadOpenEvent = (Button) findViewById(R.id.btnUploadOpenEvent);

        startPicker.setIs24HourView(true);
        endPicker.setIs24HourView(true);

        ivOpenEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image For Upload"), 6);
            }
        });


        etOpenEVentTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnUploadOpenEvent.setEnabled(!etOpenEVentTitle.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnUploadOpenEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






               if(etOpenDoorPrice.getText().toString().isEmpty()){

                   Toast.makeText(UploadEventActivity.this,"The Door Price field must be field",Toast.LENGTH_LONG).show();

                }else if(etOpenVipPrice.getText().toString().isEmpty()){

                   Toast.makeText(UploadEventActivity.this,"The Vip Price field must be field",Toast.LENGTH_LONG).show();
                }else if(etOpentEventArea.getText().toString().isEmpty()){

                   Toast.makeText(UploadEventActivity.this,"The Area field must be field",Toast.LENGTH_LONG).show();
                }else if(etOpenGeneralPrice.getText().toString().isEmpty()){
                   Toast.makeText(UploadEventActivity.this,"The General Price field must be field",Toast.LENGTH_LONG).show();

                }else if(etOpenEventDate.getText().toString().isEmpty()){

                   Toast.makeText(UploadEventActivity.this,"The Date field must be field",Toast.LENGTH_LONG).show();

                }else{

                   String startTime ="" + startPicker.getCurrentHour()+ ":" + startPicker.getCurrentMinute();
                   String endTime ="" + endPicker.getCurrentHour()+ ":" + endPicker.getCurrentMinute();
                   String title = etOpenEVentTitle.getText().toString();
                   String area = etOpentEventArea.getText().toString();
                   String vip = etOpenVipPrice.getText().toString();
                   String general = etOpenGeneralPrice.getText().toString();
                   String door =etOpenDoorPrice.getText().toString();
                   String date = etOpenEventDate.getText().toString();
                   String picture = title.trim() + ".jpg";
                   String image = title.trim();



                    UploadEventTask event = new UploadEventTask(UploadEventActivity.this);

                   event.execute(title,area,startTime,endTime,general,vip,door,date,picture);


                   Bitmap bitmap = ((BitmapDrawable) ivOpenEvent.getDrawable()).getBitmap();


                    new UploadEventImageTask(bitmap,image).execute();


                   etOpenDoorPrice.setText("");
                   etOpentEventArea.setText("");
                   etOpenEVentTitle.setText("");
                   etOpenGeneralPrice.setText("");
                   etOpenVipPrice.setText("");
                   etOpenEventDate.setText("");


                }
            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 6) {




                ivOpenEvent.setImageURI(data.getData());

               // Bitmap image = (Bitmap) data.getData();

                //Bitmap z = Bitmap.



                //Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                Uri selectedImage = data.getData();

                //String image = getPathImage(selectedImage);




               // Drawable d = Drawable.createFromPath(image);

                //ivOpenEvent.setBackground(d);



            }

        }
    }

    public class UploadEventTask extends AsyncTask<String,Void,String> {

        private Context context;

        UploadEventTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "http://10.0.2.2/webmacmain/upload_open_event.php";


            //name,surname,username,email,sex,province,password


            String title = params[0];
            String area = params[1];
            String start= params[2];
            String end = params[3];
            String general = params[4];
            String vip = params[5];
            String door = params[6];
            String date = params[7];
            String picture = params[8];


            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8") + "&" +
                        URLEncoder.encode("area", "UTF-8") + "=" + URLEncoder.encode(area, "UTF-8") + "&" +
                        URLEncoder.encode("start", "UTF-8") + "=" + URLEncoder.encode(start, "UTF-8") + "&" +
                        URLEncoder.encode("end", "UTF-8") + "=" + URLEncoder.encode(end, "UTF-8") + "&" +
                        URLEncoder.encode("general", "UTF-8") + "=" + URLEncoder.encode(general, "UTF-8") + "&"
                        + URLEncoder.encode("vip", "UTF-8") + "=" + URLEncoder.encode(vip, "UTF-8") + "&"
                        + URLEncoder.encode("door", "UTF-8") + "=" + URLEncoder.encode(door, "UTF-8")+"&"
                        + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")+ "&"
                        + URLEncoder.encode("picture", "UTF-8") + "=" + URLEncoder.encode(picture, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                is.close();
                httpURLConnection.disconnect();
                return "Successfully Uploaded details...";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }





            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {


        }
    }

    private class UploadEventImageTask extends AsyncTask<Void, Void, Void> {

        private Bitmap image;
        private  String name;

        public UploadEventImageTask(Bitmap image, String name){
            this.image= image;
            this.name = name;
        }




        @Override
        protected Void doInBackground(Void... params) {

            String upLoadServerUri = "http://10.0.2.2/webmacmain/upload_cultural_image.php";
            //http://10.0.2.2/webmacmain/registration.php"
            //image_upload



            URL url = null;




            try {


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);









                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                url = new URL(upLoadServerUri);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") +"="+ URLEncoder.encode(name, "UTF-8") +"&"+
                        URLEncoder.encode("image", "UTF-8") +"="+ URLEncoder.encode(encodedImage, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                is.close();
                httpURLConnection.disconnect();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }




            return null;

        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(UploadEventActivity.this,"Upload successful", Toast.LENGTH_LONG).show();
        }
    }

}
