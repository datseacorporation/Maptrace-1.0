package com.datseacorporation.maptrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firebaseAuth = FirebaseAuth.getInstance();
                if(firebaseAuth.getCurrentUser() !=null){
                    // instance profile activity here
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }

                else {
                    finish();
                    Intent homeIntent = new Intent(MainActivity.this, UserLogin.class);
                    startActivity(homeIntent);
                }
        }

    }, SPLASH_TIME_OUT);
}

}