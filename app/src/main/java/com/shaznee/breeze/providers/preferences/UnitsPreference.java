package com.shaznee.breeze.providers.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SHAZNEE on 22-Jun-16.
 */
public class UnitsPreference {

    private static final String PREFKEY = "unitPref";
    private static final String UNIT_PREF = "unitPrefs";
    private SharedPreferences unitPrefs;

    public UnitsPreference(Context context) {
        this.unitPrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public boolean isFarenheight() {
        return unitPrefs.getBoolean(UNIT_PREF, true);
    }

    public boolean setFarenheight(boolean preference) {
        SharedPreferences.Editor editor = unitPrefs.edit();
        editor.putBoolean(UNIT_PREF, preference);
        editor.commit();
        return true;
    }
}
