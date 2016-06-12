package com.shaznee.breeze.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.recycleradapters.HourAdapter;
import com.shaznee.breeze.fragments.WeatherFragment;
import com.shaznee.breeze.models.Forecast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyForecastActivity extends Activity {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Forecast forecast = intent.getParcelableExtra(WeatherFragment.FORECAST);

        HourAdapter hourAdapter = new HourAdapter(forecast);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(hourAdapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

    }

}
