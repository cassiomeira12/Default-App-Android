package com.android.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    private static final String PREF_NAME = "app";

    private static final String TOKEN = "token";
    private static final String VERSION = "version";
    private static final String NOTIFICACAO = "notificacao";

    public static SharedPreferences getSPreference(Context context) {
        if (context == null)
            return null;

        SharedPreferences preferences;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return preferences;
    }

    public static void setVersion(Context context, String value) {
        SharedPreferences.Editor editor = getSPreference(context).edit();

        editor.putString(VERSION, value);
        editor.apply();
    }

    public static String getVersion(Context context) {
        return getSPreference(context).getString(VERSION, null);
    }

    public static void setToken(Context context, String value) {
        SharedPreferences.Editor editor = getSPreference(context).edit();

        editor.putString(TOKEN, value);
        editor.apply();
    }

    public static String getToken(Context context) {
        return getSPreference(context).getString(TOKEN, null);
    }

    public static void setNotificacao(Context context, boolean value) {
        SharedPreferences.Editor editor = getSPreference(context).edit();

        editor.putBoolean(NOTIFICACAO, value);
        editor.apply();
    }

    public static boolean getNotificacao(Context context) {
        return getSPreference(context).getBoolean(NOTIFICACAO, false);
    }

}
