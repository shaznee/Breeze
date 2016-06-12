package com.shaznee.breeze.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.arrayadapters.SearchAdapter;
import com.shaznee.breeze.location.LocationProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAcitivty extends AppCompatActivity implements SearchView.OnQueryTextListener {

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

        locationProvider = new LocationProvider(this, null);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchAdapter = new SearchAdapter(locationProvider.getGoogleApiClient());
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(layoutManager);

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
        return super.onCreateOptionsMenu(menu);
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

    private void publishResults() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
