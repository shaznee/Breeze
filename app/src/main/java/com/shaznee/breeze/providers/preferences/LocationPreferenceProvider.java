package com.shaznee.breeze.providers.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.shaznee.breeze.models.location.MyLocation;

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

        Map<String, ?> placesMap = notePrefs.getAll();
        SortedSet<String> places = new TreeSet<>(placesMap.keySet());
        List<MyLocation> locations = new ArrayList<>();
        for (String place : places) {
            MyLocation myLocation = MyLocation.fromJSON(place,(String) placesMap.get(place));
            locations.add(myLocation);
        }
        return locations;
    }

    public boolean update(MyLocation location) throws JSONException {
        SharedPreferences.Editor editor = notePrefs.edit();
        editor.putString(location.getPrimaryText(), location.toJSON());
        editor.commit();
        return true;
    }

    public boolean remove(MyLocation location) {

        if (notePrefs.contains(location.getPrimaryText())) {
            SharedPreferences.Editor editor = notePrefs.edit();
            editor.remove(location.getPrimaryText());
            editor.commit();
        }
        return true;
    }
}
