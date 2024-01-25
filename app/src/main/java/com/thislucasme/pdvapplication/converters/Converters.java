package com.thislucasme.pdvapplication.converters;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thislucasme.pdvapplication.model.Produto;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<Produto> fromString(String value) {
        Type listType = new TypeToken<List<Produto>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Produto> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
