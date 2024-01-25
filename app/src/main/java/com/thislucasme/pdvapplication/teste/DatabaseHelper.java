package com.thislucasme.pdvapplication.teste;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thislucasme.pdvapplication.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;
    // Database Info
    private static final String DATABASE_NAME = "pdvapplication";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
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
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  `name` varchar(100) DEFAULT NULL,\n" +
                "  `email` varchar(150) DEFAULT NULL,\n" +
                "  `senha` varchar(45) DEFAULT NULL\n" +
                ")";
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
            onCreate(sqLiteDatabase);
        }
    }
    public long add(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO `users` ( `name`, `email`, `senha`) VALUES ( 'piu', 'piu@gmail', '321');");
        return 0;
    }
    public long getAll(){
        try{
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM users", null);

            //indice da tabela
            int indiceNome = cursor.getColumnIndex("name");
            int indiceId = cursor.getColumnIndex("id");
            int indiceEmail = cursor.getColumnIndex("email");
            int indiceSenha = cursor.getColumnIndex("senha");

            cursor.moveToFirst();
            while (cursor != null){
                Log.i("RESULTADO", cursor.getString(indiceId)+", "+cursor.getString(indiceNome) +", "+ cursor.getString(indiceEmail)+", "+cursor.getString(indiceSenha));
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}
