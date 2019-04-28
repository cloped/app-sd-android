package com.example.sistemas_distribuidos;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getFromApi(View view) throws IOException {
        final TextView mTextView = findViewById(R.id.api_response);

        URL url = new URL("https://restcountries.eu/rest/v1/all");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            byte[] contents = new byte[1024];

            int bytesRead;
            String strFileContents = "";
            while((bytesRead = in.read(contents)) != -1) {
                strFileContents += new String(contents, 0, bytesRead);
            }
            mTextView.setText(strFileContents);
        } catch (Exception e) {
            mTextView.setText("Error: " + e);
        } finally {
            urlConnection.disconnect();
        }

    }



}
