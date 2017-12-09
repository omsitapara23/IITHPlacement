package com.android.example.iithplacement.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by om on 8/12/17.
 */

public final class PrefrenceManagment {
    public static final String last_event_key = "Last_Event";
    public static final String default_event = "omsitaparaisthebest";
    public static final String last_announcement = "last_announcement";

    synchronized public static void setLast_event_key(Context context, String new_event){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(last_event_key, new_event);
        editor.apply();
    }

    public static String getLast_event_key(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String last_event = prefs.getString(last_event_key,default_event);
        return last_event;
    }

    synchronized public static void setLast_announcement(Context context, String new_announce){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(last_announcement, new_announce);
        editor.apply();
    }

    public static String getLast_announcement(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String last_event = prefs.getString(last_announcement,default_event);
        return last_event;
    }

}
