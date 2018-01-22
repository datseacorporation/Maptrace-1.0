package com.datseacorporation.maptrace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    // Instance Declearation

    private FirebaseAuth firebaseAuth;
    private TextView emailidtv;
    private ImageButton saveinstabtn,showlogsbtn,showprofilebtn,homelogoutbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        emailidtv = (TextView) findViewById(R.id.emailidtv);
        saveinstabtn = (ImageButton) findViewById(R.id.saveinstabtn);
        showlogsbtn = (ImageButton) findViewById(R.id.showlogsbtn);
        showprofilebtn = (ImageButton) findViewById(R.id.showprofilebtn);
        homelogoutbtn = (ImageButton) findViewById(R.id.homelogoutbtn);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        // Printing user's email-id
        emailidtv.setText(user.getEmail());


        // Calling onClick Listners

        saveinstabtn.setOnClickListener(this);
        showlogsbtn.setOnClickListener(this);
        showprofilebtn.setOnClickListener(this);
        homelogoutbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == saveinstabtn){
            startActivity(new Intent(this, ReportSuspectData.class ));
        }

        if(v == showlogsbtn){
            startActivity(new Intent(this, Log.class ));
        }

        if(v == showprofilebtn){
            startActivity(new Intent(this, ProfileActivity.class ));
        }

        if(v == homelogoutbtn){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, UserLogin.class ));
        }

    }
}
