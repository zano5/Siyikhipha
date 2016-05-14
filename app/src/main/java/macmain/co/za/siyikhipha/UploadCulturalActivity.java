package macmain.co.za.siyikhipha;

import android.app.AlertDialog;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class UploadCulturalActivity extends AppCompatActivity {

    private EditText etUploadCulturalItemName, etUploadCulturalImageName,etUploadCulturalItemCity, etUploadCulturalItemArea, etUploadCulturalItemItemContact,
    etUploadCulturalItemTown, etUploadCulturalItemPrice;

    private ImageView ivCulturalItemImage;
    private Button btnUploadCulturalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_cultural);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        etUploadCulturalItemName = (EditText) findViewById(R.id.etUploadItemName);
        etUploadCulturalItemArea = (EditText) findViewById(R.id.etUploadItemArea);
        etUploadCulturalItemCity = (EditText) findViewById(R.id.etUploadItemCity);
        etUploadCulturalItemPrice = (EditText) findViewById(R.id.etUploadItemPrice);
        etUploadCulturalItemTown = (EditText) findViewById(R.id.etUploadItemTown);
        ivCulturalItemImage = (ImageView) findViewById(R.id.ivUploadItemImage);
        btnUploadCulturalItem = (Button) findViewById(R.id.btnUploadCulturalItem);
        etUploadCulturalItemItemContact = (EditText) findViewById(R.id.etUploadItemContact);

        etUploadCulturalItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                btnUploadCulturalItem.setEnabled(!etUploadCulturalItemName.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivCulturalItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image For Upload"), 4);

            }
        });

        btnUploadCulturalItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etUploadCulturalItemCity.getText().toString().isEmpty()){

                    Toast.makeText(UploadCulturalActivity.this,"The City field must be field", Toast.LENGTH_LONG).show();
                }else if(etUploadCulturalItemPrice.getText().toString().isEmpty()){


                    Toast.makeText(UploadCulturalActivity.this,"The Price field must be field", Toast.LENGTH_LONG).show();
                }else if(etUploadCulturalItemArea.getText().toString().isEmpty()){

                    Toast.makeText(UploadCulturalActivity.this,"The Area field must be field", Toast.LENGTH_LONG).show();
                }else if(etUploadCulturalItemItemContact.getText().toString().isEmpty()){

                    Toast.makeText(UploadCulturalActivity.this,"The Contact field must be field", Toast.LENGTH_LONG).show();
                }else if(etUploadCulturalItemTown.getText().toString().isEmpty()){

                    Toast.makeText(UploadCulturalActivity.this,"The Town field must be field", Toast.LENGTH_LONG).show();
                }else  {


                    String amount =etUploadCulturalItemPrice.getText().toString();

                    String name = etUploadCulturalItemName.getText().toString();
                    String city = etUploadCulturalItemCity.getText().toString();
                    String town = etUploadCulturalItemTown.getText().toString();
                    String area = etUploadCulturalItemArea.getText().toString();
                    String contact = etUploadCulturalItemItemContact.getText().toString();
                    String picture = name + city + ".jpg";


                    UploadCulturalTask upload = new UploadCulturalTask(UploadCulturalActivity.this);

                    upload.execute(name,city,town,area,contact,amount,picture);
                    Bitmap bitmap = ((BitmapDrawable) ivCulturalItemImage.getDrawable()).getBitmap();


                    new UploadCulturalImageTask(bitmap,picture).execute();

                    etUploadCulturalItemName.setText("");
                    etUploadCulturalItemItemContact.setText("");
                    etUploadCulturalItemPrice.setText("");
                    etUploadCulturalItemCity.setText("");
                    etUploadCulturalItemTown.setText("");
                    etUploadCulturalItemArea.setText("");

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 4) {

                ivCulturalItemImage.setImageURI(data.getData());


                Uri selectedImage = data.getData();



            }

        }
    }



    public class UploadCulturalTask extends AsyncTask<String,Void,String> {

        private Context context;

        UploadCulturalTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "http://10.0.2.2/webmacmain/upload_cultural_item.php";


            //name,surname,username,email,sex,province,password


                String name = params[0];
                String city = params[1];
                String town= params[2];
                String area = params[3];
                String contact = params[4];
                String price = params[5];
                String picture = params[6];


                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8") + "&" +
                            URLEncoder.encode("town", "UTF-8") + "=" + URLEncoder.encode(town, "UTF-8") + "&" +
                            URLEncoder.encode("area", "UTF-8") + "=" + URLEncoder.encode(area, "UTF-8") + "&" +
                            URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&"
                            + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8") + "&"
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

    private class UploadCulturalImageTask extends AsyncTask<Void, Void, Void> {

        private Bitmap image;
        private  String name;

        public UploadCulturalImageTask(Bitmap image, String name){
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

            Toast.makeText(UploadCulturalActivity.this,"Upload successful", Toast.LENGTH_LONG).show();
        }
    }

}
