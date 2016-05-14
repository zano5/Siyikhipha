package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class UploadClotheringActivity extends AppCompatActivity {

    private ImageView ivUploadClothering;
    private Button btnUploadClothering;
    private EditText etUploadClotheringLine, etUploadClotheringName, etUploadClotheringContact, etUploadClotheringPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_clothering);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ivUploadClothering = (ImageView) findViewById(R.id.ivUploadClothering);
        btnUploadClothering= (Button) findViewById(R.id.btnUploadClothering);
        etUploadClotheringContact = (EditText) findViewById(R.id.etUploadClotheringContact);
        etUploadClotheringLine = (EditText) findViewById(R.id.etUploadClotheringLine);
        etUploadClotheringName = (EditText) findViewById(R.id.etUploadClotheringName);
        etUploadClotheringPrice = (EditText) findViewById(R.id.etUploadClotheringPrice);


        etUploadClotheringName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnUploadClothering.setEnabled(!etUploadClotheringName.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivUploadClothering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image For Upload"), 5);
            }
        });

        btnUploadClothering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etUploadClotheringLine.getText().toString().isEmpty()){

                    Toast.makeText(UploadClotheringActivity.this,"The Clothering Line field must be entered", Toast.LENGTH_LONG).show();
                }else if(etUploadClotheringPrice.getText().toString().isEmpty()){
                    Toast.makeText(UploadClotheringActivity.this,"The Clothering Price field must be entered", Toast.LENGTH_LONG).show();
                }else if(etUploadClotheringContact.getText().toString().isEmpty()){
                    Toast.makeText(UploadClotheringActivity.this,"The Clothering Contact field must be entered", Toast.LENGTH_LONG).show();

                }else{

                    String line = etUploadClotheringLine.getText().toString();
                    String name = etUploadClotheringName.getText().toString();
                    String price = etUploadClotheringPrice.getText().toString();
                    String contact = etUploadClotheringContact.getText().toString();
                    String picture = name.trim()+ line.trim()  + ".jpg";
                    String image = name.trim()+ line.trim();
                    UploadClotheringTask upload = new UploadClotheringTask(UploadClotheringActivity.this);

                    upload.execute(name,line,contact,price,picture);

                    Bitmap bitmap = ((BitmapDrawable) ivUploadClothering.getDrawable()).getBitmap();



                    new UploadClotheringImageTask(bitmap,image).execute();



                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 5) {

                ivUploadClothering.setImageURI(data.getData());


                Uri selectedImage = data.getData();



            }

        }
    }

    public class UploadClotheringTask extends AsyncTask<String,Void,String> {

        private Context context;

        UploadClotheringTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "http://10.0.2.2/webmacmain/upload_clothering_item.php";


            //name,surname,username,email,sex,province,password


            String name = params[0];
            String line= params[1];
            String contact= params[2];
            String price = params[3];
            String picture = params[4];



            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("line", "UTF-8") + "=" + URLEncoder.encode(line, "UTF-8") + "&" +
                        URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&" +
                        URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8")+ "&" +
                        URLEncoder.encode("picture", "UTF-8") + "=" + URLEncoder.encode(picture, "UTF-8");

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

            Toast.makeText(UploadClotheringActivity.this,result,Toast.LENGTH_LONG).show();


        }
    }

    private class UploadClotheringImageTask extends AsyncTask<Void, Void, Void> {

        private Bitmap image;
        private  String name;

        public UploadClotheringImageTask(Bitmap image, String name){
            this.image= image;
            this.name = name;
        }




        @Override
        protected Void doInBackground(Void... params) {

            String upLoadServerUri = "http://10.0.2.2/webmacmain/upload_clothering_image.php";
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

           // Toast.makeText(UploadClotheringActivity.this,"Upload successful", Toast.LENGTH_LONG).show();
        }
    }

}
