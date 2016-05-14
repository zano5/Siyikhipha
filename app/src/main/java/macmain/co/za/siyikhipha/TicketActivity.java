package macmain.co.za.siyikhipha;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TicketActivity extends AppCompatActivity {

    private TextView tvTicketCount;
    private Spinner spinnerTicket;
    private List<Ticket> ticketList;
    private String[] area;
    private String[] ticketsAvailable;
    private ArrayAdapter<String> aAdapter;
    private static final String TAG = "TicketActivity";
    private ImageView ivTicket;
    private List<EventSchedule> eventScheduleList;
    private int value;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
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


        ivTicket = (ImageView) findViewById(R.id.ivTicket);
        tvTicketCount = (TextView) findViewById(R.id.tvTicketCount);
        spinnerTicket = (Spinner) findViewById(R.id.spinnerTicket);

        Intent intent = getIntent();

        Ticket ticket = (Ticket) intent.getSerializableExtra(AppConstants.TICKET);



        String tickNumber = ticket.getNumberOfTickets();

        for(int x= 0; x< RVEventAdapter.events.size(); x++){

            if(ticket.getFkId() == RVEventAdapter.events.get(x).getId()){
                //Drawable d = new BitmapDrawable(getResources(), RVEventAdapter.events.get(x).getBitImage());
                ivTicket.setImageBitmap(RVEventAdapter.events.get(x).getBitImage());

                value = ticket.getFkId();
            }
        }



       // Drawable d = new BitmapDrawable(getResources() );
        //ivTicket.setImageBitmap(RVEventAdapter.image);
        //ivTicket.setB
        //ivTicket.setBackground(d);
        String location = ticket.getArea();



        ticketsAvailable = tickNumber.split("#");

        area= location.split("#");








        aAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,area);

        spinnerTicket.setAdapter(aAdapter);

        spinnerTicket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            spinnerTicket.toString();

                for(int x=0; x< area.length; x++){

                    if(spinnerTicket.getSelectedItem().toString().equals(area[x])){

                        tvTicketCount.setText(ticketsAvailable[x]);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });










    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_schedule) {

            Intent intent = new Intent(TicketActivity.this,LineUpActivity.class);
            intent.putExtra(AppConstants.ID,String.valueOf(value));
            startActivityForResult(intent,400);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }








}
