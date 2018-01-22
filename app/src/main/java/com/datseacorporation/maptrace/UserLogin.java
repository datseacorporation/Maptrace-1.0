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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity implements View.OnClickListener {

    private Button submitbtn;
    private EditText emailtf;
    private EditText passwordtf;
    private TextView exsignup;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        firebaseAuth = FirebaseAuth.getInstance();
                if(firebaseAuth.getCurrentUser() !=null){
                    // instance profile activity here

                    finish();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }

        exsignup = (TextView) findViewById(R.id.exsignup);
        submitbtn = (Button) findViewById(R.id.submitbtn);
        passwordtf = (EditText) findViewById(R.id.passwordtf);
        emailtf = (EditText) findViewById(R.id.emailtf);

        progressDialog = new ProgressDialog(this);

        submitbtn.setOnClickListener(this);
        exsignup.setOnClickListener(this);
    }

    private void userLogin(){
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
        progressDialog.setMessage("Sign In the User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            // starting profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else {
                            Toast.makeText(UserLogin.this, "Could Not Login.. Please Check Credentials...", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
if (v == submitbtn){
    userLogin();
}
if (v == exsignup){
    startActivity(new Intent(this, LoginActivity.class));
}
    }
}
