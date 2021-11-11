package com.jeeni.facultyapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class FacultyPref {

    private static FacultyPref facultyPref;
    private SharedPreferences sharedPreferences;
    private static final String JEENI_FACULTY_PREFERENCE = "JeeniFacultyPrefernces";

    public static FacultyPref getInstance(Context context) {
        if (facultyPref == null) {
            facultyPref = new FacultyPref(context);
        }
        return facultyPref;
    }

    private FacultyPref(Context context) {
        sharedPreferences = context.getSharedPreferences(JEENI_FACULTY_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public String getData(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return null;
    }
    public void loggedIn(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }
    public void saveBooleanData(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }


    public boolean loggedIn(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }

    public void clearAll() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.remove(JEENI_FACULTY_PREFERENCE).apply();
    }
}
