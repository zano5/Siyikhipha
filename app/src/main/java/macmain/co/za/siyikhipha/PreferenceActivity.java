package macmain.co.za.siyikhipha;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PreferenceActivity extends AppCompatActivity {

    private CardView cvInformation,cvFeedBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cvInformation = (CardView) findViewById(R.id.cvInformation);

        cvFeedBack = (CardView) findViewById(R.id.cvFeedBack);




        cvInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent  intent = new Intent(PreferenceActivity.this, ImageSelectionActivity.class);

                    startActivityForResult(intent, 404);
            }
        });








    }

}
