package com.example.sistemas_distribuidos;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    List<String> listItems;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list_view);
        listItems = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(arrayAdapter);
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
                String texto;
                listItems.clear();
                for (int i = 0; i < arr.length(); i++) {
                    try {
                        texto = "";
                        texto += "Nome - sigla - relevância:\n\t\t\t" +  arr.getJSONObject(i).getString("name") + " - " + arr.getJSONObject(i).getJSONArray("altSpellings").get(0) + " - " + arr.getJSONObject(i).getDouble("relevance") + "\n";
                        texto += "Capital - região:\n\t\t\t" + arr.getJSONObject(i).getString("capital") + " - " + arr.getJSONObject(i).getString("region") + "\n";
                        texto += "População - área:\n\t\t\t" + arr.getJSONObject(i).getDouble("population") + " - " + arr.getJSONObject(i).getDouble("area") + "\n";
                        texto += "Moedas:\n\t\t\t" + arr.getJSONObject(i).getJSONArray("currencies") + "\n";
                        texto += "Línguas:\n\t\t\t" + arr.getJSONObject(i).getJSONArray("languages");

                        listItems.add(texto);
                        Log.d("Textooooooo", texto);
                    } catch (Exception e) {
                        Log.e("****ERRO d++", e.toString());
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            } catch (Throwable t) {
//                writeOnScreen("Erro no JSON Array", null);
            }
        } catch (Exception e) {
//            writeOnScreen("Erro no GET", null);
        } finally {
            urlConnection.disconnect();
        }
    }

    public void eraseText(View view) {
        Log.d("Erase Text", "erro");
        listItems.clear();
        arrayAdapter.notifyDataSetChanged();
//        final TextView mTextView = findViewById(R.id.api_response);
//        mTextView.setText("Empty");
    }

}
