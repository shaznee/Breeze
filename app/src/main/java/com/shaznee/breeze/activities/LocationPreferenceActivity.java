package com.shaznee.breeze.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.arrayadapters.PreferenceAdapter;
import com.shaznee.breeze.preferences.LocationPreferenceProvider;
import com.shaznee.breeze.preferences.MyLocation;

import org.json.JSONException;

import java.util.List;

public class LocationPreferenceActivity extends ListActivity {

    private static final String TAG = LocationPreferenceActivity.class.getSimpleName();

    private static final int PREFERENCE_ACTIVITY_REQUEST = 1001;
    private List<MyLocation> locations;
    private LocationPreferenceProvider locationPreferenceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_preference);
        locationPreferenceProvider = new LocationPreferenceProvider(this);
        try {
            refreshDisplay();
        } catch (JSONException e) {
            Log.d(TAG, "JSONException : ", e);
        }
    }

    private void refreshDisplay() throws JSONException {
        locations = locationPreferenceProvider.findAll();
        PreferenceAdapter adapter = new PreferenceAdapter(this, locations);
        setListAdapter(adapter);
    }

//    private void addLocation() {
//        MyLocation location = new MyLocation();
//        Intent intent = new Intent(this, SearchActivity.class);
////        intent.putExtra(Note.KEY, note.getKey());
////        intent.putExtra(Note.TEXT, note.getText());
//        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
//            MyLocation location = new MyLocation();
////            note.setKey(data.getStringExtra("key"));
////            note.setText(data.getStringExtra("text"));
//            try {
//                locationPreferenceProvider.update(location);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    refreshDisplay();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}


