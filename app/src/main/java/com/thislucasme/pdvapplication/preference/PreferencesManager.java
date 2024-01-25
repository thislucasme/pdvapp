package com.thislucasme.pdvapplication.preference;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREFERENCES_NAME = "pdvPreferences";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_UID = "user_uid";
    // Adicione mais chaves conforme necessário

    private final SharedPreferences preferences;

    public PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saverUserToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getUserToken() {
        return preferences.getString(KEY_TOKEN, null);
        // O segundo argumento é o valor padrão que será retornado se a chave não estiver presente
        // Neste caso, retorna null se a chave não estiver definida
    }

    public void saveUserId(String userUID) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USER_UID, userUID);
        editor.apply();
    }

    public String getUserId() {
        return preferences.getString(KEY_USER_UID, null);
        // O segundo argumento é o valor padrão que será retornado se a chave não estiver presente
        // Neste caso, retorna -1 se a chave não estiver definida
    }

    // Adicione métodos adicionais conforme necessário

    // Exemplo de como limpar todas as preferências (apenas para fins de exemplo, use com cuidado)
    public void clearPreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
