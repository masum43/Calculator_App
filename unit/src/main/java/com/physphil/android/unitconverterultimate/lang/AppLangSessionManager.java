package com.physphil.android.unitconverterultimate.lang;

import android.content.Context;
import android.content.SharedPreferences;

public class AppLangSessionManager {

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static int PRIVATE_MODE = 0;

    private static final String PREFER_NAME = "AppLangPref";
    public static final String KEY_APP_LANGUAGE = "lang";


    public static void setLanguage(Context _context, String lang) {
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.putString(KEY_APP_LANGUAGE,lang);
        editor.apply();
    }


    public static String getLanguage(Context _context) {
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        return pref.getString(KEY_APP_LANGUAGE, "");
    }








}

