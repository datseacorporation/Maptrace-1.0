package com.datseacorporation.maptrace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class Log extends AppCompatActivity implements View.OnClickListener {


    private Button viewLogbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        final WebView newWebapp = (WebView) findViewById(R.id.newWebapp);
        newWebapp.loadUrl("http://anupritdangi.com/heat_map/hmap.php");
        WebSettings webSettings = newWebapp.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Button refreshmapbtn = (Button) findViewById(R.id.refreshmapbtn);
        viewLogbtn = (Button) findViewById(R.id.viewLogbtn);

        refreshmapbtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                newWebapp.reload();

            }
        });

        viewLogbtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == viewLogbtn) {
            startActivity(new Intent(this, DataLogsActivity.class));
        }
    }

}