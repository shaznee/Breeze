package com.shaznee.breeze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.pageadapters.SectionsPagerAdapter;
import com.shaznee.breeze.fragments.WeatherFragment;
import com.shaznee.breeze.models.location.MyLocation;
import com.shaznee.breeze.providers.preferences.LocationPreferenceProvider;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements WeatherFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int SEARCH_REQUEST = 1001;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private LocationPreferenceProvider locationPreferenceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar searchBar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(searchBar);

        locationPreferenceProvider = new LocationPreferenceProvider(this);
        try {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), locationPreferenceProvider.findAll());
        } catch (JSONException e) {
            Log.d(TAG, "JSONException : ", e);
        }

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mSectionsPagerAdapter.addLocations(locationPreferenceProvider.findAll());
        } catch (JSONException e) {
            Log.d(TAG, "JSONException : ", e);
        }
    }

    @Override
    public void onFragmentInteraction() {
        Toast.makeText(this, "Call from fragment", Toast.LENGTH_SHORT).show();
    }

    protected void onCitiesLabelClick(View view) {
        startActivity(new Intent(this, LocationPreferenceActivity.class));
    }

    protected void onAddLabelClick(View view) {
        Intent intent = new Intent(this, SearchAcitivty.class);
        startActivityForResult(intent, SEARCH_REQUEST);
    }

    protected void onMoreLabelClick(View view) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SEARCH_REQUEST) {
            MyLocation location = data.getParcelableExtra(SearchAcitivty.SEARCH_RESULT);
            Toast.makeText(this, location.getPrimaryText(), Toast.LENGTH_LONG).show();
        }
    }
}
