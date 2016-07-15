package com.shaznee.breeze.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shaznee.breeze.R;
import com.shaznee.breeze.ui.activities.DetailsActivity;
import com.shaznee.breeze.models.location.MyLocation;
import com.shaznee.breeze.services.weather.Forecast;
import com.shaznee.breeze.providers.location.LocationChangeCallBack;
import com.shaznee.breeze.providers.location.LocationProvider;
import com.shaznee.breeze.providers.preferences.UnitsPreference;
import com.shaznee.breeze.services.weatherapi.ForecastCallBack;
import com.shaznee.breeze.services.weatherapi.ForecastClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherFragment extends Fragment implements ForecastCallBack, LocationChangeCallBack {

    private static final String TAG = WeatherFragment.class.getSimpleName();

    @BindView(R.id.timeLabel) TextView timeLabel;
    @BindView(R.id.locationLabel) TextView locationLabel;
    @BindView(R.id.temperatureLabel) TextView temperatureLabel;
    @BindView(R.id.unitLabel) TextView unitLabel;
    @BindView(R.id.humidityValue) TextView humidityValue;
    @BindView(R.id.precipValue) TextView precipValue;
    @BindView(R.id.summaryLabel) TextView summaryLabel;
    @BindView(R.id.iconImageView) ImageView iconImageView;
    @BindView(R.id.refreshImageView) ImageView refreshImageView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    public static final String FORECAST = "FORECAST";
    public static final String CITY_NAME = "CITY_NAME";
    public static final String UNIT_PREF = "UNIT_PREF";

    private static final String LOCATION = "LOCATION";
    private static final String LOCATION_PREFERENCE = "PREFERENCE";
    private static final String CURRENT_LOCATION = "CURRENT";
    private static final String SAVED_LOCATION = "SAVED";

    private ForecastClient client = ForecastClient.getInstance();
    private LocationProvider locationProvider;
    private Forecast forecast;
    private MyLocation location;
    private UnitsPreference unitsPreference;

    private double latitude;
    private double longitude;
    private String cityName;
    private String pref = CURRENT_LOCATION;

    private OnFragmentInteractionListener mListener;

    public WeatherFragment() {}

    public static WeatherFragment newInstance(MyLocation location) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        if (location != null) {
            args.putString(LOCATION_PREFERENCE, SAVED_LOCATION);
            args.putParcelable(LOCATION, location);
        } else {
            args.putString(LOCATION_PREFERENCE, CURRENT_LOCATION);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pref = getArguments().getString(LOCATION_PREFERENCE);
            if (pref.equals(SAVED_LOCATION)) {
                this.location = getArguments().getParcelable(LOCATION);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.INVISIBLE);
        unitLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unitsPreference.isFarenheight()) {
                    unitsPreference.setFarenheight(false);
                } else {
                    unitsPreference.setFarenheight(true);
                }
                updateDisplay();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        unitsPreference = new UnitsPreference(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (pref.equals(SAVED_LOCATION)) {
            handleLocation(location.getPrimaryText(), location.getLatitude(), location.getLongitude());
        } else if (pref.equals(CURRENT_LOCATION)) {
            locationProvider = new LocationProvider(getContext(), this);
            locationProvider.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (pref.equals(CURRENT_LOCATION)){
            locationProvider.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pref.equals(CURRENT_LOCATION)){
            locationProvider.connect();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (pref.equals(CURRENT_LOCATION)){
            locationProvider.disconnect();
        }
    }

    @Override
    public void successful(Forecast forecast) {
        toggleRefresh();
        this.forecast = forecast;
        updateDisplay();
    }

    @Override
    public void failure(Throwable throwable) {
        Log.e(TAG, "On Failure", throwable);
        toggleRefresh();
        showAlertDialog(getString(R.string.error_title), getString(R.string.error_message));
    }

    private void toggleRefresh() {
        if (progressBar.getVisibility() == View.INVISIBLE){
            progressBar.setVisibility(View.VISIBLE);
            refreshImageView.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            refreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        locationLabel.setText(cityName);
        if(unitsPreference.isFarenheight()) {
            temperatureLabel.setText(forecast.getCurrently().getTemperature() + "°");
            unitLabel.setText("F");
        } else {
            temperatureLabel.setText(forecast.getTemperatureCelcius(forecast.getCurrently().getTemperature()) + "°");
            unitLabel.setText("C");
        }
        timeLabel.setText(forecast.getFormattedTime());
        humidityValue.setText(forecast.getCurrently().getHumidity() + "");
        precipValue.setText(forecast.getCurrently().getPrecipProbability() + "%");
        summaryLabel.setText(forecast.getCurrently().getSummary());
        iconImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),forecast.getCurrently().getIconId()));
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @OnClick(R.id.refreshImageView)
    protected void refresh(View view) {
        handleNewLocation(cityName,latitude,longitude);
    }

    @OnClick(R.id.detailsButton)
    protected void startDailyActivity(View view) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(FORECAST, forecast);
        intent.putExtra(CITY_NAME, cityName);
        intent.putExtra(UNIT_PREF, unitLabel.getText());
        startActivity(intent);
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.dialog_ok), null)
                .create();

        alertDialog.show();
    }

    @Override
    public void handleNewLocation(String cityName, double latitude, double longitude) {
        handleLocation(cityName, latitude, longitude);
    }

    private void handleLocation(String cityName, double latitude, double longitude) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
        if (isOnline()) {
            toggleRefresh();
            client.getForecast(latitude, longitude, this);
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_network_string), Toast.LENGTH_LONG).show();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

}