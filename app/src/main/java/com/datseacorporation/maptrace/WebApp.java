package com.datseacorporation.maptrace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class WebApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_app);
        //
        final WebView mywebview = (WebView) findViewById(R.id.webpane);
        mywebview.loadUrl("http://anupritdangi.com/heat_map/hmap.php");
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Button button = (Button) findViewById(R.id.refresh);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                mywebview.reload();

            }
        });

        //

    }
}
