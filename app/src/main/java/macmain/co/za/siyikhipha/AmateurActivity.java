package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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


public class AmateurActivity extends AppCompatActivity {

    private Button btnAmateurConfirm;
    private ImageView ivAmateur;
    private EditText etAmateurName, etAmateurImageName;
    private String image;


    private int serverResponseCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_amateur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnAmateurConfirm = (Button) findViewById(R.id.btnAmateurConfirm);
        ivAmateur = (ImageView) findViewById(R.id.ivAmateur);
        etAmateurName = (EditText) findViewById(R.id.etAmateurName);
        etAmateurImageName = (EditText) findViewById(R.id.etAmateurImageName);




        etAmateurName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnAmateurConfirm.setEnabled(!etAmateurName.getText().toString().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        ivAmateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image For Upload"), 1);*/



               Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickIntent.setType("image/*");

                Intent takePhotoIntent;
                 takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent chooserIntent = Intent.createChooser(pickIntent,
                        "Choose An Action");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,

                new Intent[]{takePhotoIntent});
                startActivityForResult(chooserIntent, 1);
            }
        });


        btnAmateurConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etAmateurName.getText().toString().equals("")) {

                    Toast.makeText(AmateurActivity.this, "The name field must be field in", Toast.LENGTH_LONG).show();
                } else if (etAmateurImageName.getText().toString().equals("")) {

                    Toast.makeText(AmateurActivity.this, "The image name field must be field in", Toast.LENGTH_LONG).show();
                } else {

                    Bitmap bitmap = ((BitmapDrawable) ivAmateur.getDrawable()).getBitmap();

                    String joinName= etAmateurName.getText().toString().trim() + etAmateurImageName.getText().toString();

                    new UploadImage(bitmap, joinName).execute();

                    UploadImageInfo uploadInfo = new UploadImageInfo();

                    String method = "uploadInfo";
                    String name = etAmateurName.getText().toString().trim();
                    String imageName= name+  etAmateurImageName.getText().toString().trim();
                    String picture=imageName + ".jpg";

                    uploadInfo.execute(method,name,imageName,picture);

                    etAmateurName.setText("");
                    etAmateurImageName.setText("");

                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.amateur_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_amateur_model) {
            Intent intent = new Intent(AmateurActivity.this,AmateurViewActivity.class);
            startActivity(intent);


            return true;
        }else if(id == R.id.action_back){

            Intent intent = new Intent();

            String message = "You have returned to the Modelling screen";

            intent.putExtra(AppConstants.MESSAGE,message);
            setResult(RESULT_OK,intent);
            finish();

            return true;
        }else if(id == R.id.action_menu){

            Intent intent = new Intent(AmateurActivity.this,MainActivity.class);
            startActivity(intent);




           // Intent intent = new Intent(AmateurActivity.this,MainActivity.class);
            //startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1)

                ivAmateur.setImageURI(data.getData());



                Uri selectedImage = data.getData();

                image = getPath(selectedImage);




        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private class UploadImage extends AsyncTask<Void, Void, Void> {

      private  Bitmap image;
      private  String name;

        public UploadImage(Bitmap image, String name){
            this.image= image;
            this.name = name;
        }





        @Override
        protected Void doInBackground(Void... params) {

            String upLoadServerUri = "http://10.0.2.2/webmacmain/image_upload.php";
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

            Toast.makeText(AmateurActivity.this,"Upload successful", Toast.LENGTH_LONG).show();
        }
    }

    private class UploadImageInfo extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... params) {
            String upload_info_url = "http://10.0.2.2/webmacmain/amateur_upload_info.php";

            String method = params[0];
            //name,surname,username,email,sex,province,password
            if (method.equals("uploadInfo")) {

                String name = params[1];
                String imageName= params[2];
                String picture= params[3];



                try {
                    URL url = new URL(upload_info_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("imageName", "UTF-8") + "=" + URLEncoder.encode(imageName, "UTF-8") + "&" +
                            URLEncoder.encode("picture", "UTF-8") + "=" + URLEncoder.encode(picture, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();

                    InputStream is = httpURLConnection.getInputStream();
                    is.close();
                    httpURLConnection.disconnect();
                    return "Information upload was success...";

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            return null;
        }


    }





}
