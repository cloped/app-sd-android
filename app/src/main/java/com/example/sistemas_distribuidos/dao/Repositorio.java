package com.example.sistemas_distribuidos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sistemas_distribuidos.dao.SQLHelper;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class Repositorio {

    private SQLHelper helper;
    private SQLiteDatabase db;

    public Repositorio(Context ctx){
        helper = new SQLHelper(ctx);
    }

    public long inserir(JSONArray arr){
        db = helper.getReadableDatabase();
        for (int i = 0; i < arr.length(); i++){
            ContentValues cv = new ContentValues();
            try {
                String pais = arr.getJSONObject(i).getString("name");
                Log.d("erroInserir",pais);
                String capital = arr.getJSONObject(i).getString("capital");
                Log.d("erroInserir",capital);
                String spelling = arr.getJSONObject(i).getString("altSpellings");
                Log.d("erroInserir",spelling);
                String regiao = arr.getJSONObject(i).getString("region");
                Log.d("erroInserir",regiao);
                String subregiao = arr.getJSONObject(i).getString("subregion");
                Log.d("erroInserir",subregiao);
                int populacao = arr.getJSONObject(i).getInt("population");
                Log.d("erroInserir", String.valueOf(populacao));
                float area = Float.valueOf(arr.getJSONObject(i).getString("area"));
                Log.d("erroInserir",String.valueOf(area));
                String moedas = arr.getJSONObject(i).getString("currencies");
                Log.d("erroInserir",moedas);
                String linguas = arr.getJSONObject(i).getString("languages");
                Log.d("erroInserir",linguas);
                float relevancia = Float.valueOf(arr.getJSONObject(i).getString("relevance"));
                Log.d("erroInserir",String.valueOf(relevancia));
                System.out.println("OI ME AJUDA");
                cv.put(SQLHelper.COLUNA_ID,i);
                cv.put(SQLHelper.COLUNA_PAIS, pais);
                cv.put(SQLHelper.COLUNA_CAPITAL, capital);
                cv.put(SQLHelper.COLUNA_SPELLING, spelling);
                cv.put(SQLHelper.COLUNA_REGIAO, regiao);
                cv.put(SQLHelper.COLUNA_SUBREGIAO, subregiao);
                cv.put(SQLHelper.COLUNA_POPULACAO, populacao);
                cv.put(SQLHelper.COLUNA_AREA, area);
                cv.put(SQLHelper.COLUNA_MOEDAS, moedas);
                cv.put(SQLHelper.COLUNA_LINGUAS, linguas);
                cv.put(SQLHelper.COLUNA_RELEVANCIA, relevancia);
                System.out.println(pais);
                long id = db.insert(SQLHelper.TABELA_SD, null, cv);
                if(id == -1){
                    Log.e("insercao","Insercao errada" + pais);
                }else{
                    Log.e("insercao","Insercao no banco com sucesso!");
                }
            }catch (Throwable t) {
                System.out.println("erro no JSON" + t.toString());

            }
        }



        db.close();
        return 1;
    }

    public void excluirAll(){
        db = helper.getWritableDatabase();
        db.delete(SQLHelper.TABELA_SD, null, null);
        db.close();
    }

    public JSONArray listarTodas(JSONArray list) {
        Log.d("listarTodas","entrei no listar todas");
        String sql = "SELECT * FROM " + SQLHelper.TABELA_SD;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        //JSONArray list = new JSONArray();

        while (cursor.moveToNext()) {
            Log.d("Loop1","Entrei no while");
            int id = cursor.getInt(
                    cursor.getColumnIndex(SQLHelper.COLUNA_ID)
            );
            String pais = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_PAIS)
            );
            String capital = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_CAPITAL)
            );
            String spelling = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_SPELLING)
            );
            String regiao = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_REGIAO)
            );
            String subregiao = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_SUBREGIAO)
            );
            int populacao = Integer.valueOf(cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_POPULACAO))
            );
            float area = cursor.getFloat(
                    cursor.getColumnIndex(SQLHelper.COLUNA_AREA)
            );
            String moedas = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_MOEDAS)
            );
            String linguas = cursor.getString(
                    cursor.getColumnIndex(
                            SQLHelper.COLUNA_LINGUAS)
            );
            float relevancia = cursor.getFloat(
                    cursor.getColumnIndex(SQLHelper.COLUNA_RELEVANCIA)
            );

            JSONObject json = new JSONObject();
            try {
                json.put("id",id);
                json.put("name",pais);
                json.put("capital",capital);
                json.put("altSpellings",new JSONArray(spelling));
                json.put("region",regiao);
                json.put("subregion",subregiao);
                json.put("population",populacao);
                json.put("area",area);
                json.put("currencies",new JSONArray(moedas));
                json.put("languages",new JSONArray(linguas));
                json.put("relevance",relevancia);
                list.put(json);
                Log.d("Tentou criar JSON","Tentei criar JSON" + json.toString());
            } catch (JSONException e) {
                Log.e("ERROTentou criar JSON","ERRO:Tentei criar JSON" + e.toString());
                e.printStackTrace();
            }


        }
        cursor.close();
        return list;
    }

}