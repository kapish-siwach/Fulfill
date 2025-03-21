package com.example.bottomandnav;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

public class SessionManagement {
    private static final String PREF_NAME = "UserSession";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Context context;

    public SessionManagement(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getUserDetail(String key) {
        return sharedPreferences.getString(key, "N/A");
    }


    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }


    public void logout() {
        editor.clear();
        editor.apply();
    }

    public void storeLoginDetails(List<ResponseModel> responseData) {
        editor.putString("email",responseData.get(0).email);
        editor.putString("role_name",responseData.get(0).role_name);
        editor.putString("mobile",responseData.get(0).phoneNo);
        editor.putString("name",responseData.get(0).name);
        editor.putString("company_id",responseData.get(0).company_id);
        editor.putString("menu",new Gson().toJson(responseData.get(0).menu));
        editor.putBoolean("isLoggedIn",true);
        editor.putString("profile",responseData.get(0).profile);
        editor.apply();
    }
}
