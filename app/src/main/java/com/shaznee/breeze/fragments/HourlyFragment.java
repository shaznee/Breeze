package com.shaznee.breeze.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.adapters.recycleradapters.HourAdapter;
import com.shaznee.breeze.models.weather.Forecast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SHAZNEE on 29-Jun-16.
 */
public class HourlyFragment extends Fragment {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.locationLabel) TextView locationLabel;

    public HourlyFragment() {}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HourlyFragment newInstance(/*int sectionNumber*/) {
        HourlyFragment fragment = new HourlyFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hourly_forecast, container, false);
        ButterKnife.bind(this, rootView);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        Intent intent = getActivity().getIntent();
        Forecast forecast = intent.getParcelableExtra(WeatherFragment.FORECAST);
        String cityName = intent.getStringExtra(WeatherFragment.CITY_NAME);
        locationLabel.setText(cityName);
        CharSequence unitPref = intent.getCharSequenceExtra(WeatherFragment.UNIT_PREF);

        HourAdapter hourAdapter = new HourAdapter(forecast, unitPref);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setAdapter(hourAdapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        return rootView;
    }
}
