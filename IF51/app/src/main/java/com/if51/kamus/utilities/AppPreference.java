package com.if51.kamus.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.if51.kamus.R;

public class AppPreference {
    private SharedPreferences prefs;
    private Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getString(R.string.app_first_run);
        editor.putBoolean(context.getString(R.string.app_first_run), input);
        editor.commit();
    }

    public Boolean getFirstRun() {
        String key = context.getString(R.string.app_first_run);
        return prefs.getBoolean("first_run", true);

    }

}
