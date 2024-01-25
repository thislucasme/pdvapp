package com.thislucasme.pdvapplication.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thislucasme.pdvapplication.MainActivity;
import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.model.Token;
import com.thislucasme.pdvapplication.model.User;
import com.thislucasme.pdvapplication.pdv.PdvActivity;
import com.thislucasme.pdvapplication.preference.PreferencesManager;
import com.thislucasme.pdvapplication.viewmodel.UserViewModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private Button button;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        email = findViewById(R.id.emailLogin);
        senha = findViewById(R.id.passwordLogin);
        button = findViewById(R.id.buttonLogarLogin);
        userViewModel = new UserViewModel(getApplicationContext());


        // Dentro de um método em sua Activity ou Fragment
        User user = new User();
        user.setEmail("lucas@gmail.com");
        user.setPassword("050903");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarLogin(email.getText().toString(), senha.getText().toString());
            }
        });

    }
    private void realizarLogin(String email, String senha){
        button.setText("Carregando...");
        User user = new User();
        user.setEmail(email);
        user.setPassword(senha);


        userViewModel.logar(user, new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if(response.isSuccessful()){
                    Token token =  response.body();
                    Log.i("thislucasmed", "existe");
                    if (token != null) {
                        String tokenString = token.getAcess_token();
                        Log.i("thislucasmed", "Mensagem: " + tokenString);
                        SharedPreferences sharedPreferences = getSharedPreferences("dadosLogin", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", tokenString);
                        editor.apply();
                        PreferencesManager preferencesManager = new PreferencesManager(getApplicationContext());
                        preferencesManager.saverUserToken(tokenString);
                        finish();
                        Intent intent = new Intent(LoginActivity.this, PdvActivity.class);
                        startActivity(intent);
                    } else {
                        Log.i("thislucasmed", "Resposta vazia");
                    }
                }else{
                    Log.i("thislucasmed", "não existe");
                }
                // Lógica para tratar a resposta bem-sucedida da API
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();
                button.setText("Login");
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
              //  Log.i("AIOE", user.getEmail()+", "+user.getPassword());
                // Lógica para tratar a falha na chamada à API
                Toast.makeText(getApplicationContext(), t.getMessage()+call.request(), Toast.LENGTH_LONG).show();
                button.setText("Login");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("dadosLogin", Context.MODE_PRIVATE);
        String valorString = sharedPreferences.getString("token", null);
        if(valorString != null){
            Intent intent = new Intent(LoginActivity.this, PdvActivity.class);
            finish();
            startActivity(intent);
        }
    }
}