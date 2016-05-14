package macmain.co.za.siyikhipha;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ProJava on 11/25/2015.
 */public class BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog builder;
    Context context;
    String user_name;



    BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        builder = new AlertDialog.Builder(context).create();
        builder.setTitle("Login Information...");



    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://10.0.2.2/webmacmain/registration.php";
        String log_url = "http://10.0.2.2/webmacmain/log.php";
        String method = params[0];
        //name,surname,username,email,sex,province,password
        if (method.equals("register")) {

            String name = params[1];
            String surname = params[2];
            String username = params[3];
            String email = params[4];
            String region = params[5];
            String password = params[6];


            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(surname, "UTF-8") + "&" +
                        URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" + URLEncoder.encode("region", "UTF-8") + "=" + URLEncoder.encode(region, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                is.close();
                httpURLConnection.disconnect();
                return "registration success...";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (method.equals("login")) {

            user_name = params[1];
            String password = params[2];


            try {
                URL url = new URL(log_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                String data = URLEncoder.encode("login_username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("login_password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                String response = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    response += line;

                }

                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return response;


                // httpURLConnection.disconnect();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {


        if (result.equals("registration success...")) {


            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        } else {
            builder.setMessage(result);
            builder.show();
        }

        if (result.equals("Login Success...Welcome")) {

            Intent intent = new Intent(context, MainActivity.class);


            context.startActivity(intent);
        }

    }




}
