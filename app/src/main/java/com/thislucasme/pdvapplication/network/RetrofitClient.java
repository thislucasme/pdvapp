package com.thislucasme.pdvapplication.network;

import retrofit2.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(Context ct) {
        Context context = ct;
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("dadosLogin", Context.MODE_PRIVATE);
                    String valorString = sharedPreferences.getString("token", null);
                    if(valorString != null){
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + valorString)
                                .build();
                        return chain.proceed(newRequest);
                    }else{
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + "")
                                .build();
                        return chain.proceed(newRequest);
                    }
                }
            }).build();
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl("http://192.168.0.110:8081/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
