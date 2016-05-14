package macmain.co.za.siyikhipha;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ImageSelectionActivity extends AppCompatActivity  {

    private EditText etSettingUsername;
    private Button btnSettingAccept;
    private Button btnSettingCancel;
    public static Drawable image;
    public static String username;

    private ImageView ivSettingImage;
    public static Uri picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);


        etSettingUsername = (EditText) findViewById(R.id.etSettingUsername);
        btnSettingAccept = (Button) findViewById(R.id.btnSettingAccept);
        btnSettingCancel = (Button) findViewById(R.id.btnSettingCancel);


        ivSettingImage = (ImageView) findViewById(R.id.ivSettingImage);

        ivSettingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,"Select Contact Image"),8);

            }
        });


        btnSettingAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 image = ivSettingImage.getDrawable();



                if(!etSettingUsername.getText().toString().equals("")) {
                    username = etSettingUsername.getText().toString();
                }

                finish();


            }
        });

        btnSettingCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(resultCode == RESULT_OK){
            if(requestCode == 8){

                ivSettingImage.setImageURI(data.getData());

                picture = data.getData();

            }
        }
    }
}
