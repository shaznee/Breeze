package com.shaznee.breeze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.arrayadapters.PreferenceAdapter;
import com.shaznee.breeze.providers.preferences.LocationPreferenceProvider;
import com.shaznee.breeze.models.location.MyLocation;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationPreferenceActivity extends AppCompatActivity {

    private static final String TAG = LocationPreferenceActivity.class.getSimpleName();

    private static final int SEARCH_REQUEST = 1001;
    private List<MyLocation> locations;
    private LocationPreferenceProvider locationPreferenceProvider;
    @BindView(R.id.preference_list) ListView preferenceList;
    @BindView(android.R.id.empty) TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_preference);
        Toolbar searchBar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(searchBar);
        ButterKnife.bind(this);

        preferenceList.setEmptyView(emptyTextView);

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
        preferenceList.setAdapter(adapter);
    }

    protected void onAddLabelClick(View view) {
        Intent intent = new Intent(this, SearchAcitivty.class);
        startActivityForResult(intent, SEARCH_REQUEST);
    }

    protected void onMoreLabelClick(View view) {
    }

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


