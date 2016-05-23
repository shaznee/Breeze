package com.shaznee.breeze.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Shaznee on 23-May-16.
 */
public class LocationPreferenceProvider {

    private static final String PREFKEY = "locationPref";
    private SharedPreferences notePrefs;

    public LocationPreferenceProvider(Context context) {
        this.notePrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public List<MyLocation> findAll() throws JSONException {

        Map<String, ?> locationsMap = notePrefs.getAll();
        SortedSet<String> cities = new TreeSet<>(locationsMap.keySet());
        List<MyLocation> locations = new ArrayList<>();
        for (String city : cities) {
            MyLocation myLocation = MyLocation.fromJSON(city,(String) locationsMap.get(city));
            locations.add(myLocation);
        }
        return locations;
    }

    public boolean update(MyLocation location) throws JSONException {
        SharedPreferences.Editor editor = notePrefs.edit();
        editor.putString(location.getCityName(), location.toJSON());
        editor.commit();
        return true;
    }

    public boolean remove(MyLocation location) {

        if (notePrefs.contains(location.getCityName())) {
            SharedPreferences.Editor editor = notePrefs.edit();
            editor.remove(location.getCityName());
            editor.commit();
        }
        return true;
    }
}
