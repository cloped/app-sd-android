package com.example.sistemas_distribuidos;

import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    List<String> listItems;
    ArrayAdapter<String> arrayAdapter;
    JSONArray arr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.list_view);
        listItems = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(arrayAdapter);

        String[] arraySpinner = new String[] {
                "Nome", "Sigla", "Relevância", "Capital", "Região", "População", "Área"
        };
        Spinner spin = findViewById(R.id.spinner2);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinnerAdapter);
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
                arr = new JSONArray(strFileContents);
                String texto;
                listItems.clear();
                for (int i = 0; i < arr.length(); i++) {
                    try {
                        texto = "";
                        if (((CheckBox)findViewById(R.id.opt_1)).isChecked()) {
                            texto += "Nome - sigla - relevância:\n\t\t\t" +  arr.getJSONObject(i).getString("name") + " - " + arr.getJSONObject(i).getJSONArray("altSpellings").get(0) + " - " + arr.getJSONObject(i).getDouble("relevance") + "\n";
                        }
                        if (((CheckBox)findViewById(R.id.opt_2)).isChecked()) {
                            texto += "Capital - região:\n\t\t\t" + arr.getJSONObject(i).getString("capital") + " - " + arr.getJSONObject(i).getString("region") + "\n";
                        } if (((CheckBox)findViewById(R.id.opt_3)).isChecked()) {
                            texto += "População - área:\n\t\t\t" + arr.getJSONObject(i).getDouble("population") + " - " + arr.getJSONObject(i).getDouble("area") + "\n";
                        } if (((CheckBox)findViewById(R.id.opt_4)).isChecked()) {
                            texto += "Moedas:\n\t\t\t" + arr.getJSONObject(i).getJSONArray("currencies") + "\n";
                        } if (((CheckBox)findViewById(R.id.opt_5)).isChecked()) {
                            texto += "Línguas:\n\t\t\t" + arr.getJSONObject(i).getJSONArray("languages");
                        }
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void crescente(View view) throws JSONException {
        Spinner mySpinner = findViewById(R.id.spinner2);
        String text = mySpinner.getSelectedItem().toString();
        final String aux;
        if (text == "Nome") {
            aux = "name";
        }

        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            jsonValues.add(arr.getJSONObject(i));
        }
        Collections.sort( jsonValues, new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "Name";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get(KEY_NAME);
                    valB = (String) b.get(KEY_NAME);
                }
                catch (JSONException e) {
                    //do something
                }

                return valA.compareTo(valB);
                //if you want to change the sort order, simply use the following:
                //return -valA.compareTo(valB);
            }
        });

        for (int i = 0; i < arr.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }

        arr = new JSONArray(sortedJsonArray);

        String texto;
        listItems.clear();
        for (int i = 0; i < arr.length(); i++) {
            try {
                texto = "";
                if (((CheckBox)findViewById(R.id.opt_1)).isChecked()) {
                    texto += "Nome - sigla - relevância:\n\t\t\t" +  arr.getJSONObject(i).getString("name") + " - " + arr.getJSONObject(i).getJSONArray("altSpellings").get(0) + " - " + arr.getJSONObject(i).getDouble("relevance") + "\n";
                }
                if (((CheckBox)findViewById(R.id.opt_2)).isChecked()) {
                    texto += "Capital - região:\n\t\t\t" + arr.getJSONObject(i).getString("capital") + " - " + arr.getJSONObject(i).getString("region") + "\n";
                } if (((CheckBox)findViewById(R.id.opt_3)).isChecked()) {
                    texto += "População - área:\n\t\t\t" + arr.getJSONObject(i).getDouble("population") + " - " + arr.getJSONObject(i).getDouble("area") + "\n";
                } if (((CheckBox)findViewById(R.id.opt_4)).isChecked()) {
                    texto += "Moedas:\n\t\t\t" + arr.getJSONObject(i).getJSONArray("currencies") + "\n";
                } if (((CheckBox)findViewById(R.id.opt_5)).isChecked()) {
                    texto += "Línguas:\n\t\t\t" + arr.getJSONObject(i).getJSONArray("languages");
                }
                listItems.add(texto);
                Log.d("Textooooooo", texto);
            } catch (Exception e) {
                Log.e("****ERRO d++", e.toString());
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
