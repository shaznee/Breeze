package com.shaznee.breeze.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shaznee.breeze.R;
import com.shaznee.breeze.ui.adapters.pageadapters.WeatherPagerAdapter;
import com.shaznee.breeze.ui.fragments.WeatherFragment;
import com.shaznee.breeze.models.location.MyLocation;
import com.shaznee.breeze.providers.preferences.LocationPreferenceProvider;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements WeatherFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int SEARCH_REQUEST = 1001;

    private WeatherPagerAdapter mWeatherPagerAdapter;
    private ViewPager mViewPager;
    private LocationPreferenceProvider locationPreferenceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar searchBar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(searchBar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.mnu_cities:
                onMenuCitiesClick();
                break;
            case R.id.mnu_add:
                onMenuAddClick();
                break;
//            case R.id.mnu_more:
//                onMenuMoreClick();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationPreferenceProvider = new LocationPreferenceProvider(getApplicationContext());
        try {
            mWeatherPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(), locationPreferenceProvider.findAll());
        } catch (JSONException e) {
            Log.d(TAG, "JSONException : ", e);
        }
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mWeatherPagerAdapter);
    }

    @Override
    public void onFragmentInteraction() {
        Toast.makeText(this, "Call from fragment", Toast.LENGTH_SHORT).show();
    }

    protected void onMenuCitiesClick() {
        startActivity(new Intent(this, LocationPreferenceActivity.class));
    }

    protected void onMenuAddClick() {
        Intent intent = new Intent(this, SearchAcitivty.class);
        startActivityForResult(intent, SEARCH_REQUEST);
    }

//    private void onMenuMoreClick() {
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SEARCH_REQUEST) {
            MyLocation location = data.getParcelableExtra(SearchAcitivty.SEARCH_RESULT);
            try {
                locationPreferenceProvider.update(location);
                mWeatherPagerAdapter.addLocation(location);
                Toast.makeText(this, location.getPrimaryText() + " registered", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Log.d(TAG, "JSONException : ", e);
            }
        }
    }
}
