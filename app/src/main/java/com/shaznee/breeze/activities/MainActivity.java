package com.shaznee.breeze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.pageadapter.SectionsPagerAdapter;
import com.shaznee.breeze.fragments.WeatherFragment;
import com.shaznee.breeze.preferences.LocationPreferenceProvider;
import com.shaznee.breeze.preferences.MyLocation;

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
    public void onFragmentInteraction() {
        Toast.makeText(this, "Call from fragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
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
            Toast.makeText(this, "On activity result", Toast.LENGTH_LONG).show();
        }
    }
}
