package com.example.airloca.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Functions {


    public static final String LabelPermanentPersonne = "PermanentPersonne";

    public static void SaveSharedPreferences(Activity activity, String key, String value)
    {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String LoadSharedPreferences(Activity activity, String key)
    {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }
}
