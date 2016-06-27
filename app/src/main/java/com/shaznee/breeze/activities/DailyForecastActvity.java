package com.shaznee.breeze.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.arrayadapters.DayAdapter;
import com.shaznee.breeze.fragments.WeatherFragment;
import com.shaznee.breeze.models.weather.Forecast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyForecastActvity extends AppCompatActivity {

    @BindView(R.id.weeklyForecastList) ListView weeklyForecastList;
    @BindView(R.id.emptyTextView) TextView emptyTextView;
    @BindView(R.id.locationLabel) TextView locationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        Toolbar searchBar = (Toolbar) findViewById(R.id.actionBar);
        setSupportActionBar(searchBar);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.this_week_label));

        Intent intent = getIntent();
        Forecast forecast = intent.getParcelableExtra(WeatherFragment.FORECAST);
        String cityName = intent.getStringExtra(WeatherFragment.CITY_NAME);
        locationLabel.setText(cityName);
        CharSequence unitPref = intent.getCharSequenceExtra(WeatherFragment.UNIT_PREF);

        DayAdapter adapter = new DayAdapter(this, forecast, unitPref);
        weeklyForecastList.setEmptyView(emptyTextView);
        weeklyForecastList.setAdapter(adapter);
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

}
