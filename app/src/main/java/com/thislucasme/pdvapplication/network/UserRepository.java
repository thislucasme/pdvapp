package com.thislucasme.pdvapplication.network;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.login.LoginActivity;
import com.thislucasme.pdvapplication.model.Token;
import com.thislucasme.pdvapplication.model.User;
import com.thislucasme.pdvapplication.pdv.PdvActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private ApiService apiService;
    private Context context;
    public UserRepository(Context context) {
        this.context = context;
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
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://2e6e-177-152-104-103.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void logar(User user, Callback<Token> callback) {
        Call<Token> call = apiService.login(user);
        call.enqueue(callback);
    }
}
