package com.example.activiesandviews;

import android.content.Context;
import android.content.SharedPreferences;

public class UserModel {
    private static final String PREFERENCES_FILE = "UserPreferences";
    private static final String NAME_KEY = "name";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";


    private SharedPreferences sharedPreferences;

    public UserModel(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public boolean registerUser(String username, String password, String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(NAME_KEY, name);
        return editor.commit();
    }

    public boolean authenticate(String username, String password) {
        String storedUsername = sharedPreferences.getString(USERNAME_KEY, "");
        String storedPassword = sharedPreferences.getString(PASSWORD_KEY, "");
        return storedUsername.equals(username) && storedPassword.equals(password);
    }


    public String getUserName() {
        return sharedPreferences.getString(USERNAME_KEY, "");
    }

    public String getName() {
        return sharedPreferences.getString(NAME_KEY, "");
    }
}
