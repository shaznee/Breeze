package com.shaznee.breeze.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.places.Place;
import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.recycleradapters.SearchAdapter;
import com.shaznee.breeze.listeners.SearchClickListener;
import com.shaznee.breeze.models.location.MyLocation;
import com.shaznee.breeze.models.location.PredictedPlace;
import com.shaznee.breeze.providers.location.LocationProvider;
import com.shaznee.breeze.providers.location.PlaceCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAcitivty extends AppCompatActivity implements
        SearchView.OnQueryTextListener, SearchClickListener.OnItemClickListener {

    public static final String SEARCH_RESULT = "SEARCH_RESULT";
    private static final String TAG = SearchAcitivty.class.getSimpleName();

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private SearchAdapter searchAdapter;
    private LocationProvider locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar searchBar = (Toolbar) findViewById(R.id.searchBar);
        setSupportActionBar(searchBar);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        locationProvider = new LocationProvider(this, null);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchAdapter = new SearchAdapter(locationProvider.getGoogleApiClient());
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new SearchClickListener(this, this));

        handleIntent(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationProvider.disconnect();

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationProvider.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            onQueryTextSubmit(query);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchAdapter.getFilter().filter(newText);
        return true;
    }

    private void publishResults(final PredictedPlace predictedPlace) {

        locationProvider.getPlaceById(predictedPlace.getPlaceId(), new PlaceCallback() {
            @Override
            public void onPlaceFound(Place place) {
                MyLocation location = new MyLocation(predictedPlace.getPlaceId(),
                        place.getLatLng().latitude,
                        place.getLatLng().longitude,
                        predictedPlace.getPrimaryText(),
                        predictedPlace.getSecondaryText());

                Intent intent = new Intent();
                intent.putExtra(SEARCH_RESULT, location);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        publishResults(searchAdapter.getPlaceIDAt(position));
    }

}
