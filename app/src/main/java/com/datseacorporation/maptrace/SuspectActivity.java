package com.datseacorporation.maptrace;

import android.content.Intent;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import android.support.v4.app.ActivityCompat;

//

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
//







import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



@RequiresApi(api = Build.VERSION_CODES.N)
public class SuspectActivity extends AppCompatActivity implements View.OnClickListener {

// user defined instances... here

    private DatabaseReference infodb;

    private Button save_location;
    private Button heatmapbtn,logbtn;
    private EditText drugnametf;
    private EditText latitudetf;
    private EditText longitudetf;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude,longitude;
    private AlertDialog.Builder builder;
    String server_url = "http://anupritdangi.com/heat_map/heat_data.php";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspect);
        builder = new AlertDialog.Builder(SuspectActivity.this);
      //  firebaseAuth = FirebaseAuth.getInstance();
       // FirebaseUser user = firebaseAuth.getCurrentUser();
       // userID = user.getUid();




        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        save_location = (Button) findViewById(R.id.save_location);
        logbtn = (Button) findViewById(R.id.logbtn);
        infodb = FirebaseDatabase.getInstance().getReference("SuspectDetails");
        drugnametf = (EditText) findViewById(R.id.drugnametf);
        latitudetf = (EditText) findViewById(R.id.latitudetf);
        longitudetf = (EditText) findViewById(R.id.longitudetf);
        heatmapbtn = (Button) findViewById(R.id.heatmapbtn);
        //getloc = (Button) findViewById(R.id.getloc);

        save_location.setOnClickListener(this);
      //  getloc.setOnClickListener(this);
        heatmapbtn.setOnClickListener(this);
        logbtn.setOnClickListener(this);



    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(SuspectActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (SuspectActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SuspectActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                latitudetf.setText(latitude);
                longitudetf.setText(longitude);


            }else{
                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();
            }
        }
    }




    protected void buildAlertMessageNoGps() {

       /* final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        dialog.dismiss();



                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();*/


       /* Toast.makeText(this,"Turn your location to High Acuracy !",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
*/

    }

    private void senddata(){
        RequestQueue requestQueue = Volley.newRequestQueue(SuspectActivity.this);
        final double flat,flong;
        flat =   Double.parseDouble(latitudetf.getText().toString().trim());
        flong  = Double.parseDouble(latitudetf.getText().toString().trim());
        final String drug = drugnametf.getText().toString().trim();
        final String fflat = String.valueOf(flat);
        final String fflong = String.valueOf(flong);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        builder.setTitle("Server Response");
                        builder.setMessage(response);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                latitudetf.setText(fflat);
                                longitudetf.setText(fflong);

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(SuspectActivity.this,"Error...",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }
        ){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                params.put("latitude", fflat);
                params.put("longitude", fflong);
               params.put("drug",drug);
               // System.out.print(params);
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

private void saveUserInformation(){

String save = "SuspectDetails";
    infodb = FirebaseDatabase.getInstance().getReference(save);   // SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
   // String currentDateandTime = sdf.format(new Date());
    String drugname = drugnametf.getText().toString().trim();
    double finallatitude = Double.parseDouble(latitudetf.getText().toString().trim());
    double finallongitude = Double.parseDouble(longitudetf.getText().toString().trim());
    //String logimage = "https://cdn.pixabay.com/photo/2014/11/10/11/43/map-525349_960_720.png";
    //String id = infodb.push().getKey();
     ///////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////


     senddata();
    UserInformation userInformation = new UserInformation (drugname,finallatitude,finallongitude);

    infodb.push().setValue(userInformation);
    Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
    drugnametf.setText("");
    latitudetf.setText("");
    longitudetf.setText("");
    latitudetf.setText(latitude);
    longitudetf.setText(longitude);
    //System.out.print(currentDateandTime);


}





    @Override
    public void onClick(View view) {




            if (view == heatmapbtn) {
                startActivity(new Intent(this, DrugLocationActivity.class));
            }
            if (view == save_location) {
                saveUserInformation();

            }
        if (view == logbtn) {
            startActivity(new Intent(this, Test.class));

        }


    }


    @Override
    protected void onStart()
    {



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
        super.onStart();
    }


    @Override
    protected void onResume()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
        super.onResume();
    }

}

 /*

 if(finallatitude & finallongitude ==0.000){
         buildAlertMessageNoGps();
         }
         else{
         dialog.dismiss();
         }

         */


