package com.e.found.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class FoundAppSharedPreference {
    String PREF_NAME = "doc";
String user_id="user_id";

    private SharedPreferences pref = null;
    private static FoundAppSharedPreference preferences = null;
    static Context mContext = null;

    public static FoundAppSharedPreference getInstance(Context context) {
        if (preferences == null) {
            preferences = new FoundAppSharedPreference(context);
        }
        mContext = context;
        return preferences;

    }

    public void clearPreference() {
        pref.edit().clear().commit();
    }

    public FoundAppSharedPreference(Context context) {
        mContext = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public FoundAppSharedPreference(Activity context) {
        mContext = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    //----------
    public void setUser_id(String str) {
        pref.edit().putString(user_id, str).commit();
    }

    public String getUser_id() {
        return pref.getString(user_id, "");
    }





}
