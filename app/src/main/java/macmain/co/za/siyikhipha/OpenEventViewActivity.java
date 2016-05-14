package macmain.co.za.siyikhipha;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OpenEventViewActivity extends AppCompatActivity {

    private ImageView ivOpenEventView;
    private TextView tvOpenEventViewArea, tvOpenEventViewGeneral,tvOpenEventViewVip,tvOpenEventViewDoor,tvOpenEventViewStart, tvOpenEventViewEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_event_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       /* String start = openEventList.get(position).getStart();
        String end = openEventList.get(position).getEnd();
        String area = openEventList.get(position).getArea();
        double general = openEventList.get(position).getPrice();
        double vip = openEventList.get(position).getVip();
        double door = openEventList.get(position).getDoor()*/

        ivOpenEventView = (ImageView) findViewById(R.id.ivOpenEventView);
        tvOpenEventViewArea =(TextView) findViewById(R.id.tvOpenEventViewArea);
        tvOpenEventViewDoor = (TextView) findViewById(R.id.tvOpenEventViewDoor);
        tvOpenEventViewStart = (TextView) findViewById(R.id.tvOpenEventViewStart);
        tvOpenEventViewEnd = (TextView) findViewById(R.id.tvOpenEventViewEnd);
        tvOpenEventViewGeneral = (TextView) findViewById(R.id.tvOpenEventViewGeneral);
        tvOpenEventViewVip = (TextView) findViewById(R.id.tvOpenEventViewVip);


        Intent intent = getIntent();

     // Event event = (Event) intent.getSerializableExtra(AppConstants.EVENT);

        //String area = intent.getStringExtra(AppConstants.AREA);

       // String num = intent.getStringExtra(AppConstants.ID);

        //int id = Integer.parseInt(num);

        //Drawable d = new Drawable(Resources.getSystem(),event.getBitImage());


        String num = intent.getStringExtra(AppConstants.ID);

        int id = Integer.parseInt(num);

        for(int x=0; x < RVOpenEventAdapter.openEventList.size(); x++) {


            if (id == RVOpenEventAdapter.openEventList.get(x).getId()) {

                tvOpenEventViewArea.setText("Area: "+RVOpenEventAdapter.openEventList.get(x).getArea());
                Drawable d = new BitmapDrawable(Resources.getSystem(),RVOpenEventAdapter.openEventList.get(x).getBitImage());

                ivOpenEventView.setBackground(d);

                tvOpenEventViewEnd.setText("End: " + RVOpenEventAdapter.openEventList.get(x).getEnd() );
                tvOpenEventViewStart.setText("Start: " + RVOpenEventAdapter.openEventList.get(x).getStart());
                tvOpenEventViewVip.setText("Vip: " + RVOpenEventAdapter.openEventList.get(x).getVip());
                tvOpenEventViewGeneral.setText("General: " + RVOpenEventAdapter.openEventList.get(x).getPrice());
            }
        }



     // tvOpenEventViewArea.setText(RVOpenEventAdapter.openEventList.get(0).getTitle());
        /*tvOpenEventViewDoor.setText("Door: " + event.getDoor());
        tvOpenEventViewEnd.setText("End: " + event.getEnd() );
        tvOpenEventViewStart.setText("Start: " + event.getStart());
        tvOpenEventViewVip.setText("Vip: " + event.getVip());
        tvOpenEventViewGeneral.setText("General: " + event.getPrice());*/















    }

}
