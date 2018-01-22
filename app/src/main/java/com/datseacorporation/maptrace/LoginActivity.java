package com.datseacorporation.maptrace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitbtn;
    private EditText emailtf;
    private EditText passwordtf;
    private TextView exlogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog =  new ProgressDialog(this);

        submitbtn = (Button) findViewById(R.id.submitbtn);
        emailtf = (EditText) findViewById(R.id.emailtf);
        passwordtf = (EditText) findViewById(R.id.passwordtf);
        exlogin = (TextView) findViewById(R.id.exlogin);

        submitbtn.setOnClickListener(this);
        exlogin.setOnClickListener(this);
    }

    private void registerUser(){
        String email = emailtf.getText().toString().trim();
        String password = passwordtf.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

            //email is empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            //stopping excution
            return;
        }

        if (TextUtils.isEmpty(password)){

            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            //stopping excution
            return;
        }
        // if validate ok
        //show progress bar
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), ProfileDetailsActivity.class));

                        }
                        else {

                            Toast.makeText(LoginActivity.this, "Could Not Register.. Please Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });


    }




    @Override
    public void onClick(View v) {
        if(v == submitbtn){
            registerUser();
        }

        if(v == exlogin){
            //this will open login activity
            startActivity(new Intent(this, UserLogin.class));
        }

    }
}
