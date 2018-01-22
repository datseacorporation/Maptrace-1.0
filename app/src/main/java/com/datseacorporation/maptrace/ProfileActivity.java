package com.datseacorporation.maptrace;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView usernametv;
    private Button logoutbtn;
    private Button showheatmapbtn;
    private Button instancesavebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(this, UserLogin.class));

        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        usernametv = (TextView) findViewById(R.id.usernametv);
        // calling user details ...

        usernametv.setText("Welcome, "+user.getEmail());

      //  logoutbtn = (Button) findViewById(R.id.logoutbtn);
//        showheatmapbtn = (Button) findViewById(R.id.showheatmapbtn);

       // logoutbtn.setOnClickListener(this);
       // instancesavebtn.setOnClickListener(this);
      //  showheatmapbtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v == logoutbtn){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, UserLogin.class ));
        }

        if(v == instancesavebtn){
            startActivity(new Intent(this,SuspectActivity.class ));

        }

        if(v == showheatmapbtn){
            startActivity(new Intent(this,WebApp.class ));

        }

    }
}
