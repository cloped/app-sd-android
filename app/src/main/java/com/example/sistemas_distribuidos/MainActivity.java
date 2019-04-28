package com.example.sistemas_distribuidos;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;

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


            try {
                JSONArray arr = new JSONArray(strFileContents);
                writeOnScreen(null, arr);
            } catch (Throwable t) {
                writeOnScreen("Erro no JSON Array", null);
            }
        } catch (Exception e) {
            writeOnScreen("Erro no GET", null);
        } finally {
            urlConnection.disconnect();
        }
    }

    public void eraseText(View view) {
        Log.d("Erase Text");
//        final TextView mTextView = findViewById(R.id.api_response);
//        mTextView.setText("Empty");
    }

    public void writeOnScreen(String error, JSONArray arr) {
        Log.d("Write on Screen");
    }
}
