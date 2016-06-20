package com.shaznee.breeze.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.arrayadapters.DayAdapter;
import com.shaznee.breeze.fragments.WeatherFragment;
import com.shaznee.breeze.models.weather.Forecast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyForecastActvity extends ListActivity {

    @BindView(R.id.locationLabel) TextView locationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Forecast forecast = intent.getParcelableExtra(WeatherFragment.FORECAST);
        String cityName = intent.getStringExtra(WeatherFragment.CITY_NAME);
        locationLabel.setText(cityName);

        DayAdapter adapter = new DayAdapter(this, forecast);
        setListAdapter(adapter);
    }

}
