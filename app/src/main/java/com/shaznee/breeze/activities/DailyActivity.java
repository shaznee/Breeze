package com.shaznee.breeze.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.DayListAdapter;
import com.shaznee.breeze.models.Forecast;

public class DailyActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Intent intent = getIntent();
        Forecast forecast = intent.getParcelableExtra(MainActivity.FORECAST);

        DayListAdapter adapter = new DayListAdapter(this, forecast);
        setListAdapter(adapter);
    }

}
