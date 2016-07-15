package com.shaznee.breeze.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.shaznee.breeze.R;
import com.shaznee.breeze.ui.adapters.arrayadapters.DayAdapter;
import com.shaznee.breeze.services.weather.Forecast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SHAZNEE on 29-Jun-16.
 */
public class DailyFragment extends Fragment {

    @BindView(R.id.weeklyForecastList)
    ListView weeklyForecastList;
    @BindView(R.id.emptyTextView) TextView emptyTextView;
    @BindView(R.id.locationLabel) TextView locationLabel;

    public DailyFragment() {}

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DailyFragment newInstance(/*int sectionNumber*/) {
        DailyFragment fragment = new DailyFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily_forecast, container, false);
        ButterKnife.bind(this, rootView);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        Intent intent = getActivity().getIntent();
        Forecast forecast = intent.getParcelableExtra(WeatherFragment.FORECAST);
        String cityName = intent.getStringExtra(WeatherFragment.CITY_NAME);
        locationLabel.setText(cityName);
        CharSequence unitPref = intent.getCharSequenceExtra(WeatherFragment.UNIT_PREF);

        DayAdapter adapter = new DayAdapter(getActivity(), forecast, unitPref);
        weeklyForecastList.setEmptyView(emptyTextView);
        weeklyForecastList.setAdapter(adapter);
        return rootView;
    }
}
