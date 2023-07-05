package com.thislucasme.pdvapplication.teste;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PedidoDatabaseHelper extends SQLiteOpenHelper {
    private static PedidoDatabaseHelper sInstance;
    // Database Info
    private static final String DATABASE_NAME = "pdvapplication";
    private static final int DATABASE_VERSION = 1;

    public PedidoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized PedidoDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new PedidoDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS products_copy1 (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  estoque INTEGER,\n" +
                "  codigo_barras VARCHAR(100),\n" +
                "  observacao VARCHAR(255),\n" +
                "  controlar_estoque INTEGER,\n" +
                "  venda_fracionada INTEGER,\n" +
                "  valor_aberto VARCHAR(45),\n" +
                "  users_id INTEGER NOT NULL,\n" +
                "  descricao VARCHAR(100),\n" +
                "  url_image TEXT,\n" +
                "  FOREIGN KEY (users_id) REFERENCES users (id)\n" +
                ")";
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
            onCreate(sqLiteDatabase);
        }
    }
}
