package com.thislucasme.pdvapplication.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thislucasme.pdvapplication.model.Produto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "contactdb.sqlite";
    public static final String CONTACTS_TABLE_NAME = "mycontacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_STUNAME = "name";
    public static final String CONTACTS_COLUMN_STUPHONE = "phone";
    public static final String CONTACTS_COLUMN_STUSTREET = "street";
    public static final String CONTACTS_COLUMN_STUEMAIL = "email";
    public static final String CONTACTS_COLUMN_STUCITY = "place";

    public static final String ORDERS_TABLE_NAME = "orders";
    public static final String ORDERS_COLUMN_ID = "order_id";
    public static final String ORDERS_COLUMN_CUSTOMER_NAME = "customer_name";
    public static final String ORDERS_COLUMN_PRODUCTS = "products";

    public static final String PRODUTO_TABLE_NAME = "produtos";
    public static final String PRODUTO_COLUMN_ID = "id";
    public static final String PRODUTO_COLUMN_DESCRICAO = "descricao";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
        Log.d("DBHelper", "DBHelper criado");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table mycontacts " +
                        "(id integer primary key autoincrement, name text, phone text, email text, street text, place text)"
        );
        Log.d("DBHelper", "Tabela 'mycontacts' criada");

        db.execSQL(
                "create table orders " +
                        "(order_id integer primary key autoincrement, customer_name text, products text)"
        );
        Log.d("DBHelper", "Tabela 'orders' criada");

        db.execSQL(
                "create table " + PRODUTO_TABLE_NAME +
                        "(" +
                        PRODUTO_COLUMN_ID + " integer primary key, " +
                        PRODUTO_COLUMN_DESCRICAO + " text" +
                        ")"
        );
        Log.d("DBHelper", "Tabela 'produtos' criada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mycontacts");
        db.execSQL("DROP TABLE IF EXISTS orders");
        db.execSQL("DROP TABLE IF EXISTS " + PRODUTO_TABLE_NAME);
        onCreate(db);
    }

    public boolean addOrder(String customerName, List<Produto> produtos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues orderValues = new ContentValues();
        orderValues.put("customer_name", customerName);
        long orderId = db.insert("orders", null, orderValues);

        if (orderId != -1) {
            for (Produto produto : produtos) {
                ContentValues produtoValues = new ContentValues();
                produtoValues.put(PRODUTO_COLUMN_DESCRICAO, produto.getDescricao());

                long produtoId = db.insert(PRODUTO_TABLE_NAME, null, produtoValues);

                if (produtoId != -1) {
                    ContentValues orderProdutoValues = new ContentValues();
                    orderProdutoValues.put("order_id", orderId);
                    orderProdutoValues.put("produto_id", produtoId);
                    db.insert("order_produto", null, orderProdutoValues);
                } else {
                    Log.e("DBHelper", "Falha ao inserir produto");
                    db.close();
                    return false;
                }
            }

            db.close();
            Log.d("DBHelper", "Pedido inserido com sucesso");
            return true;
        } else {
            Log.e("DBHelper", "Falha ao inserir pedido");
            db.close();
            return false;
        }
    }

    private String convertProdutoListToJson(List<Produto> produtos) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Produto>>(){}.getType();
        return gson.toJson(produtos, listType);
    }

    private List<Produto> convertJsonToProdutoList(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Produto>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public List<Produto> getOrderData(int orderId) {
        List<Produto> produtos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT produto_id FROM order_produto WHERE order_id = " + orderId, null);

        if (cursor != null && cursor.moveToFirst()) {
            int produtoIdIndex = cursor.getColumnIndex("produto_id");
            do {
                int produtoId = cursor.getInt(produtoIdIndex);
                Cursor produtoCursor = db.rawQuery("SELECT * FROM " + PRODUTO_TABLE_NAME + " WHERE " + PRODUTO_COLUMN_ID + " = " + produtoId, null);
                if (produtoCursor != null && produtoCursor.moveToFirst()) {
                    Produto produto = new Produto();
                    int listColumnIndex = produtoCursor.getColumnIndex(PRODUTO_COLUMN_DESCRICAO);
                    produto.setDescricao(produtoCursor.getString(listColumnIndex));
                    produtos.add(produto);
                    produtoCursor.close();
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return produtos;
    }

    // Other methods can be added as needed for your application
}
