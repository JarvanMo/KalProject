package com.jarvanmo.kal.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    private SharedPreferences sharedPreferences ;
    private String sharedPreferencesName ="";

    public SharedPreferencesUtil(Context context){
        this("sharedPreferencesName",context);
    }


    public SharedPreferencesUtil(String name,Context context){
        sharedPreferencesName = name;
        sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }


    public SharedPreferencesUtil putInt (String key,int value) {
        sharedPreferences.edit().putInt(key, value).apply();
        return this;
    }

    public SharedPreferencesUtil putString(String key,String value) {
        sharedPreferences.edit().putString(key, value).apply();
        return this;
    }

    public SharedPreferencesUtil putBoolean(String key,boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
        return this;
    }

    public SharedPreferencesUtil putFloat(String key,float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
        return this;
    }

    public SharedPreferencesUtil putLong(String key,long value) {
        sharedPreferences.edit().putLong(key, value).apply();
        return this;
    }

    public int getInt(String key,int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public int getInt(String key) {
        return getInt(key,Integer.MIN_VALUE);
    }

    public String getString( String key,String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public String getString( String key) {
        return getString(key,"");
    }

    public boolean getBoolean( String key,boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public boolean getBoolean( String key) {
        return getBoolean(key,false);
    }

    public float getFloat( String key,float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public float getFloat( String key) {
        return getFloat(key,Float.MIN_VALUE);
    }

    public float getLong( String key,long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public float getLong( String key) {
        return getLong(key,Long.MIN_VALUE);
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
