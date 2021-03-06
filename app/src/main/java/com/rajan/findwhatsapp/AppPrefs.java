package com.rajan.findwhatsapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {
    private static AppPrefs appPrefs;
    Context context;
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    public AppPrefs(Context context) {
        this.context = context;
        this.appSharedPrefs = context.getSharedPreferences("PREFERENCES", Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public static AppPrefs getAppPrefs(Context context) {
        if (appPrefs == null) {
            appPrefs = new AppPrefs(context);
        }

        return appPrefs;
    }

    public void clearAllData() {
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public boolean getBoolean(String key) {
        return appSharedPrefs.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value) {
        prefsEditor.putBoolean(key, value).commit();
    }


    public void setString(String key, String value) {
        prefsEditor.putString(key, value).commit();
    }

    public String getString(String key) {
        return appSharedPrefs.getString(key, null);
    }

    public CountryList getCountryPref() {
        String jsonUser = appSharedPrefs.getString("COUNTRY_LIST", "{}");
        return CountryList.fromJson(CountryList.class, jsonUser);
    }

    public void setCountryPref(String countryJson) {
        prefsEditor.putString("COUNTRY_LIST", countryJson).commit();
    }

}
