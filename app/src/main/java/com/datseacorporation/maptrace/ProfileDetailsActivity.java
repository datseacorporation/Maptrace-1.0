package com.datseacorporation.maptrace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference infodb;
    private EditText usernamedetailtf,usermobiletf,useragetf,useraddresstf;
    private Button submitinformationbtn;
    private FirebaseAuth firebaseAuth;
    private String mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        usernamedetailtf = (EditText) findViewById(R.id.usernamedetailtf);
        usermobiletf = (EditText) findViewById(R.id.usermobiletf);
        useragetf = (EditText) findViewById(R.id.useragetf);
        useraddresstf = (EditText) findViewById(R.id.useraddresstf);
        submitinformationbtn = (Button) findViewById(R.id.submitinformationbtn);
        infodb = FirebaseDatabase.getInstance().getReference("User Details");
        submitinformationbtn.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        mUser = user.getUid();



    }


    @Override
    public void onClick(View v) {
        if(v == submitinformationbtn){

            saveUserProfile();
    }
}

    private void saveUserProfile() {

        String sUsername = usernamedetailtf.getText().toString().trim();
        String sMobile = usermobiletf.getText().toString().trim();
        String sAge = useragetf.getText().toString().trim();
        String sAddress = useraddresstf.getText().toString().trim();

        UserProfileDetails userProfileDetails = new  UserProfileDetails(sUsername,sMobile,sAge,sAddress);

        infodb.child(mUser).setValue(userProfileDetails);
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, HomeActivity.class ));

    }


}
