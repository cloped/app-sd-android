package com.example.sistemas_distribuidos.dao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "dbSD";
    private static final int VERSAO_BANCO = 1;
    public static final String TABELA_SD = "sd_tabela";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_PAIS = "name";
    public static final String COLUNA_CAPITAL = "capital";
    public static final String COLUNA_SPELLING = "altSpellings";
    public static final String COLUNA_REGIAO = "regiao";
    public static final String COLUNA_SUBREGIAO = "subregion";
    public static final String COLUNA_POPULACAO = "population";
    public static final String COLUNA_AREA = "area";
    public static final String COLUNA_MOEDAS = "currencies";
    public static final String COLUNA_LINGUAS = "languages";
    public static final String COLUNA_RELEVANCIA = "relevance";


    public SQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABELA_SD + " ( " +
                        COLUNA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        COLUNA_PAIS + " TEXT, " +
                        COLUNA_CAPITAL + " TEXT, " +
                        COLUNA_SPELLING + " TEXT, " +
                        COLUNA_REGIAO + " TEXT, " +
                        COLUNA_SUBREGIAO + " TEXT, " +
                        COLUNA_POPULACAO + " INT, " +
                        COLUNA_AREA + " REAL, " +
                        COLUNA_MOEDAS + " TEXT, " +
                        COLUNA_LINGUAS + " TEXT, "+
                        COLUNA_RELEVANCIA + " REAL)"
        );

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // para as próximas versões
    }

}