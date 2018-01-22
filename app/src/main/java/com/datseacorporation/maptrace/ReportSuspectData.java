package com.datseacorporation.maptrace;

import android.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportSuspectData extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference infodb;
    private Button submitdatabtn;
    private EditText drugnametextedit;
    private EditText drugquantitytextedit;
    private EditText suspectnametextedit;
    private TextView tvLatitude;
    private TextView tvLongitude;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude,longitude;
    private AlertDialog.Builder builder;
    String server_url = "http://anupritdangi.com/heat_map/heat_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_suspect_data);
        builder = new AlertDialog.Builder(ReportSuspectData.this);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        submitdatabtn = (Button) findViewById(R.id.submitdatabtn);
        infodb = FirebaseDatabase.getInstance().getReference("SuspectDetails");
        drugnametextedit = (EditText) findViewById(R.id.drugnametextedit);
        drugquantitytextedit = (EditText) findViewById(R.id.drugquantitytextedit);
        suspectnametextedit = (EditText) findViewById(R.id.suspectnametextedit);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvLatitude.setText("0.00000");
        tvLongitude.setText("0.00000");
        submitdatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==submitdatabtn){
                    SaveUserData();
                    drugnametextedit.getText().clear();
                    drugquantitytextedit.getText().clear();
                    suspectnametextedit.getText().clear();
                }
            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(ReportSuspectData.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (ReportSuspectData.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ReportSuspectData.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                //latitudetf.setText(latitude);
                //longitudetf.setText(longitude);
                tvLatitude.setText(latitude);
                tvLongitude.setText(longitude);

            }else{
                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void senddata(){
        RequestQueue requestQueue = Volley.newRequestQueue(ReportSuspectData.this);
        final double flat,flong;
        flat =   Double.parseDouble(tvLatitude.getText().toString().trim());
        flong  = Double.parseDouble(tvLongitude.getText().toString().trim());
        final String drug = drugnametextedit.getText().toString().trim();
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
                                tvLatitude.setText(fflat);
                                tvLongitude.setText(fflong);

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(ReportSuspectData.this,"Error...",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }
        ){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
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

    private void SaveUserData(){
        String drugName = drugnametextedit.getText().toString().trim();
        String drugQuantity = drugquantitytextedit.getText().toString().trim();
        String suspectName = suspectnametextedit.getText().toString().trim();
        Double uLatitude =Double.parseDouble(tvLatitude.getText().toString().trim());
        Double uLongitude =Double.parseDouble(tvLongitude.getText().toString().trim());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        UserDataUpload  userDataUpload  =new UserDataUpload(drugName,drugQuantity,suspectName,uLatitude,uLongitude,currentDateandTime);
        infodb.push().setValue(userDataUpload);
        senddata();
        Toast.makeText(this, "Data Uploaded",Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View view) {

    }
}
