package com.If5a.booksdictionary.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    private SharedPreferences prefs;
    private Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(boolean input){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("first_run",input);
        editor.commit();
    }

    public Boolean getFirstRun() {
        return prefs.getBoolean("first_run", true);

    }
}
