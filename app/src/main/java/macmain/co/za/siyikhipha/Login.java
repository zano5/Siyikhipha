package macmain.co.za.siyikhipha;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private TextView tvRegister;
    private Button btnLogin;
    private EditText etUsername,etPassword;
    private String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etUsername =(EditText) findViewById(R.id.etUsername);
        etPassword =(EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister=(TextView) findViewById(R.id.tvRegister);


        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnLogin.setEnabled(!etUsername.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,Register.class);
                startActivityForResult(intent,100);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if(username !=null && password !=null) {


                    String method = "login";
                    BackgroundTask task = new BackgroundTask(Login.this);

                    task.execute(method, username, password);
                }else{
                    Toast.makeText(Login.this,"Error",Toast.LENGTH_LONG).show();
                }

            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null && resultCode== RESULT_OK && requestCode == 100){

            String name = data.getStringExtra(AppConstants.NAME);

            Toast.makeText(Login.this, name + " has been registerd ", Toast.LENGTH_LONG ).show();
        }
    }

}
