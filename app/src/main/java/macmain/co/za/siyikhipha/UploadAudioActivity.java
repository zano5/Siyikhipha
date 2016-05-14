package macmain.co.za.siyikhipha;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioTrack;
import android.media.MediaPlayer;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class UploadAudioActivity extends AppCompatActivity {

    private EditText etAudioArtistName;
    private EditText etAudioFileSongName;
    private EditText etAudioFileImageName;
    private ImageView ivAudioFileImage;
    private TextView tvAudioFilePath;
    private Button btnUploadAudioFile;
    private String audio,image;
    private String audioName;
    private Uri audioTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_audio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        etAudioArtistName = (EditText) findViewById(R.id.etAudioArtistName);
        etAudioFileImageName= (EditText) findViewById(R.id.etAudioFileImageName);
        etAudioFileSongName = (EditText) findViewById(R.id.etAudioFileSongName);
        ivAudioFileImage = (ImageView) findViewById(R.id.ivAudioFileImage);
        tvAudioFilePath = (TextView) findViewById(R.id.tvAudioFilePath);
        btnUploadAudioFile = (Button) findViewById(R.id.btnUploadAudioFile);

      //  String uploadImage = "http://10.0.2.2/webmacmain/images/artistaudio/";


        etAudioArtistName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnUploadAudioFile.setEnabled(!etAudioArtistName.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivAudioFileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image For Upload"), 2);
            }
        });


        tvAudioFilePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Song For Upload"), 3);

            }
        });

        btnUploadAudioFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etAudioFileSongName.getText().toString().equals("")){

                    Toast.makeText(UploadAudioActivity.this,"Song name field must be field",Toast.LENGTH_LONG).show();
                }else if(etAudioFileImageName.getText().toString().equals("")){

                    Toast.makeText(UploadAudioActivity.this,"Audio name field must be field",Toast.LENGTH_LONG).show();
                }else{

                String joinName = etAudioArtistName.getText().toString().trim() + etAudioFileImageName.getText().toString().trim();

                    Bitmap bitmap = ((BitmapDrawable) ivAudioFileImage.getDrawable()).getBitmap();

                   // new UploadImageTask(bitmap,joinName).execute();


                    audioName = etAudioArtistName.getText().toString().trim() + etAudioFileSongName.getText().toString().toString();



                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {

                ivAudioFileImage.setImageURI(data.getData());


                Uri selectedImage = data.getData();

                image = getPathImage(selectedImage);

                tvAudioFilePath.setText(String.valueOf(image));


            }else if(requestCode ==3){



                Uri selectedAudio = data.getData();
                audioTest = data.getData();
                audio = getPathAudio(selectedAudio);

                tvAudioFilePath.setText(String.valueOf(data.getData()));
            }

        }
    }

    public String getPathImage(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getPathAudio(Uri uri) {
        String[] projection = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String uploadAudio(final String filePath) {
        String extension = filePath.substring(filePath.lastIndexOf("."));

        String response = null;
        String final_upload_filename = "sample_audio"+"mp3";
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "---------------------------14737809831466499882746641449";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            URL url = new URL( "http://10.0.2.2/webmacmain/upload_audio.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setChunkedStreamingMode(1024);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", final_upload_filename);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"userfile\"; filename=\"" + final_upload_filename + "\"" + lineEnd);
            dos.writeBytes("Content-Type: application/octet-stream" + lineEnd);
            dos.writeBytes(lineEnd);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fileInputStream.close();
            dos.flush();
            dos.close();
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bytessRead;
            byte[] bytes = new byte[1024];
            while ((bytessRead = is.read(bytes)) != -1) {
                baos.write(bytes, 0, bytessRead);
            }
            byte[] bytesReceived = baos.toByteArray();
            baos.close();
            is.close();
            response = new String(bytesReceived);

        } catch (MalformedURLException ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }
        return response;
    }



    private class UploadImageTask extends AsyncTask<Void, Void, Void> {

        private Bitmap image;
        private  String name;

        public UploadImageTask(Bitmap image, String name){
            this.image= image;
            this.name = name;
        }





        @Override
        protected Void doInBackground(Void... params) {

            String upLoadServerUri = "http://10.0.2.2/webmacmain/audio_image_upload.php";
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

            Toast.makeText(UploadAudioActivity.this,"Upload successful", Toast.LENGTH_LONG).show();
        }
    }


    private class UploadAudioInfo extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... params) {
            String upload_info_url = "http://10.0.2.2/webmacmain/audio_info_upload.php";

            String method = params[0];
            //name,surname,username,email,sex,province,password
            if (method.equals("uploadInfo")) {

                String artistName = params[1];
                String songName= params[2];
                String audioName = params[3];
                String image = params[4];



                try {
                    URL url = new URL(upload_info_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("artistName", "UTF-8") + "=" + URLEncoder.encode(artistName, "UTF-8") + "&" +
                            URLEncoder.encode("songName", "UTF-8") + "=" + URLEncoder.encode(songName, "UTF-8") + "&" +
                            URLEncoder.encode("audioName", "UTF-8") + "=" + URLEncoder.encode(audioName, "UTF-8") + "&"+
                            URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8") ;
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(UploadAudioActivity.this,s, Toast.LENGTH_SHORT).show();
        }
    }


    private class UploadAudioTask extends AsyncTask<Void, Void, Void> {

        private Bitmap image;
        private  String name;

        public UploadAudioTask(Bitmap image, String name){
            this.image= image;
            this.name = name;
        }




        @Override
        protected Void doInBackground(Void... params) {

            String upLoadServerUri = "http://10.0.2.2/webmacmain/audio_image_upload.php";
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

            Toast.makeText(UploadAudioActivity.this,"Upload successful", Toast.LENGTH_LONG).show();
        }
    }






}
