package com.shaznee.breeze.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.arrayadapters.DayAdapter;
import com.shaznee.breeze.fragments.WeatherFragment;
import com.shaznee.breeze.models.Forecast;

public class DailyForecastActvity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        Forecast forecast = intent.getParcelableExtra(WeatherFragment.FORECAST);

        DayAdapter adapter = new DayAdapter(this, forecast);
        setListAdapter(adapter);
    }

}
