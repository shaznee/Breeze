package com.shaznee.breeze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.arrayadapters.PreferenceAdapter;
import com.shaznee.breeze.models.location.MyLocation;
import com.shaznee.breeze.providers.preferences.LocationPreferenceProvider;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationPreferenceActivity extends AppCompatActivity {

    private static final String TAG = LocationPreferenceActivity.class.getSimpleName();

    private static final int SEARCH_REQUEST = 1001;
    private List<MyLocation> locations;
    private LocationPreferenceProvider locationPreferenceProvider;
    private PreferenceAdapter adapter;
    @BindView(R.id.preference_list) ListView preferenceList;
    @BindView(android.R.id.empty) TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_preference);
        Toolbar searchBar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(searchBar);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        preferenceList.setEmptyView(emptyTextView);

        locationPreferenceProvider = new LocationPreferenceProvider(getApplicationContext());
        try {
            refreshDisplay();
        } catch (JSONException e) {
            Log.d(TAG, "JSONException : ", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preference, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.mnu_add:
                onMenuAddClick();
                break;
            case R.id.mnu_more:
                onMenuMoreClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshDisplay() throws JSONException {
        locations = locationPreferenceProvider.findAll();
        adapter = new PreferenceAdapter(this, locations);
        preferenceList.setAdapter(adapter);
    }

    private void onMenuAddClick() {
        Intent intent = new Intent(this, SearchAcitivty.class);
        startActivityForResult(intent, SEARCH_REQUEST);
    }

    private void onMenuMoreClick() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SEARCH_REQUEST) {
            MyLocation location = data.getParcelableExtra(SearchAcitivty.SEARCH_RESULT);
            try {
                locationPreferenceProvider.update(location);
                adapter.addLocation(location);
                Toast.makeText(this, location.getPrimaryText() + " registered", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Log.d(TAG, "JSONException : ", e);
            }
        }
    }
}


