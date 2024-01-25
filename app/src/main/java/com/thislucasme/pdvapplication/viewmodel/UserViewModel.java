package com.thislucasme.pdvapplication.viewmodel;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.thislucasme.pdvapplication.model.Token;
import com.thislucasme.pdvapplication.model.User;
import com.thislucasme.pdvapplication.network.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel {
    private UserRepository userRepository;
    private Context context;
    public UserViewModel(Context context) {
        this.context = context;
        userRepository = new UserRepository(context);
    }

    public void logar(User user, final Callback<Token> callback) {
        userRepository.logar(user, new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                Log.i("PATATI", user.getEmail()+", "+ user.getPassword().toString());
                Log.i("PATATI", response.message());
                if (response.isSuccessful()) {
                    // Requisição bem sucedida
                    String accessToken = response.headers().toString();
                    Log.i("PATATI", accessToken);
                    callback.onResponse(call, response);
                } else {
                    // Requisição falhou
                    callback.onFailure(call, new Throwable("Erro na requisição"));
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                // Erro na requisição
                callback.onFailure(call, t);
            }
        });
    }
}
